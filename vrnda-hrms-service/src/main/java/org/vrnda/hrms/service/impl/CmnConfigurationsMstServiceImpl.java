package org.vrnda.hrms.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.CmnConfigurationsMstEntity;
import org.vrnda.hrms.entity.CmnLeavePlanConfigDetailsEntity;
import org.vrnda.hrms.repository.CmnConfigurationsMstRepository;
import org.vrnda.hrms.repository.CmnLeavePlanConfigDetailsRepository;
import org.vrnda.hrms.repository.dto.CmnConfigurationsMstDTO;
import org.vrnda.hrms.service.CmnConfigurationsMstService;
import org.vrnda.hrms.service.CmnDeductionTypesConfigDetailsService;
import org.vrnda.hrms.service.CmnGeneralLoanConfigDetailsService;
import org.vrnda.hrms.service.CmnLeaveConfigDetailsService;
import org.vrnda.hrms.service.CmnLookupMstService;
import org.vrnda.hrms.service.CmnSalaryAllowanceConfigDetailsService;
import org.vrnda.hrms.service.CmnSalaryAnnualBenefitsConfigDetailsService;
import org.vrnda.hrms.service.CmnSalaryEarningsConfigDetailsService;
import org.vrnda.hrms.service.CmnSalarySlabLevelConfigDetailsService;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.EmployeeAllConfigDetailsService;
import org.vrnda.hrms.service.resultset.CmnConfigurationsMstResultSet;
import org.vrnda.hrms.service.resultset.CmnDeductionTypesConfigDetailsResultset;
import org.vrnda.hrms.service.resultset.CmnGeneralLoanConfigDetailsResultSet;
import org.vrnda.hrms.service.resultset.CmnLeaveConfigDetailsResultSet;
import org.vrnda.hrms.service.resultset.CmnSalaryAllowanceConfigDetailResultSet;
import org.vrnda.hrms.service.resultset.CmnSalaryAnnualBenefitsConfigDetailResultSet;
import org.vrnda.hrms.service.resultset.CmnSalaryEarningsConfigDetailsResultSet;
import org.vrnda.hrms.service.resultset.CmnSalarySlabLevelConfigDetailsResultSet;
import org.vrnda.hrms.service.resultset.EmployeeAllConfigDetailsResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class CmnConfigurationsMstServiceImpl extends GenericServiceImpl<CmnConfigurationsMstEntity, String> implements CmnConfigurationsMstService {

	public CmnConfigurationsMstServiceImpl(PagingAndSortingRepository<CmnConfigurationsMstEntity, String> typeRepository) {
		super(typeRepository);
	}

	@Autowired
	CmnLeavePlanConfigDetailsRepository cmnLeavePlanConfigDetailsRepository;

	@Autowired
	CmnGeneralLoanConfigDetailsService cmnGeneralLoanConfigDetailsService;
	
	@Autowired
	CmnConfigurationsMstRepository cmnConfigurationMstRepository;

	@Autowired
	EmployeeAllConfigDetailsService employeeAllConfigDetailsService;
	
	@Autowired
	CmnSalarySlabLevelConfigDetailsService cmnSalarySlabLevelConfigDetailsService;
	
	@Autowired
    CmnSalaryAllowanceConfigDetailsService cmnSalaryAllowanceConfigDetailsService;
	
	@Autowired
    CmnLeaveConfigDetailsService CmnLeaveConfigDetailsService;

	@Autowired
	CmnLookupMstService cmnLookupMstService;

	@Autowired
	CmnTableSeqService cmnTableSeqService;
	
	@Autowired
	CmnSalaryEarningsConfigDetailsService cmnSalaryEarningsConfigDetailsService;
	
	@Autowired
    CmnSalaryAnnualBenefitsConfigDetailsService cmnSalaryAnnualBenefitsConfigDetailsService;
	
	@Autowired
	CmnDeductionTypesConfigDetailsService cmnDeductionTypesConfigDetailsService;

	@Override
	public CmnConfigurationsMstResultSet saveOrUpdateConfiguration(CmnConfigurationsMstDTO cmnConfiguration, String loggedInUser) {
		CmnConfigurationsMstResultSet cmnConfigMstResultSet = new CmnConfigurationsMstResultSet();
		Long inActiveFlag = cmnLookupMstService.getLookupIdByLookupNameAndParentLookupName(ApplicationConstants.INACTIVE, ApplicationConstants.STATUS);
		cmnConfiguration.setConfigTypeLookupId(cmnLookupMstService.getLookupByLookupName(cmnConfiguration.getConfigTypeLookupName()).getCmnLookupMstEntity().getLookupId());
		try {
			if(cmnConfiguration!=null) {
				if(verifyDuplicateConfiguratoinName(cmnConfiguration)) {
					cmnConfigMstResultSet.setStatus(false);
					cmnConfigMstResultSet.setMessage("Failed");
					cmnConfigMstResultSet.setMessageDescription("Configuration already exists with same name for selected year.");
					return cmnConfigMstResultSet;
				}
				if ("Y".equals(cmnConfiguration.getIsOverride()) && cmnConfiguration.getPreviousConfigId() != null && cmnConfiguration.getPreviousConfigId() > 0) {
					CmnConfigurationsMstEntity previousConfiguration = cmnConfigurationMstRepository.getConfigurationByConfigurationId(cmnConfiguration.getPreviousConfigId());
					if (previousConfiguration != null) {
						CmnConfigurationsMstEntity cmnConfigurationsMstEntity = new CmnConfigurationsMstEntity();
						BeanUtils.copyProperties(previousConfiguration, cmnConfigurationsMstEntity);
						cmnConfigurationsMstEntity.setDefaultConfig("N");
						save(cmnConfigurationsMstEntity);
					}
				}
				if(cmnConfiguration.getConfigurationId()==null || cmnConfiguration.getConfigurationId() == 0) {
					CmnConfigurationsMstEntity cmnConfigMstEntity = new CmnConfigurationsMstEntity();
					BeanUtils.copyProperties(cmnConfiguration, cmnConfigMstEntity);
					cmnConfigMstEntity.setConfigurationId(cmnTableSeqService.getNextSequence(ApplicationConstants.CMN_CONFIGURATIONS_MST, ApplicationConstants.CONFIGURATION_ID));
					BaseUtils.setBaseData(cmnConfigMstEntity, loggedInUser);
					save(cmnConfigMstEntity);
					cmnConfigMstResultSet.setMessageDescription("Record Saved Successfully."); 
				} else if(cmnConfiguration != null &&cmnConfiguration.getConfigurationId()>0) {
					EmployeeAllConfigDetailsResultSet employeeAllConfigDetailsResultSet = null;
					CmnConfigurationsMstEntity cmnConfigurationMstEntity = cmnConfigurationMstRepository.getConfigurationByConfigurationId(cmnConfiguration.getConfigurationId());
					if(cmnConfigurationMstEntity != null) {
						if(cmnConfiguration.getStatusLookupId()==inActiveFlag) {
							employeeAllConfigDetailsResultSet = employeeAllConfigDetailsService.getEmployeeAllConfigDetailsByConfigurationIdAndConfigTypeLookupId(cmnConfiguration.getConfigurationId(), cmnConfiguration.getConfigTypeLookupId());
							if (employeeAllConfigDetailsResultSet != null && employeeAllConfigDetailsResultSet.getEmployeeAllConfigDetailsEntityList() != null && employeeAllConfigDetailsResultSet.getEmployeeAllConfigDetailsEntityList().size() > 0) {
								cmnConfigMstResultSet.setStatus(false);
								cmnConfigMstResultSet.setMessage("Failed");
								cmnConfigMstResultSet.setMessageDescription("Record cannot be inactivated as a child record exists in the Employee all configurations.");
							} else {
								BeanUtils.copyProperties(cmnConfiguration, cmnConfigurationMstEntity);
								BaseUtils.modifyBaseData(cmnConfigurationMstEntity, loggedInUser);
								save(cmnConfigurationMstEntity);
								cmnConfigMstResultSet.setMessageDescription("Record updated successfully.");
							}
						}
						else {
							BeanUtils.copyProperties(cmnConfiguration, cmnConfigurationMstEntity);
							BaseUtils.modifyBaseData(cmnConfigurationMstEntity, loggedInUser);
							save(cmnConfigurationMstEntity);
							cmnConfigMstResultSet.setMessageDescription("Record updated successfully.");
						}
					}
				}
				else {
					cmnConfigMstResultSet.setStatus(false);
					cmnConfigMstResultSet.setMessage("Error");
					cmnConfigMstResultSet.setMessageDescription("Invalid Inputs.");
				}
			}
		} catch (Exception e) {
			cmnConfigMstResultSet.setStatus(false);
			cmnConfigMstResultSet.setMessage("Exception while save Configuration Data");
			cmnConfigMstResultSet.setMessageDescription(e.getMessage());
		}
		return cmnConfigMstResultSet;
	}

	@Transactional(rollbackOn = Exception.class)
	@Override
	public CmnConfigurationsMstResultSet deleteConfigurationByConfigurationId(Long configurationId) {
		CmnConfigurationsMstResultSet cmnConfigMstResultSet = new CmnConfigurationsMstResultSet();
		try {
			if (configurationId != null) {
				CmnConfigurationsMstEntity cmnConfigMstEntity = cmnConfigurationMstRepository.getConfigurationByConfigurationId(configurationId);
				if (cmnConfigMstEntity != null) {
					List<CmnLeavePlanConfigDetailsEntity> cmnLeavePlanConfigDetailsList=cmnLeavePlanConfigDetailsRepository.getLeavePlanConfigByConfigurationId(configurationId);
					CmnGeneralLoanConfigDetailsResultSet cmnGeneralLoanConfigDetailsResultSet=cmnGeneralLoanConfigDetailsService.getGeneralLoanConfigDetailsByConfigurationId(configurationId);
					CmnSalarySlabLevelConfigDetailsResultSet cmnSalarySlabLevelConfigDetailsResultSet=cmnSalarySlabLevelConfigDetailsService.getAllsalarySlabDetails(configurationId);
					CmnSalaryAllowanceConfigDetailResultSet cmnSalaryAllowanceConfigDetailResultSet = cmnSalaryAllowanceConfigDetailsService.getAllsalaryAllowanceDetails(configurationId);
					CmnLeaveConfigDetailsResultSet leaveConfigdetailsResultSet=CmnLeaveConfigDetailsService.getLeaveConfigDetailsByConfigurationId(configurationId);
					CmnSalaryEarningsConfigDetailsResultSet cmnSalaryEarningsConfigDetailsResultSet= cmnSalaryEarningsConfigDetailsService.getSalaryEarningsConfigDetailsByConfigId(configurationId);
					CmnDeductionTypesConfigDetailsResultset cmnDeductionTypesConfigDetailsResultset = cmnDeductionTypesConfigDetailsService.getDeductionTypesConfigDetailsByConfigurationId(configurationId);
					CmnSalaryAnnualBenefitsConfigDetailResultSet cmnSalaryAnnualBenefitsConfigDetailResultSet =cmnSalaryAnnualBenefitsConfigDetailsService.getSalaryAnnualTypeconfigDetails(configurationId);
					if(cmnLeavePlanConfigDetailsList!=null && cmnLeavePlanConfigDetailsList.size()>0) {
						cmnConfigMstResultSet.setStatus(false);
						cmnConfigMstResultSet.setMessage("Failed");
						cmnConfigMstResultSet.setMessageDescription("Record cannot be deleted as a child record exists in the Leave Plan Configurations");
						return cmnConfigMstResultSet;
					}
					else if(cmnGeneralLoanConfigDetailsResultSet!=null&&cmnGeneralLoanConfigDetailsResultSet.getCmnGeneralLoanConfigDetailsEntityList()!=null&&cmnGeneralLoanConfigDetailsResultSet.getCmnGeneralLoanConfigDetailsEntityList().size()>0) {
						cmnConfigMstResultSet.setStatus(false);
						cmnConfigMstResultSet.setMessage("Failed");
						cmnConfigMstResultSet.setMessageDescription("Record cannot be deleted as a child record exists in the General Loan Configurations");
						return cmnConfigMstResultSet;
					
					}
					else if(cmnSalarySlabLevelConfigDetailsResultSet!=null&&cmnSalarySlabLevelConfigDetailsResultSet.getCmnSalarySlabLevelConfigDetailsEntityList()!=null&&cmnSalarySlabLevelConfigDetailsResultSet.getCmnSalarySlabLevelConfigDetailsEntityList().size()>0) {
						cmnConfigMstResultSet.setStatus(false);
						cmnConfigMstResultSet.setMessage("Failed");
						cmnConfigMstResultSet.setMessageDescription("Record cannot be deleted as a child record exists in the Salary slab Configurations");
						return cmnConfigMstResultSet;
					}
					else if(cmnSalaryAllowanceConfigDetailResultSet!=null&&cmnSalaryAllowanceConfigDetailResultSet.getCmnSalaryAllowanceConfigDetailsEntityList()!=null&&cmnSalaryAllowanceConfigDetailResultSet.getCmnSalaryAllowanceConfigDetailsEntityList().size()>0) {
						cmnConfigMstResultSet.setStatus(false);
						cmnConfigMstResultSet.setMessage("Failed");
						cmnConfigMstResultSet.setMessageDescription("Record cannot be deleted as a child record exists in the Salary slab Allowance Configurations");
						return cmnConfigMstResultSet;
					}
					else if (leaveConfigdetailsResultSet != null
							&& leaveConfigdetailsResultSet.getCmnLeaveConfigDetailsDTO() != null) {
						CmnLeaveConfigDetailsResultSet leaveconfigResultSet = CmnLeaveConfigDetailsService
								.deleteLeaveConfigDetailsbyConfigurationId(configurationId);
						if (leaveconfigResultSet.getMessage() == "Success") {
							delete(cmnConfigMstEntity);
						} else {
							cmnConfigMstResultSet.setStatus(false);
							cmnConfigMstResultSet.setMessage("Failed");
							cmnConfigMstResultSet.setMessageDescription("Configuration unable to deleted");
						}
						cmnConfigMstResultSet.setStatus(false);
						cmnConfigMstResultSet.setMessage("Failed");
						cmnConfigMstResultSet.setMessageDescription(
								"Record cannot be deleted as a child record exists in the Leave Config Details Configurations");
						return cmnConfigMstResultSet;
					}
					else if (cmnSalaryAnnualBenefitsConfigDetailResultSet != null
							&& cmnSalaryAnnualBenefitsConfigDetailResultSet.getCmnSalaryAnnualBenefitsConfigDetailsEntity()!= null
							&& cmnSalaryAnnualBenefitsConfigDetailResultSet.getCmnSalaryAnnualBenefitsConfigDetailsEntity().size() > 0) {
						CmnSalaryAnnualBenefitsConfigDetailResultSet annualBenefitconfigResultSet = cmnSalaryAnnualBenefitsConfigDetailsService
								.deleteAnnualConfigDetailsbyConfigurationId(configurationId);
						if (annualBenefitconfigResultSet.getMessage() == "Success") {
		
							delete(cmnConfigMstEntity);
						} else {
							cmnConfigMstResultSet.setStatus(false);
							cmnConfigMstResultSet.setMessage("Failed");
							cmnConfigMstResultSet.setMessageDescription("Configuration unable to deleted");
						}
					}
						
					else if (cmnSalaryEarningsConfigDetailsResultSet != null
							&& cmnSalaryEarningsConfigDetailsResultSet
									.getCmnSalaryEarningsConfigDetailsDtoList() != null
							&& cmnSalaryEarningsConfigDetailsResultSet.getCmnSalaryEarningsConfigDetailsDtoList()
									.size() > 0) {
						CmnSalaryEarningsConfigDetailsResultSet cmnSalaryEarningsConfigDetails = cmnSalaryEarningsConfigDetailsService
								.deleteCmnSalaryEarningsConfigDetailsByConfigId(configurationId);
						if (cmnSalaryEarningsConfigDetails.getMessage() == "Success") {
							delete(cmnConfigMstEntity);
							cmnConfigMstResultSet.setMessageDescription("Record deleted successfully");
						} else {
							cmnConfigMstResultSet.setStatus(false);
							cmnConfigMstResultSet.setMessage("Failed");
							cmnConfigMstResultSet.setMessageDescription("Configuration unable to deleted");
						}
						return cmnConfigMstResultSet;
					}
					
					else if (cmnDeductionTypesConfigDetailsResultset != null
							&& cmnDeductionTypesConfigDetailsResultset.getDeductiontypeconfigdetailsdtolist() != null
							&& cmnDeductionTypesConfigDetailsResultset.getDeductiontypeconfigdetailsdtolist().size() > 0) {
						CmnDeductionTypesConfigDetailsResultset deductiontypesconfigResultSet = cmnDeductionTypesConfigDetailsService
								.deleteDeductionTypesConfigDetailsbyConfigurationId(configurationId);
						if (deductiontypesconfigResultSet.getMessage() == "Success") {
							delete(cmnConfigMstEntity);
							cmnConfigMstResultSet.setMessageDescription("Record deleted successfully");
						} else {
							cmnConfigMstResultSet.setStatus(false);
							cmnConfigMstResultSet.setMessage("Failed");
							cmnConfigMstResultSet.setMessageDescription("Configuration unable to deleted");
						}
						return cmnConfigMstResultSet;
					}
					 
					else {
						delete(cmnConfigMstEntity);
						cmnConfigMstResultSet.setMessageDescription("Configuration deleted successfully");
					}
				} else {
					cmnConfigMstResultSet.setStatus(false);
					cmnConfigMstResultSet.setMessage("Failed");
					cmnConfigMstResultSet.setMessageDescription("Invalid Inputs");
				}
			} else {
				cmnConfigMstResultSet.setStatus(false);
				cmnConfigMstResultSet.setMessage("Failed");
				cmnConfigMstResultSet.setMessageDescription("Invalid Inputs");
			}
		} catch (Exception e) {
			cmnConfigMstResultSet.setStatus(false);
			cmnConfigMstResultSet.setMessage("Error");
			cmnConfigMstResultSet.setMessageDescription(e.getMessage());
		}
		return cmnConfigMstResultSet;
	}

	@Override
	public CmnConfigurationsMstResultSet deleteConfigurationsList(List<CmnConfigurationsMstDTO> cmnConfigurationsMstDtoList) {
		CmnConfigurationsMstResultSet cmnConfigurationsMstResultSet = new CmnConfigurationsMstResultSet();
		int successCount = 0;
		int failureCount = 0;
		int count=0;
		try {
			if(cmnConfigurationsMstDtoList != null && cmnConfigurationsMstDtoList.size() > 0) {
				for(CmnConfigurationsMstDTO cmnConfigurationsMstDto : cmnConfigurationsMstDtoList) {
					CmnConfigurationsMstEntity cmnconfigEntity =  cmnConfigurationMstRepository.getConfigurationByConfigurationId((cmnConfigurationsMstDto.getConfigurationId()));
					if (cmnconfigEntity.getConfigurationId() != null) {
						EmployeeAllConfigDetailsResultSet employeeAllConfigDetailsResultSet = null;
						List<CmnLeavePlanConfigDetailsEntity> cmnLeavePlanConfigDetailsList=cmnLeavePlanConfigDetailsRepository.getLeavePlanConfigByConfigurationId(cmnConfigurationsMstDto.getConfigurationId());
						CmnGeneralLoanConfigDetailsResultSet cmnGeneralLoanConfigDetailsResultSet=cmnGeneralLoanConfigDetailsService.getGeneralLoanConfigDetailsByConfigurationId(cmnConfigurationsMstDto.getConfigurationId());
						CmnConfigurationsMstEntity cmnConfigMstEntity = cmnConfigurationMstRepository.getConfigurationByConfigurationId(cmnConfigurationsMstDto.getConfigurationId());
						CmnSalarySlabLevelConfigDetailsResultSet cmnSalarySlabLevelConfigDetailsResultSet=cmnSalarySlabLevelConfigDetailsService.getAllsalarySlabDetails(cmnConfigurationsMstDto.getConfigurationId());
						CmnSalaryAllowanceConfigDetailResultSet cmnSalaryAllowanceConfigDetailResultSet = cmnSalaryAllowanceConfigDetailsService.getAllsalaryAllowanceDetails(cmnConfigurationsMstDto.getConfigurationId());
						CmnLeaveConfigDetailsResultSet leaveConfigdetailsResultSet=CmnLeaveConfigDetailsService.getLeaveConfigDetailsByConfigurationId(cmnConfigurationsMstDto.getConfigurationId());
						CmnSalaryEarningsConfigDetailsResultSet cmnSalaryEarningsConfigDetailsResultSet= cmnSalaryEarningsConfigDetailsService.getSalaryEarningsConfigDetailsByConfigId(cmnConfigurationsMstDto.getConfigurationId());
						CmnDeductionTypesConfigDetailsResultset cmnDeductionTypesConfigDetailsResultset = cmnDeductionTypesConfigDetailsService.getDeductionTypesConfigDetailsByConfigurationId(cmnConfigurationsMstDto.getConfigurationId());
						CmnSalaryAnnualBenefitsConfigDetailResultSet cmnSalaryAnnualBenefitsConfigDetailResultSet =cmnSalaryAnnualBenefitsConfigDetailsService.getSalaryAnnualTypeconfigDetails(cmnConfigurationsMstDto.getConfigurationId());
						if (cmnConfigMstEntity != null) {
							employeeAllConfigDetailsResultSet = employeeAllConfigDetailsService.getEmployeeAllConfigDetailsByConfigurationIdAndConfigTypeLookupId(cmnConfigurationsMstDto.getConfigurationId(), cmnConfigurationsMstDto.getConfigTypeLookupId());
							if (employeeAllConfigDetailsResultSet != null && employeeAllConfigDetailsResultSet.getEmployeeAllConfigDetailsEntityList() != null && employeeAllConfigDetailsResultSet.getEmployeeAllConfigDetailsEntityList().size() > 0) {
								cmnConfigurationsMstResultSet.setStatus(false);
								cmnConfigurationsMstResultSet.setMessage("Failed");
								cmnConfigurationsMstResultSet.setMessageDescription("Record cannot be deleted as a child record exists in the Employee all configurations");
								count++;
							}
							else if (cmnLeavePlanConfigDetailsList!=null && cmnLeavePlanConfigDetailsList.size()>0) {
								cmnConfigurationsMstResultSet.setStatus(false);
								cmnConfigurationsMstResultSet.setMessage("Failed");
								cmnConfigurationsMstResultSet.setMessageDescription("Record cannot be deleted as a child record exists in the Leave Plan Configurations");
								count++;
							}
							else if(cmnGeneralLoanConfigDetailsResultSet!=null && cmnGeneralLoanConfigDetailsResultSet.getCmnGeneralLoanConfigDetailsEntityList()!=null && cmnGeneralLoanConfigDetailsResultSet.getCmnGeneralLoanConfigDetailsEntityList().size()>0) {
								cmnConfigurationsMstResultSet.setStatus(false);
								cmnConfigurationsMstResultSet.setMessage("Failed");
								cmnConfigurationsMstResultSet.setMessageDescription("Record cannot be deleted as a child record exists in the General Loan Configurations");
								count++;
							}
							else if(cmnSalarySlabLevelConfigDetailsResultSet!=null && cmnSalarySlabLevelConfigDetailsResultSet.getCmnSalarySlabLevelConfigDetailsEntityList()!=null && cmnSalarySlabLevelConfigDetailsResultSet.getCmnSalarySlabLevelConfigDetailsEntityList().size()>0) {
								cmnConfigurationsMstResultSet.setStatus(false);
								cmnConfigurationsMstResultSet.setMessage("Failed");
								cmnConfigurationsMstResultSet.setMessageDescription("Record cannot be deleted as a child record exists in the Salary slab Configurations");
								count++;
							}
							else if(cmnSalaryAllowanceConfigDetailResultSet!=null&&cmnSalaryAllowanceConfigDetailResultSet.getCmnSalaryAllowanceConfigDetailsEntityList()!=null&&cmnSalaryAllowanceConfigDetailResultSet.getCmnSalaryAllowanceConfigDetailsEntityList().size()>0) {
								cmnConfigurationsMstResultSet.setStatus(false);
								cmnConfigurationsMstResultSet.setMessage("Failed");
								cmnConfigurationsMstResultSet.setMessageDescription("Record cannot be deleted as a child record exists in the Salary slab Allowance Configurations");
								count++;
							}
							else if(leaveConfigdetailsResultSet!=null&&leaveConfigdetailsResultSet.getCmnLeaveConfigDetailsDTO()!=null) {
								CmnLeaveConfigDetailsResultSet leaveconfigResultSet= CmnLeaveConfigDetailsService.deleteLeaveConfigDetailsbyConfigurationId(cmnConfigurationsMstDto.getConfigurationId());
							    if(leaveconfigResultSet.getMessage()=="Success") {
									delete(cmnConfigMstEntity);
									successCount++;
							    }
							    else {
							    	count++;
							    }
							}
							else if(cmnSalaryAnnualBenefitsConfigDetailResultSet!=null&&cmnSalaryAnnualBenefitsConfigDetailResultSet.getCmnSalaryAnnualBenefitsConfigDetailsEntity()!=null&&cmnSalaryAnnualBenefitsConfigDetailResultSet.getCmnSalaryAnnualBenefitsConfigDetailsEntity().size()>0) {
								CmnSalaryAnnualBenefitsConfigDetailResultSet AnnualBenefitsConfigResultSet= cmnSalaryAnnualBenefitsConfigDetailsService.deleteAnnualConfigDetailsbyConfigurationId(cmnConfigurationsMstDto.getConfigurationId());
							    if(AnnualBenefitsConfigResultSet.getMessage()=="Success") {
									delete(cmnConfigMstEntity);
									successCount++;
							    }
							    else {
							    	count++;
							    }
							}
							else if(cmnSalaryEarningsConfigDetailsResultSet!=null&&cmnSalaryEarningsConfigDetailsResultSet.getCmnSalaryEarningsConfigDetailsDtoList()!=null&&cmnSalaryEarningsConfigDetailsResultSet.getCmnSalaryEarningsConfigDetailsDtoList().size()>0 ) {
								CmnSalaryEarningsConfigDetailsResultSet salaryEarningsResultSet = cmnSalaryEarningsConfigDetailsService
										.deleteCmnSalaryEarningsConfigDetailsByConfigId(cmnConfigurationsMstDto.getConfigurationId());
								if(salaryEarningsResultSet.getMessage()=="Success" ) {
									delete(cmnConfigMstEntity);
									successCount++;
								}
								else {
							    	count++;
							    }
							}
							else if(cmnDeductionTypesConfigDetailsResultset!=null&&cmnDeductionTypesConfigDetailsResultset.getDeductiontypeconfigdetailsdtolist()!=null&&cmnDeductionTypesConfigDetailsResultset.getDeductiontypeconfigdetailsdtolist().size()>0) {
								CmnDeductionTypesConfigDetailsResultset deductiontypesconfigResultSet= cmnDeductionTypesConfigDetailsService.deleteDeductionTypesConfigDetailsbyConfigurationId(cmnConfigurationsMstDto.getConfigurationId());
							    if(deductiontypesconfigResultSet.getMessage()=="Success") {
									delete(cmnConfigMstEntity);
									successCount++;
							    }
							    else {
							    	count++;
							    }
							}
							else {
								delete(cmnConfigMstEntity);
								successCount++;
							}
							if(count>0) {
								failureCount++;
								count=0;
							}
						}
					} else {
						cmnConfigurationsMstResultSet.setStatus(false);
						cmnConfigurationsMstResultSet.setMessage("Failed");
						cmnConfigurationsMstResultSet.setMessageDescription("Invalid Inputs.");
						failureCount++;
					}
				}
			} else {
				cmnConfigurationsMstResultSet.setStatus(false);
				cmnConfigurationsMstResultSet.setMessage("Failed");
				cmnConfigurationsMstResultSet.setMessageDescription("Invalid Inputs.");
				failureCount++;
			}
			if(failureCount > 0) {
				cmnConfigurationsMstResultSet.setMessageDescription(successCount + " rows deleted and failed to delete " + failureCount + " rows.");
			} else {
				cmnConfigurationsMstResultSet.setMessageDescription(successCount + " row deleted successfully.");
			}
			cmnConfigurationsMstResultSet.setSuccessCount(successCount);
			cmnConfigurationsMstResultSet.setFailureCount(failureCount);
		} catch(Exception e) {
			cmnConfigurationsMstResultSet.setStatus(false);
			cmnConfigurationsMstResultSet.setMessage("Error");
			cmnConfigurationsMstResultSet.setMessageDescription("Excepton occured while deleting Configurations. Please contact Administrator");
		}
		return cmnConfigurationsMstResultSet;
	}

	@Override
	public CmnConfigurationsMstResultSet getConfigurationByConfigurationId(Long configurationId) {
		CmnConfigurationsMstResultSet cmnConfigMstResultSet = new CmnConfigurationsMstResultSet();
		try {
			if (configurationId != null) {
				CmnConfigurationsMstEntity cmnConfigMstEntity = cmnConfigurationMstRepository.getConfigurationByConfigurationId(configurationId);
				List<CmnConfigurationsMstEntity> cmnConfigurationsMstEntityList = new ArrayList<CmnConfigurationsMstEntity>();
				if (cmnConfigMstEntity != null)
					cmnConfigurationsMstEntityList.add(cmnConfigMstEntity);
				cmnConfigMstResultSet.setCmnConfigurationsMstEntityList(cmnConfigurationsMstEntityList);
			} else {
				cmnConfigMstResultSet.setStatus(false);
				cmnConfigMstResultSet.setMessage("Failed");
				cmnConfigMstResultSet.setMessageDescription("Invalid Inputs");
			}
		} catch (Exception e) {
			cmnConfigMstResultSet.setStatus(false);
			cmnConfigMstResultSet.setMessage("Error");
			cmnConfigMstResultSet.setMessageDescription(e.getMessage());
		}
		return cmnConfigMstResultSet;
	}

	@Override
	public CmnConfigurationsMstResultSet getConfigurationsByConfigTypeLookupIdAndYearId(String configTypeLookupName, Long yearId) {
		CmnConfigurationsMstResultSet cmnConfigMstResultSet = new CmnConfigurationsMstResultSet();
		try {
			Long configTypeLookupId=cmnLookupMstService.getLookupByLookupName(configTypeLookupName).getCmnLookupMstEntity().getLookupId();
			List<CmnConfigurationsMstEntity> cmnConfigMstEntityList = cmnConfigurationMstRepository.getConfigurationsByConfigTypeLookupIdAndYearId(configTypeLookupId, yearId);
			if (cmnConfigMstEntityList.size() > 0) {
				cmnConfigMstResultSet.setCmnConfigurationsMstEntityList(cmnConfigMstEntityList);
			} else {
				cmnConfigMstResultSet.setStatus(false);
				cmnConfigMstResultSet.setMessage("Warning");
				cmnConfigMstResultSet.setMessageDescription("No configurations available for selected year.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			cmnConfigMstResultSet.setStatus(false);
			cmnConfigMstResultSet.setMessage("Error");
			cmnConfigMstResultSet.setMessageDescription(e.getMessage());
		}
		return cmnConfigMstResultSet;
	}

	@Override
	public CmnConfigurationsMstResultSet getConfigurationsByConfigurationTypeLookupIdAndYearIdAndStatusLookupId(Long configTypeLookupId, Long yearId, Long statusLookupId) {
		CmnConfigurationsMstResultSet cmnConfigMstResultSet = new CmnConfigurationsMstResultSet();
		try {
			if (configTypeLookupId != null && yearId != null && statusLookupId != null) {
				List<CmnConfigurationsMstEntity> cmnConfigMstEntityList = cmnConfigurationMstRepository.getConfigurationsByConfigurationTypeLookupIdAndYearIdAndStatusLookupId(configTypeLookupId, yearId, statusLookupId);
				if (cmnConfigMstEntityList != null) 
					cmnConfigMstResultSet.setCmnConfigurationsMstEntityList(cmnConfigMstEntityList);
			} else {
				cmnConfigMstResultSet.setStatus(false);
				cmnConfigMstResultSet.setMessage("Failed");
				cmnConfigMstResultSet.setMessageDescription("Invalid Inputs");
			}
		} catch (Exception e) {
			cmnConfigMstResultSet.setStatus(false);
			cmnConfigMstResultSet.setMessage("Error");
			cmnConfigMstResultSet.setMessageDescription(e.getMessage());
		}
		return cmnConfigMstResultSet;
	}

	@Override
	public boolean verifyDuplicateConfiguratoinName(CmnConfigurationsMstDTO cmnConfiguration) {
		if(cmnConfiguration != null) {
			CmnConfigurationsMstEntity tempCmnConfig = cmnConfigurationMstRepository.getConfigurationByconfigurationNameAndConfigTypeLookupIdAndYearId(cmnConfiguration.getConfigurationName(), cmnConfiguration.getConfigTypeLookupId(), cmnConfiguration.getYearId());
			if(tempCmnConfig != null && cmnConfiguration.getConfigurationId() != null && cmnConfiguration.getConfigurationId() == 0)
				return true;
			else if(tempCmnConfig != null && cmnConfiguration.getConfigurationId() != null && cmnConfiguration.getConfigurationId() > 0 && tempCmnConfig.getConfigurationId().longValue() != cmnConfiguration.getConfigurationId().longValue())
				return true;
			else 
				return false;
		} else {
			return true;
		}
	}

	@Override
	public CmnConfigurationsMstResultSet getConfigurationsByYearId(Long yearId) {
		CmnConfigurationsMstResultSet cmnConfigMstResultSet = new CmnConfigurationsMstResultSet();
		try {
			if (yearId != null) {
				List<CmnConfigurationsMstEntity> cmnConfigMstList = cmnConfigurationMstRepository.getConfigurationsByYearId(yearId);
				if (cmnConfigMstList != null && cmnConfigMstList.size() > 0) {
					cmnConfigMstResultSet.setCmnConfigurationsMstEntityList(cmnConfigMstList);
				}
			}
		} catch(Exception e) {
			cmnConfigMstResultSet.setCmnConfigurationsMstEntityList(null);
		}
		return cmnConfigMstResultSet;

}
}
