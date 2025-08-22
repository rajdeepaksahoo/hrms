package org.vrnda.hrms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.CmnSalaryAllowanceConfigDetailsEntity;
import org.vrnda.hrms.repository.CmnConfigurationsMstRepository;
import org.vrnda.hrms.repository.CmnSalaryAllowanceConfigDetailsRepository;
import org.vrnda.hrms.repository.dto.CmnSalaryAllowanceConfigDetailsDTO;
import org.vrnda.hrms.service.CmnSalaryAllowanceConfigDetailsService;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.resultset.CmnSalaryAllowanceConfigDetailResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class CmnSalaryAllowanceConfigDetailsServiceImpl
		extends GenericServiceImpl<CmnSalaryAllowanceConfigDetailsEntity, Long>
		implements CmnSalaryAllowanceConfigDetailsService {

	public CmnSalaryAllowanceConfigDetailsServiceImpl(
			PagingAndSortingRepository<CmnSalaryAllowanceConfigDetailsEntity, Long> typeRepository) {
		super(typeRepository);
		// TODO Auto-generated constructor stub
	}

	@Autowired
	CmnSalaryAllowanceConfigDetailsRepository allowanceLevelRepository;

	@Autowired
	CmnConfigurationsMstRepository cmnConfigurationMstRepository;

	@Autowired
	CmnTableSeqService cmnTableSeqService;

	@Override
	public CmnSalaryAllowanceConfigDetailResultSet getAllsalaryAllowanceDetails(Long configurationId) {
		CmnSalaryAllowanceConfigDetailResultSet cmnSalaryAllowanceConfigDetailResultSet = new CmnSalaryAllowanceConfigDetailResultSet();
		try {
			if (configurationId != null) {
				List<CmnSalaryAllowanceConfigDetailsEntity> cmnSalaryAllowanceList = allowanceLevelRepository
						.getAllowanceLevelDetailsById(configurationId);
				List<CmnSalaryAllowanceConfigDetailsDTO> cmnSalaryAllowancDtoList = new ArrayList<CmnSalaryAllowanceConfigDetailsDTO>();
				if (cmnSalaryAllowanceList.size() > 0) {
					cmnSalaryAllowanceList.forEach(obj -> {
						CmnSalaryAllowanceConfigDetailsDTO allowanceconfigdto = new CmnSalaryAllowanceConfigDetailsDTO();
						BeanUtils.copyProperties(obj, allowanceconfigdto);
						cmnSalaryAllowancDtoList.add(allowanceconfigdto);
					});

					cmnSalaryAllowanceConfigDetailResultSet
							.setCmnSalaryAllowanceConfigDetailsDtoList(cmnSalaryAllowancDtoList);
				}
			} else {
				cmnSalaryAllowanceConfigDetailResultSet.setStatus(false);
				cmnSalaryAllowanceConfigDetailResultSet.setMessage("Failed");
				cmnSalaryAllowanceConfigDetailResultSet.setMessageDescription("Salary Allowance is Empty");
			}
		} catch (Exception e) {
			cmnSalaryAllowanceConfigDetailResultSet.setStatus(false);
			cmnSalaryAllowanceConfigDetailResultSet.setMessage("Error");
			cmnSalaryAllowanceConfigDetailResultSet.setMessageDescription(e.getMessage());
		}
		return cmnSalaryAllowanceConfigDetailResultSet;

	}

	@Override
	public CmnSalaryAllowanceConfigDetailResultSet saveOrUpdatesalaryAllowances(
			CmnSalaryAllowanceConfigDetailsDTO cmnSalaryAllowancDto, String loggedInUser) {
		CmnSalaryAllowanceConfigDetailResultSet cmnSalaryAllowanceConfigDetailResultSet = new CmnSalaryAllowanceConfigDetailResultSet();
		try {
			if (cmnSalaryAllowancDto != null) {
				if (verifyDuplicateInSalaryAllowance(cmnSalaryAllowancDto)) {
					cmnSalaryAllowanceConfigDetailResultSet.setStatus(false);
					cmnSalaryAllowanceConfigDetailResultSet.setMessage("Failed");
					cmnSalaryAllowanceConfigDetailResultSet
							.setMessageDescription("SalaryAllowance already exists with same configuration.");
					return cmnSalaryAllowanceConfigDetailResultSet;
				}
				if (cmnSalaryAllowancDto.getCmnSalaryAllowanceConfigDetlsId() == 0) {
					CmnSalaryAllowanceConfigDetailsEntity salaryallowanceEntity = new CmnSalaryAllowanceConfigDetailsEntity();
					BeanUtils.copyProperties(cmnSalaryAllowancDto, salaryallowanceEntity);
					salaryallowanceEntity.setCmnSalaryAllowanceConfigDetlsId(
							cmnTableSeqService.getNextSequence(ApplicationConstants.CMN_SALARY_ALLOWANCE_CONFIG_DETAILS,
									ApplicationConstants.CMN_SALARY_ALLOWANCE_CONFIG_DETLS_ID));
					BaseUtils.setBaseData(salaryallowanceEntity, loggedInUser);
					save(salaryallowanceEntity);
					cmnSalaryAllowanceConfigDetailResultSet.setMessageDescription("Record saved successfully.");
				} else if (cmnSalaryAllowancDto != null
						&& cmnSalaryAllowancDto.getCmnSalaryAllowanceConfigDetlsId() > 0) {
					CmnSalaryAllowanceConfigDetailsEntity salaryallowanceEntity = allowanceLevelRepository
							.getCmnsalaryAllowanceBycmnsalarydetails(
									cmnSalaryAllowancDto.getCmnSalaryAllowanceConfigDetlsId());
					BeanUtils.copyProperties(cmnSalaryAllowancDto, salaryallowanceEntity);
					BaseUtils.modifyBaseData(salaryallowanceEntity, loggedInUser);
					save(salaryallowanceEntity);
					cmnSalaryAllowanceConfigDetailResultSet.setMessageDescription("Record updated successfully.");
				}
			} else {
				cmnSalaryAllowanceConfigDetailResultSet.setStatus(false);
				cmnSalaryAllowanceConfigDetailResultSet.setMessage("Error");
				cmnSalaryAllowanceConfigDetailResultSet.setMessageDescription("Invalid Inputs.");
			}

		} catch (Exception e) {
			cmnSalaryAllowanceConfigDetailResultSet.setStatus(false);
			cmnSalaryAllowanceConfigDetailResultSet.setMessage("Exception");
			cmnSalaryAllowanceConfigDetailResultSet.setMessageDescription(e.getMessage());
		}
		return cmnSalaryAllowanceConfigDetailResultSet;
	}

	public boolean verifyDuplicateInSalaryAllowance(CmnSalaryAllowanceConfigDetailsDTO cmnSalaryAllowancDto) {
		if (cmnSalaryAllowancDto != null) {
			CmnSalaryAllowanceConfigDetailsEntity tempSalaryslab = allowanceLevelRepository.getCmnsalaryAllowance(
					cmnSalaryAllowancDto.getAllowanceTypeLookupId(), cmnSalaryAllowancDto.getSalarySlabLevelId(),
					cmnSalaryAllowancDto.getValue(), cmnSalaryAllowancDto.getConfigurationId());
			if (tempSalaryslab != null && cmnSalaryAllowancDto.getCmnSalaryAllowanceConfigDetlsId() != null
					&& cmnSalaryAllowancDto.getCmnSalaryAllowanceConfigDetlsId() == 0)
				return true;
			else if (tempSalaryslab != null && cmnSalaryAllowancDto.getCmnSalaryAllowanceConfigDetlsId() != null
					&& cmnSalaryAllowancDto.getCmnSalaryAllowanceConfigDetlsId() > 0
					&& tempSalaryslab.getCmnSalaryAllowanceConfigDetlsId().longValue() != cmnSalaryAllowancDto
							.getCmnSalaryAllowanceConfigDetlsId().longValue())
				return true;
			else
				return false;
		} else {
			return true;
		}
	}

	@Override
	public CmnSalaryAllowanceConfigDetailResultSet deleteSalarySlabBycmnSalaryAllowanceConfigDetlsId(
			Long cmnSalaryAllowanceConfigDetlsId) {
		CmnSalaryAllowanceConfigDetailResultSet cmnSalaryAllowanceResultSet = new CmnSalaryAllowanceConfigDetailResultSet();
		try {
			if (cmnSalaryAllowanceConfigDetlsId != null) {
				CmnSalaryAllowanceConfigDetailsEntity salaryallowanceEntity = allowanceLevelRepository
						.getSalarySlabLevelByCmnSalarySlabAllowanceConfigDetlsId(cmnSalaryAllowanceConfigDetlsId);
				delete(salaryallowanceEntity);
				cmnSalaryAllowanceResultSet.setMessageDescription("Salary Slab Allowance deleted successfully.");
			} else {
				cmnSalaryAllowanceResultSet.setStatus(false);
				cmnSalaryAllowanceResultSet.setMessage("Failed");
				cmnSalaryAllowanceResultSet.setMessageDescription("Invalid Inputs.");
			}
		} catch (Exception e) {
			cmnSalaryAllowanceResultSet.setStatus(false);
			cmnSalaryAllowanceResultSet.setMessage("Exception");
			cmnSalaryAllowanceResultSet.setMessageDescription(e.getMessage());
		}
		return cmnSalaryAllowanceResultSet;
	}

	@Override
	public CmnSalaryAllowanceConfigDetailResultSet deleteSalarySlabsAllowance(
			List<CmnSalaryAllowanceConfigDetailsDTO> cmnSalaryAllowancDto) {
		CmnSalaryAllowanceConfigDetailResultSet cmnSalaryAllowanceResultSet = new CmnSalaryAllowanceConfigDetailResultSet();
		int successCount = 0;
		int failureCount = 0;
		try {
			if (cmnSalaryAllowancDto != null && cmnSalaryAllowancDto.size() > 0) {
				for (CmnSalaryAllowanceConfigDetailsDTO salarySlabAllowance : cmnSalaryAllowancDto) {
					CmnSalaryAllowanceConfigDetailsEntity salaryallowanceEntity = allowanceLevelRepository
							.getSalarySlabLevelByCmnSalarySlabAllowanceConfigDetlsId(
									salarySlabAllowance.getCmnSalaryAllowanceConfigDetlsId());
					if (salaryallowanceEntity != null) {
						delete(salaryallowanceEntity);
						successCount++;
					} else {
						failureCount++;
					}
				}
				if (failureCount > 0) {
					cmnSalaryAllowanceResultSet.setMessageDescription(
							successCount + " rows deleted and failed to delete " + failureCount + " rows.");
				} else {
					cmnSalaryAllowanceResultSet.setMessageDescription(successCount + " row deleted successfully.");
				}
				cmnSalaryAllowanceResultSet.setSuccessCount(successCount);
				cmnSalaryAllowanceResultSet.setFailureCount(failureCount);
			} else {
				cmnSalaryAllowanceResultSet.setStatus(false);
				cmnSalaryAllowanceResultSet.setMessage("Failed");
				cmnSalaryAllowanceResultSet.setMessageDescription("Invalid Inputs");
			}
		} catch (Exception e) {
			cmnSalaryAllowanceResultSet.setStatus(false);
			cmnSalaryAllowanceResultSet.setMessage("Exception");
			cmnSalaryAllowanceResultSet.setMessageDescription(
					"Excepton occured while deleting the Salary Slab Allowance. Please contact Administrator");
		}
		return cmnSalaryAllowanceResultSet;
	}

	@Override
	public CmnSalaryAllowanceConfigDetailResultSet saveOrUpdateAllowances(
			List<CmnSalaryAllowanceConfigDetailsDTO> cmnSalaryAllowancDto, String loggedInUser) {
		CmnSalaryAllowanceConfigDetailResultSet cmnSalaryAllowanceConfigDetailResultSet = new CmnSalaryAllowanceConfigDetailResultSet();
		try {
			if (cmnSalaryAllowancDto != null && cmnSalaryAllowancDto.size() > 0) {
				for (CmnSalaryAllowanceConfigDetailsDTO cmnAllowancDto : cmnSalaryAllowancDto) {
					if (cmnAllowancDto.getCmnSalaryAllowanceConfigDetlsId() == null) {
						CmnSalaryAllowanceConfigDetailsEntity salaryallowanceEntity = new CmnSalaryAllowanceConfigDetailsEntity();
						BeanUtils.copyProperties(cmnAllowancDto, salaryallowanceEntity);
						salaryallowanceEntity.setCmnSalaryAllowanceConfigDetlsId(cmnTableSeqService.getNextSequence(
								ApplicationConstants.CMN_SALARY_ALLOWANCE_CONFIG_DETAILS,
								ApplicationConstants.CMN_SALARY_ALLOWANCE_CONFIG_DETLS_ID));
						BaseUtils.setBaseData(salaryallowanceEntity, loggedInUser);
						save(salaryallowanceEntity);
						cmnSalaryAllowanceConfigDetailResultSet.setMessageDescription("Record saved successfully.");
					} else if (cmnAllowancDto != null && cmnAllowancDto.getCmnSalaryAllowanceConfigDetlsId() > 0) {
						CmnSalaryAllowanceConfigDetailsEntity allowanceEntity = allowanceLevelRepository
								.getSalarySlabLevelByCmnSalarySlabAllowanceConfigDetlsId(
										cmnAllowancDto.getCmnSalaryAllowanceConfigDetlsId());
						if (allowanceEntity != null) {
							BeanUtils.copyProperties(cmnAllowancDto, allowanceEntity);
							BaseUtils.modifyBaseData(allowanceEntity, loggedInUser);
							save(allowanceEntity);
							cmnSalaryAllowanceConfigDetailResultSet.setMessageDescription("Record saved successfully.");
						}
					} else {
						cmnSalaryAllowanceConfigDetailResultSet.setStatus(false);
						cmnSalaryAllowanceConfigDetailResultSet.setMessage("Error");
						cmnSalaryAllowanceConfigDetailResultSet
								.setMessageDescription("Unable to save annualType Configurations");
					}
				}

			}
		} catch (Exception e) {
			cmnSalaryAllowanceConfigDetailResultSet.setStatus(false);
			cmnSalaryAllowanceConfigDetailResultSet.setMessage("Exception");
			cmnSalaryAllowanceConfigDetailResultSet.setMessageDescription(e.getMessage());
		}
		return cmnSalaryAllowanceConfigDetailResultSet;
	}

	@Override
	public CmnSalaryAllowanceConfigDetailResultSet getAllsalaryAllowanceDetailsBycnfgIdAndLevelId(Long configurationId,
			Long cmnSalarySlabLevelConfigDetlsId) {
		CmnSalaryAllowanceConfigDetailResultSet cmnSalaryAllowanceConfigDetailResultSet = new CmnSalaryAllowanceConfigDetailResultSet();
		try {
			if (configurationId != null) {
				List<CmnSalaryAllowanceConfigDetailsEntity> cmnSalaryAllowanceList = allowanceLevelRepository
						.getAllsalaryAllowanceDetailsBycnfgIdAndLevelId(configurationId,
								cmnSalarySlabLevelConfigDetlsId);
				List<CmnSalaryAllowanceConfigDetailsDTO> cmnSalaryAllowancDtoList = new ArrayList<CmnSalaryAllowanceConfigDetailsDTO>();
				if (cmnSalaryAllowanceList.size() > 0) {
					cmnSalaryAllowanceList.forEach(obj -> {
						CmnSalaryAllowanceConfigDetailsDTO allowanceconfigdto = new CmnSalaryAllowanceConfigDetailsDTO();
						BeanUtils.copyProperties(obj, allowanceconfigdto);
						cmnSalaryAllowancDtoList.add(allowanceconfigdto);
					});

					cmnSalaryAllowanceConfigDetailResultSet
							.setCmnSalaryAllowanceConfigDetailsDtoList(cmnSalaryAllowancDtoList);
				}
			} else {
				cmnSalaryAllowanceConfigDetailResultSet.setStatus(false);
				cmnSalaryAllowanceConfigDetailResultSet.setMessage("Failed");
				cmnSalaryAllowanceConfigDetailResultSet.setMessageDescription("Salary Allowance is Empty");
			}
		} catch (Exception e) {
			cmnSalaryAllowanceConfigDetailResultSet.setStatus(false);
			cmnSalaryAllowanceConfigDetailResultSet.setMessage("Error");
			cmnSalaryAllowanceConfigDetailResultSet.setMessageDescription(e.getMessage());
		}
		return cmnSalaryAllowanceConfigDetailResultSet;

	}

}
