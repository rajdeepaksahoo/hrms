package org.vrnda.hrms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.CmnYearsMstEntity;
import org.vrnda.hrms.repository.CmnYearsMstRepository;
import org.vrnda.hrms.repository.dto.CmnYearsMstDTO;
import org.vrnda.hrms.service.CmnConfigurationsMstService;
import org.vrnda.hrms.service.CmnHolidaysMstService;
import org.vrnda.hrms.service.CmnLookupMstService;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.CmnYearsMstService;
import org.vrnda.hrms.service.EmployeeAllConfigDetailsService;
import org.vrnda.hrms.service.EmployeeLeavesMstService;
import org.vrnda.hrms.service.resultset.CmnYearsMstResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class CmnYearsMstServiceImpl  extends GenericServiceImpl<CmnYearsMstEntity, String> implements CmnYearsMstService {

	public CmnYearsMstServiceImpl(PagingAndSortingRepository<CmnYearsMstEntity, String> typeRepository) {
		super(typeRepository);
	}

	@Autowired
	CmnLookupMstService cmnLookupMstService;

	@Autowired
	CmnYearsMstRepository cmnYearsMstRepository;

	@Autowired
	CmnTableSeqService cmnTableSeqService;

	@Autowired
	CmnHolidaysMstService cmnHolidaysMstService;

	@Autowired
	CmnConfigurationsMstService cmnConfigurationsMstService;

	@Autowired
	EmployeeAllConfigDetailsService employeeAllConfigDetailsService;

	@Autowired
	EmployeeLeavesMstService employeeLeavesMstService;

	public CmnYearsMstResultSet saveOrUpdateYears(CmnYearsMstDTO cmnYearsMstDto, String loggedInUser) {
		CmnYearsMstResultSet cmnYearsMstResultSet = new CmnYearsMstResultSet();
		try {
			if(verifyDuplicateYearName(cmnYearsMstDto)) {
				cmnYearsMstResultSet.setStatus(false);
				cmnYearsMstResultSet.setMessage("Failed");
				cmnYearsMstResultSet.setMessageDescription("Records already exists with Same Year Name for this Year Type.");
				return cmnYearsMstResultSet;
			}
			if (cmnYearsMstDto != null && cmnYearsMstDto.getYearId() == 0) {
				CmnYearsMstEntity cmnYearsMstEntity = new CmnYearsMstEntity();
				BeanUtils.copyProperties(cmnYearsMstDto, cmnYearsMstEntity);
				cmnYearsMstEntity.setYearId(cmnTableSeqService.getNextSequence(ApplicationConstants.CMN_YEARS_MST, ApplicationConstants.YEAR_ID));
				BaseUtils.setBaseData(cmnYearsMstEntity, loggedInUser);
				save(cmnYearsMstEntity);
			} else if (cmnYearsMstDto != null && cmnYearsMstDto.getYearId() > 0) {
				CmnYearsMstEntity cmnYearsMstEntity = cmnYearsMstRepository.getCmnYearsMstByYearId(cmnYearsMstDto.getYearId());
				BeanUtils.copyProperties(cmnYearsMstDto, cmnYearsMstEntity);
				BaseUtils.modifyBaseData(cmnYearsMstEntity, loggedInUser);
				save(cmnYearsMstEntity);
			} else {
				cmnYearsMstResultSet.setStatus(false);
				cmnYearsMstResultSet.setMessage("Error");
				cmnYearsMstResultSet.setMessageDescription("Invalid Inputs");
			}
		} catch (Exception e) {
			cmnYearsMstResultSet.setStatus(false);
			cmnYearsMstResultSet.setMessage("Exception");
			cmnYearsMstResultSet.setMessageDescription(e.getMessage());
		}
		return cmnYearsMstResultSet;
	}

	public CmnYearsMstResultSet deleteYearByYearId(Long yearId) {
		CmnYearsMstResultSet cmnYearsMstResultSet = new CmnYearsMstResultSet();
		try {
			if(yearId != null) {
				if(cmnHolidaysMstService.getHolidaysByYearId(yearId).getCmnHolidaysMstDTOList() != null  ||
						cmnConfigurationsMstService.getConfigurationsByYearId(yearId).getCmnConfigurationsMstEntityList() != null || 
						employeeAllConfigDetailsService.getEmployeeAllConfigDetailsByYearId(yearId).getEmployeeAllConfigDetailsEntityList() != null  ||
						employeeLeavesMstService.getEmployeeLeavesByYearId(yearId).getEmployeeLeavesMstEntityList() != null
						) {
					cmnYearsMstResultSet.setStatus(false);
					cmnYearsMstResultSet.setMessage("Failed");
					cmnYearsMstResultSet.setMessageDescription("This Year is used in other screens and cannot be deleted.");
				} else {
					CmnYearsMstEntity cmnYearsMstEntity = cmnYearsMstRepository.getCmnYearsMstByYearId(yearId);
					if(cmnYearsMstEntity != null) {
						delete(cmnYearsMstEntity);
						cmnYearsMstResultSet.setStatus(true);
						cmnYearsMstResultSet.setMessage("Success");
						cmnYearsMstResultSet.setMessageDescription("Record deleted successfully");
					} else {
						cmnYearsMstResultSet.setStatus(false);
						cmnYearsMstResultSet.setMessage("Failed");
						cmnYearsMstResultSet.setMessageDescription("Invalid Inputs");
					}
				}
			} else {
				cmnYearsMstResultSet.setStatus(false);
				cmnYearsMstResultSet.setMessage("Failed");
				cmnYearsMstResultSet.setMessageDescription("Invalid Inputs");
			}
		} catch(Exception e) {
			cmnYearsMstResultSet.setStatus(false);
			cmnYearsMstResultSet.setMessage("Failed");
			cmnYearsMstResultSet.setMessageDescription("Year cannot be deleted.");
		}
		return cmnYearsMstResultSet;
	}

	public CmnYearsMstResultSet deleteYearsList(List<CmnYearsMstDTO> cmnYearsMstDtoList) {
		CmnYearsMstResultSet cmnYearsMstResultSet = new CmnYearsMstResultSet();
		int successCount = 0;
		int failureCount = 0;
		try {
			if(cmnYearsMstDtoList != null && cmnYearsMstDtoList.size() > 0) {
				for(CmnYearsMstDTO cmnYearsMstDto : cmnYearsMstDtoList) {
					if(cmnHolidaysMstService.getHolidaysByYearId(cmnYearsMstDto.getYearId()).getCmnHolidaysMstDTOList() != null  ||
							cmnConfigurationsMstService.getConfigurationsByYearId(cmnYearsMstDto.getYearId()).getCmnConfigurationsMstEntityList() != null || 
							employeeAllConfigDetailsService.getEmployeeAllConfigDetailsByYearId(cmnYearsMstDto.getYearId()).getEmployeeAllConfigDetailsEntityList() != null  ||
							employeeLeavesMstService.getEmployeeLeavesByYearId(cmnYearsMstDto.getYearId()) != null
							) {
						failureCount++;
					} else {
						CmnYearsMstEntity cmnYearsMstEntity = cmnYearsMstRepository.getCmnYearsMstByYearId(cmnYearsMstDto.getYearId());
						if(cmnYearsMstEntity != null) {
							delete(cmnYearsMstEntity);
							successCount++;
						} else {
							failureCount++;
						}
					}
				}
				if(successCount == 0) {
					cmnYearsMstResultSet.setMessage("Failed");
					cmnYearsMstResultSet.setMessageDescription("Failed to delete " + failureCount + " rows. They are in use on other screens.");
				} else if (failureCount > 0 && successCount > 0){
					cmnYearsMstResultSet.setMessage("Partially Success");
					cmnYearsMstResultSet.setMessageDescription(successCount + " rows deleted and failed to delete " + failureCount + " rows.");
				} else {
					cmnYearsMstResultSet.setMessageDescription(successCount + " row deleted successfully.");
				}
				cmnYearsMstResultSet.setSuccessCount(successCount);
				cmnYearsMstResultSet.setFailureCount(failureCount);
			} else {
				cmnYearsMstResultSet.setStatus(false);
				cmnYearsMstResultSet.setMessage("Failed");
				cmnYearsMstResultSet.setMessageDescription("Invalid Inputs");
			}
		} catch(Exception e) {
			cmnYearsMstResultSet.setStatus(false);
			cmnYearsMstResultSet.setMessage("Exception");
			cmnYearsMstResultSet.setMessageDescription("Excepton occured while deleting the Bands. Please contact Administrator");
		}
		return cmnYearsMstResultSet;
	}

	public CmnYearsMstResultSet getYearsByYearType(String yearType) {
		Long yearTypeLookupId = null;
		Boolean resultStatus = true;
		List<CmnYearsMstDTO> cmnYearsMstDtoFinalList = new ArrayList<CmnYearsMstDTO>(); 
		CmnYearsMstResultSet cmnYearsMstResultSet = new CmnYearsMstResultSet();
		try {
			yearTypeLookupId = cmnLookupMstService.getLookupIdByLookupNameAndParentLookupName(yearType, ApplicationConstants.YEAR_TYPE);
			if(yearTypeLookupId != null) {
				List<CmnYearsMstDTO> cmnYearsMstList = cmnYearsMstRepository.getYearsByYearTypeLookupId(yearTypeLookupId);
				for(CmnYearsMstDTO cmnYearsMst : cmnYearsMstList) {
					if(!(cmnYearsMst.getYearName().equalsIgnoreCase(ApplicationConstants.DEFAULT_CALENDER_YEAR) || cmnYearsMst.getYearName().equalsIgnoreCase(ApplicationConstants.DEFAULT_FINANCIAL_YEAR))) {
						cmnYearsMstDtoFinalList.add(cmnYearsMst);
					}
				}
				if(cmnYearsMstDtoFinalList != null && cmnYearsMstDtoFinalList.size() > 0) {
					cmnYearsMstResultSet.setCmnYearsMstDtoList(cmnYearsMstDtoFinalList);
				} else {
					resultStatus = false;
				}
			} else {
				resultStatus = false;
			}
			if(!resultStatus){
				cmnYearsMstResultSet.setStatus(false);
				cmnYearsMstResultSet.setMessage("Error");
				cmnYearsMstResultSet.setMessageDescription("Unable to find or No data exists for this Year Type.");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return cmnYearsMstResultSet;
	}

	public CmnYearsMstResultSet getDefaultYearsByYearType(String yearType) {
		Long yearTypeLookupId = null;
		Boolean resultStatus = true;
		List<CmnYearsMstDTO> cmnYearsMstDtoFinalList = new ArrayList<CmnYearsMstDTO>(); 
		CmnYearsMstResultSet cmnYearsMstResultSet = new CmnYearsMstResultSet();
		try {
			yearTypeLookupId = cmnLookupMstService.getLookupIdByLookupNameAndParentLookupName(yearType, ApplicationConstants.YEAR_TYPE);
			if(yearTypeLookupId != null) {
				List<CmnYearsMstDTO> cmnYearsMstList = cmnYearsMstRepository.getYearsByYearTypeLookupId(yearTypeLookupId);
				for(CmnYearsMstDTO cmnYearsMst : cmnYearsMstList) {
					if(cmnYearsMst.getYearName().equalsIgnoreCase(ApplicationConstants.DEFAULT_CALENDER_YEAR) || cmnYearsMst.getYearName().equalsIgnoreCase(ApplicationConstants.DEFAULT_FINANCIAL_YEAR)) {
						cmnYearsMstDtoFinalList.add(cmnYearsMst);
					}
				}
				if(cmnYearsMstDtoFinalList != null && cmnYearsMstDtoFinalList.size() > 0) {
					cmnYearsMstResultSet.setCmnYearsMstDtoList(cmnYearsMstDtoFinalList);
				} else {
					resultStatus = false;
				}
			} else {
				resultStatus = false;
			}
			if(!resultStatus){
				cmnYearsMstResultSet.setStatus(false);
				cmnYearsMstResultSet.setMessage("Error");
				cmnYearsMstResultSet.setMessageDescription("Unable to find or No data exists for this Year Type.");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return cmnYearsMstResultSet;
	}

	@Override
	public CmnYearsMstResultSet getYearIdForCurrentYear(String yearName) {
		CmnYearsMstEntity cmnYearsMstEntity = null;
		CmnYearsMstResultSet cmnYearsMstRS = new CmnYearsMstResultSet();
		try {
			if(yearName != null) {
				cmnYearsMstEntity = cmnYearsMstRepository.getYearByYearName(yearName);
				if (cmnYearsMstEntity != null) {
					cmnYearsMstRS.setCmnYearsMstEntity(cmnYearsMstEntity);
				} else {
					cmnYearsMstEntity = cmnYearsMstRepository.getYearForMaxYear();
					if(cmnYearsMstEntity != null) {
						cmnYearsMstRS.setCmnYearsMstEntity(cmnYearsMstEntity);
					} else {
						cmnYearsMstRS.setStatus(false);
						cmnYearsMstRS.setMessage("Error");
						cmnYearsMstRS.setMessageDescription("No Year Ids Found");
					}
				}
			} 
		} catch (Exception e) {
			cmnYearsMstRS.setStatus(false);
			cmnYearsMstRS.setMessage("Exception");
			cmnYearsMstRS.setMessageDescription(e.getMessage());
		}
		return cmnYearsMstRS;
	}

	@Override
	public CmnYearsMstEntity getCmnYearsMstByYearId(Long yearId) {
		return cmnYearsMstRepository.getCmnYearsMstByYearId(yearId);
	}

	public boolean verifyDuplicateYearName(CmnYearsMstDTO cmnYearsMstDto) {
		if(cmnYearsMstDto != null) {
			CmnYearsMstEntity tempCmnYear = cmnYearsMstRepository.getYearsByYearNameAndYearType(cmnYearsMstDto.getYearName(), cmnYearsMstDto.getYearTypeLookupId());
			if(tempCmnYear != null && cmnYearsMstDto.getYearId() != null && cmnYearsMstDto.getYearId() == 0)
				return true;
			else if(tempCmnYear != null && cmnYearsMstDto.getYearId() != null && cmnYearsMstDto.getYearId() > 0 && tempCmnYear.getYearId().longValue() != cmnYearsMstDto.getYearId().longValue())
				return true;
			else 
				return false;
		} else {
			return true;
		}
	}
	
	@Override
	public CmnYearsMstResultSet deleteAllYearsList(List<CmnYearsMstDTO> cmnYearsMstDtoList) {
		CmnYearsMstResultSet cmnYearsMstResultSet = new CmnYearsMstResultSet();
		try {
			if (cmnYearsMstDtoList != null && cmnYearsMstDtoList.size() > 0) {
				List<CmnYearsMstEntity> cmnYearsMstEntityList = new ArrayList<CmnYearsMstEntity>();

				for (CmnYearsMstDTO cmnYearsMstDto : cmnYearsMstDtoList) {

					if (cmnHolidaysMstService.getHolidaysByYearId(cmnYearsMstDto.getYearId())
							.getCmnHolidaysMstDTOList() == null
							&& cmnConfigurationsMstService.getConfigurationsByYearId(cmnYearsMstDto.getYearId())
									.getCmnConfigurationsMstEntityList() == null
							&& employeeAllConfigDetailsService
									.getEmployeeAllConfigDetailsByYearId(cmnYearsMstDto.getYearId())
									.getEmployeeAllConfigDetailsEntityList() == null
							&& employeeLeavesMstService.getEmployeeLeavesByYearId(cmnYearsMstDto.getYearId())
									.getEmployeeLeavesMstEntityList() == null) {
						CmnYearsMstEntity cmnYearsMstEntity = cmnYearsMstRepository
								.getCmnYearsMstByYearId(cmnYearsMstDto.getYearId());
						cmnYearsMstEntityList.add(cmnYearsMstEntity);
					}
				}

				if (cmnYearsMstDtoList.size() != cmnYearsMstEntityList.size()) {
					cmnYearsMstResultSet.setStatus(false);
					cmnYearsMstResultSet.setMessage("Failed");
					cmnYearsMstResultSet.setMessageDescription("Failed to delete because child records found");
				} else {
					deleteAll(cmnYearsMstEntityList);
					cmnYearsMstResultSet.setStatus(true);
					cmnYearsMstResultSet
							.setMessageDescription(cmnYearsMstEntityList.size() + " row deleted successfully.");
				}
			} else {
				cmnYearsMstResultSet.setStatus(false);
				cmnYearsMstResultSet.setMessage("Failed");
				cmnYearsMstResultSet.setMessageDescription("Invalid Inputs");
			}
		} catch (Exception e) {
			cmnYearsMstResultSet.setStatus(false);
			cmnYearsMstResultSet.setMessage("Exception");
			cmnYearsMstResultSet
					.setMessageDescription("Excepton occured while deleting the Years. Please contact Administrator");
		}
		return cmnYearsMstResultSet;
	}

}
