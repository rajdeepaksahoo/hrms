package org.vrnda.hrms.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.CmnHolidaysMstEntity;
import org.vrnda.hrms.entity.CmnYearsMstEntity;
import org.vrnda.hrms.repository.CmnHolidaysMstRepository;
import org.vrnda.hrms.repository.dto.CmnHolidaysMstDTO;
import org.vrnda.hrms.service.CmnHolidaysMstService;
import org.vrnda.hrms.service.CmnLookupMstService;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.CmnYearsMstService;
import org.vrnda.hrms.service.resultset.CmnHolidaysMstResultSet;
import org.vrnda.hrms.service.resultset.CmnYearsMstResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class CmnHolidaysMstServiceImpl extends GenericServiceImpl<CmnHolidaysMstEntity, Long> implements CmnHolidaysMstService {

	@Autowired
	CmnHolidaysMstRepository cmnHolidaysMstRepository;

	@Autowired
	CmnTableSeqService cmnTableSeqService;

	@Autowired
	CmnLookupMstService cmnLookupMstService;

	@Autowired
	CmnYearsMstService cmnYearsMstService;

	public CmnHolidaysMstServiceImpl(PagingAndSortingRepository<CmnHolidaysMstEntity, Long> typeRepository) {
		super(typeRepository);
	}

	@Override
	public CmnHolidaysMstResultSet saveOrUpdateHoliday(CmnHolidaysMstDTO cmnHolidaysMstDto, String loggedInUser) {
		CmnHolidaysMstResultSet cmnHolidaysMstRS = new CmnHolidaysMstResultSet();
		try {
			if (cmnHolidaysMstDto != null) {
				if(verifyDuplicateHolidayName(cmnHolidaysMstDto)) {
					cmnHolidaysMstRS.setStatus(false);
					cmnHolidaysMstRS.setMessage("Failed");
					cmnHolidaysMstRS.setMessageDescription("Records already exists with Same Holiday Name for this Year.");
					return cmnHolidaysMstRS;
				}
				if(verifyDuplicateHolidayDate(cmnHolidaysMstDto)) {
					cmnHolidaysMstRS.setStatus(false);
					cmnHolidaysMstRS.setMessage("Failed");
					cmnHolidaysMstRS.setMessageDescription("Records already exists with Same Holiday Date for this Year.");
					return cmnHolidaysMstRS;
				}
				if(cmnHolidaysMstDto.getHolidayId() == 0) {
					CmnHolidaysMstEntity cmnHolidaysMstEntity = new CmnHolidaysMstEntity();
					BeanUtils.copyProperties(cmnHolidaysMstDto, cmnHolidaysMstEntity);
					cmnHolidaysMstEntity.setHolidayId(cmnTableSeqService.getNextSequence(ApplicationConstants.CMN_HOLIDAYS_MST, ApplicationConstants.HOLIDAY_ID));
					BaseUtils.setBaseData(cmnHolidaysMstEntity, loggedInUser);
					save(cmnHolidaysMstEntity);
					cmnHolidaysMstRS.setMessageDescription("Common Holiday saved successfully.");
				} else if(cmnHolidaysMstDto != null && cmnHolidaysMstDto.getHolidayId() > 0) {
					CmnHolidaysMstEntity cmnHolidaysMstEntity = cmnHolidaysMstRepository.getHolidaybyHolidayId(cmnHolidaysMstDto.getHolidayId());
					if(cmnHolidaysMstEntity != null) {
						BeanUtils.copyProperties(cmnHolidaysMstDto, cmnHolidaysMstEntity);
						BaseUtils.modifyBaseData(cmnHolidaysMstEntity, loggedInUser);
						save(cmnHolidaysMstEntity);
						cmnHolidaysMstRS.setMessageDescription("Common Holiday updated successfully.");
					}
				} else {
					cmnHolidaysMstRS.setStatus(false);
					cmnHolidaysMstRS.setMessage("Error");
					cmnHolidaysMstRS.setMessageDescription("Invalid Inputs.");
				}
			}
		} catch (Exception e) {
			cmnHolidaysMstRS.setStatus(false);
			cmnHolidaysMstRS.setMessage("Exception");
			cmnHolidaysMstRS.setMessageDescription("Exception occured while save/update of CommonHoliday.");
		}
		return cmnHolidaysMstRS;
	}

	@Override
	public CmnHolidaysMstResultSet deleteHolidayByHolidayId(Long holidayId) {
		CmnHolidaysMstResultSet cmnHolidaysMstRS = new CmnHolidaysMstResultSet();
		try {
			if(holidayId != null) {
				CmnHolidaysMstEntity cmnHolidaysMstEntity = cmnHolidaysMstRepository.getHolidaybyHolidayId(holidayId);
				delete(cmnHolidaysMstEntity);
				cmnHolidaysMstRS.setMessageDescription("Common Holiday deleted successfully.");
			} else {
				cmnHolidaysMstRS.setStatus(false);
				cmnHolidaysMstRS.setMessage("Failed");
				cmnHolidaysMstRS.setMessageDescription("Invalid Inputs.");
			}
		} catch (Exception e) {
			cmnHolidaysMstRS.setStatus(false);
			cmnHolidaysMstRS.setMessage("Exception");
			cmnHolidaysMstRS.setMessageDescription(e.getMessage());
		}
		return cmnHolidaysMstRS;
	}

	public CmnHolidaysMstResultSet deleteHolidaysList(List<CmnHolidaysMstDTO> holidaysDtoList) {
		CmnHolidaysMstResultSet cmnHolidaysMstRS = new CmnHolidaysMstResultSet();
		int successCount = 0;
		int failureCount = 0;
		try {
			if(holidaysDtoList != null && holidaysDtoList.size() > 0) {
				for(CmnHolidaysMstDTO holidaysDto : holidaysDtoList) {
					CmnHolidaysMstEntity cmnHolidaysMstEntity = cmnHolidaysMstRepository.getHolidaybyHolidayId(holidaysDto.getHolidayId());
					if(cmnHolidaysMstEntity != null) {
						delete(cmnHolidaysMstEntity);
						successCount++;
					} else {
						failureCount++;
					}
				}
				if(failureCount > 0) {
					cmnHolidaysMstRS.setMessageDescription(successCount + " rows deleted and failed to delete " + failureCount + " rows.");
				} else {
					cmnHolidaysMstRS.setMessageDescription(successCount + " row deleted successfully.");
				}
				cmnHolidaysMstRS.setSuccessCount(successCount);
				cmnHolidaysMstRS.setFailureCount(failureCount);
			} else {
				cmnHolidaysMstRS.setStatus(false);
				cmnHolidaysMstRS.setMessage("Failed");
				cmnHolidaysMstRS.setMessageDescription("Invalid Inputs");
			}
		} catch(Exception e) {
			cmnHolidaysMstRS.setStatus(false);
			cmnHolidaysMstRS.setMessage("Exception");
			cmnHolidaysMstRS.setMessageDescription("Excepton occured while deleting the Application Roles. Please contact Administrator");
		}
		return cmnHolidaysMstRS;
	}

	@Override
	public CmnHolidaysMstResultSet getHolidaysByYearId(Long yearId) {
		CmnHolidaysMstResultSet holidaysRS = new CmnHolidaysMstResultSet();
		List<CmnHolidaysMstDTO> holidays =  null;
		try {
			if(yearId != null) {
				holidays = cmnHolidaysMstRepository.getHolidaysByYearId(yearId);
				if (holidays != null && holidays.size() > 0) {
					holidaysRS.setCmnHolidaysMstDTOList(holidays);
				} else {
					holidaysRS.setStatus(false);
					holidaysRS.setMessage("Failed");
					holidaysRS.setMessageDescription("Holidays not found for the given YearId : " + yearId + ".");
				}
			} else {
				holidaysRS.setStatus(false);
				holidaysRS.setMessage("Failed");
				holidaysRS.setMessageDescription("Invalid Inputs: YearId " + yearId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			holidaysRS.setStatus(false);
			holidaysRS.setMessage("Exception");
			holidaysRS.setMessageDescription(e.getMessage());
		}
		return holidaysRS;
	}

	@Override
	public CmnHolidaysMstResultSet getHolidaysByYearIdAndStatusLookupId(Long yearId, Long statusLookupId) {
		CmnHolidaysMstResultSet holidaysRS = new CmnHolidaysMstResultSet();
		List<CmnHolidaysMstDTO> holidays =  null;
		try {
			if(yearId != null) {
				holidays = cmnHolidaysMstRepository.getHolidaysByYearIdAndStatusLookupId(yearId, statusLookupId);
				if (holidays != null && holidays.size() > 0) {
					holidaysRS.setCmnHolidaysMstDTOList(holidays);
				} else {
					holidaysRS.setStatus(false);
					holidaysRS.setMessage("Failed");
					holidaysRS.setMessageDescription("Holidays not found for the given YearId : " + yearId + ".");
				}
			} else {
				holidaysRS.setStatus(false);
				holidaysRS.setMessage("Failed");
				holidaysRS.setMessageDescription("Invalid Inputs: YearId " + yearId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			holidaysRS.setStatus(false);
			holidaysRS.setMessage("Exception");
			holidaysRS.setMessageDescription(e.getMessage());
		}
		return holidaysRS;
	}

	@Override
	public CmnHolidaysMstResultSet getHolidaysCountByYearId(Long yearId) {
		CmnHolidaysMstResultSet holidaysRS = new CmnHolidaysMstResultSet();
		List<CmnHolidaysMstDTO> holidays =  null;
		int weekDayHolidays = 0;
		int weekEndHolidays = 0;
		try {
			if(yearId != null) {
				holidays = cmnHolidaysMstRepository.getHolidaysByYearId(yearId);
				if (holidays != null && holidays.size() > 0) {
					for(CmnHolidaysMstDTO cmnHolidaysMstDto : holidays) {
						if(cmnHolidaysMstDto.getHolidayDate() != null && (cmnHolidaysMstDto.getHolidayDate().getDay() == 0 || cmnHolidaysMstDto.getHolidayDate().getDay() == 6)) {
							weekEndHolidays++;
						} else {
							weekDayHolidays++;
						}
					}
					holidaysRS.setWeekDayHolidays(weekDayHolidays);
					holidaysRS.setWeekEndHolidays(weekEndHolidays);
					holidaysRS.setTotalHolidays(weekDayHolidays + weekEndHolidays);
				} else {
					holidaysRS.setStatus(false);
					holidaysRS.setMessage("Failed");
					holidaysRS.setMessageDescription("Holidays not found for the given YearId : " + yearId + ".");
				}
			} else {
				holidaysRS.setStatus(false);
				holidaysRS.setMessage("Failed");
				holidaysRS.setMessageDescription("Invalid Inputs: YearId " + yearId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			holidaysRS.setStatus(false);
			holidaysRS.setMessage("Exception");
			holidaysRS.setMessageDescription(e.getMessage());
		}
		return holidaysRS;
	}

	@SuppressWarnings("deprecation")
	@Override
	public CmnHolidaysMstResultSet getHolidaysListToCopy(Long yearId, Long holidayTypeLookupId) {
		CmnHolidaysMstResultSet cmnHolidaysMstRS = new CmnHolidaysMstResultSet();
		List<CmnHolidaysMstDTO> yearlyHolidays = null;
		List<CmnHolidaysMstDTO> commonHolidays = null;
		List<CmnHolidaysMstDTO> finalHolidays = new ArrayList<CmnHolidaysMstDTO>();
		String yearName = null;
		boolean status = true;
		boolean duplicate = false;
		Long commonYearId = null;
		Long commonHolidayLookupId = null;
		Long activeFlagId = null;
		try {
			CmnYearsMstEntity cmnYearsMst = cmnYearsMstService.getCmnYearsMstByYearId(yearId);
			if(cmnYearsMst != null) {
				yearName = cmnYearsMst.getYearName();
			}
			activeFlagId = cmnLookupMstService.getLookupIdByLookupNameAndParentLookupName(ApplicationConstants.ACTIVE, ApplicationConstants.STATUS);
			CmnYearsMstResultSet cmnYearsMstResultSet = cmnYearsMstService.getDefaultYearsByYearType(ApplicationConstants.CALENDAR_YEAR);
			if(cmnYearsMstResultSet != null && cmnYearsMstResultSet.getCmnYearsMstDtoList() != null) {
				commonYearId = cmnYearsMstResultSet.getCmnYearsMstDtoList().get(0).getYearId();
				commonHolidayLookupId = cmnLookupMstService.getLookupIdByLookupNameAndParentLookupName(ApplicationConstants.COMMON_HOLIDAY, ApplicationConstants.HOLIDAY_TYPE);
				if(commonYearId != null && commonHolidayLookupId != null) {
					commonHolidays = cmnHolidaysMstRepository.getHolidaysByYearIdAndHolidayTypeLookupId(commonYearId, commonHolidayLookupId, activeFlagId);
					if(commonHolidays != null) {
						if(yearId != null && holidayTypeLookupId != null) {
							yearlyHolidays = cmnHolidaysMstRepository.getHolidaysByYearIdAndHolidayTypeLookupId(yearId, holidayTypeLookupId, activeFlagId);
							if (yearlyHolidays != null && yearlyHolidays.size() > 0) {
								for(CmnHolidaysMstDTO commonHoliday : commonHolidays) {
									duplicate = false;
									for(CmnHolidaysMstDTO yearlyHoliday : yearlyHolidays) {
										if(yearlyHoliday.getHolidayDate().getDate() == commonHoliday.getHolidayDate().getDate() 
												&& yearlyHoliday.getHolidayDate().getMonth() == commonHoliday.getHolidayDate().getMonth()) {
											duplicate = true;
											break;
										}
									}
									if(!duplicate) {
										Calendar calendar = Calendar.getInstance();
										calendar.setTime(commonHoliday.getHolidayDate());
										calendar.set(Integer.parseInt(yearName), commonHoliday.getHolidayDate().getMonth(), commonHoliday.getHolidayDate().getDate());
										commonHoliday.setHolidayDate(calendar.getTime());
										DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
										commonHoliday.setHolidayDateString(dateFormat.format(commonHoliday.getHolidayDate()));
										commonHoliday.setHolidayDate(new SimpleDateFormat("yyyy-MM-dd").parse(commonHoliday.getHolidayDateString()));
										finalHolidays.add(commonHoliday);
									}
								}
							} else {
								for(CmnHolidaysMstDTO commonHoliday : commonHolidays) {								
									Calendar calendar = Calendar.getInstance();
									calendar.setTime(commonHoliday.getHolidayDate());
									calendar.set(Integer.parseInt(yearName), commonHoliday.getHolidayDate().getMonth(), commonHoliday.getHolidayDate().getDate());
									commonHoliday.setHolidayDate(calendar.getTime());
									DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
									commonHoliday.setHolidayDateString(dateFormat.format(commonHoliday.getHolidayDate()));
									commonHoliday.setHolidayDate(new SimpleDateFormat("yyyy-MM-dd").parse(commonHoliday.getHolidayDateString()));
									finalHolidays.add(commonHoliday);
								}
							}
						}
					} else {
						status = false;
					}
				} else {
					status = false;
				}
			} else {
				status = false;
			}
			cmnHolidaysMstRS.setCmnHolidaysMstDTOList(finalHolidays);
		} catch(Exception e) {
			status = false;
		}
		if(!status) { 
			cmnHolidaysMstRS.setStatus(false);
			cmnHolidaysMstRS.setMessage("Error");
			cmnHolidaysMstRS.setMessageDescription("Failed to fetch the Holidays information.");
		}
		return cmnHolidaysMstRS;
	}

	@SuppressWarnings("deprecation")
	@Override
	public CmnHolidaysMstResultSet saveCopiedHolidays(Long yearId, Long holidayTypeLookupId, String loggedInUser) {
		CmnHolidaysMstResultSet cmnHolidaysMstRS = new CmnHolidaysMstResultSet();
		List<CmnHolidaysMstDTO> yearlyHolidays = null;
		List<CmnHolidaysMstDTO> commonHolidays = null;
		String yearName = null;
		boolean status = true;
		boolean duplicate = false;
		Long commonYearId = null;
		Long commonHolidayLookupId = null;
		Long activeFlagId = null;
		try {
			CmnYearsMstEntity cmnYearsMst = cmnYearsMstService.getCmnYearsMstByYearId(yearId);
			if(cmnYearsMst != null) {
				yearName = cmnYearsMst.getYearName();
			}
			activeFlagId = cmnLookupMstService.getLookupIdByLookupNameAndParentLookupName(ApplicationConstants.ACTIVE, ApplicationConstants.STATUS);
			CmnYearsMstResultSet cmnYearsMstResultSet = cmnYearsMstService.getDefaultYearsByYearType(ApplicationConstants.CALENDAR_YEAR);
			if(cmnYearsMstResultSet != null && cmnYearsMstResultSet.getCmnYearsMstDtoList() != null) {
				commonYearId = cmnYearsMstResultSet.getCmnYearsMstDtoList().get(0).getYearId();
				commonHolidayLookupId = cmnLookupMstService.getLookupIdByLookupNameAndParentLookupName(ApplicationConstants.COMMON_HOLIDAY, ApplicationConstants.HOLIDAY_TYPE);
				if(commonYearId != null && commonHolidayLookupId != null) {
					commonHolidays = cmnHolidaysMstRepository.getHolidaysByYearIdAndHolidayTypeLookupId(commonYearId, commonHolidayLookupId, activeFlagId);
					if(commonHolidays != null) {
						if(yearId != null && holidayTypeLookupId != null) {
							yearlyHolidays = cmnHolidaysMstRepository.getHolidaysByYearIdAndHolidayTypeLookupId(yearId, holidayTypeLookupId, activeFlagId);
							if (yearlyHolidays != null && yearlyHolidays.size() > 0) {
								for(CmnHolidaysMstDTO commonHoliday : commonHolidays) {
									duplicate = false;
									for(CmnHolidaysMstDTO yearlyHoliday : yearlyHolidays) {
										if(yearlyHoliday.getHolidayDate().getDate() == commonHoliday.getHolidayDate().getDate() 
												&& yearlyHoliday.getHolidayDate().getMonth() == commonHoliday.getHolidayDate().getMonth()) {
											duplicate = true;
											break;
										}
									}
									if(!duplicate) {
										CmnHolidaysMstEntity cmnHolidaysMst = new CmnHolidaysMstEntity();
										BeanUtils.copyProperties(commonHoliday, cmnHolidaysMst);
										Calendar calendar = Calendar.getInstance();
										calendar.setTime(commonHoliday.getHolidayDate());
										calendar.set(Integer.parseInt(yearName), commonHoliday.getHolidayDate().getMonth(), commonHoliday.getHolidayDate().getDate());
										cmnHolidaysMst.setHolidayDate(calendar.getTime());
										cmnHolidaysMst.setHolidayId(cmnTableSeqService.getNextSequence(ApplicationConstants.CMN_HOLIDAYS_MST, ApplicationConstants.HOLIDAY_ID));
										cmnHolidaysMst.setYearId(yearId);
										cmnHolidaysMst.setHolidayTypeLookupId(holidayTypeLookupId);
										BaseUtils.setBaseData(cmnHolidaysMst, loggedInUser);
										save(cmnHolidaysMst);
									}
								}
							} else {
								for(CmnHolidaysMstDTO commonHoliday : commonHolidays) {								
									CmnHolidaysMstEntity cmnHolidaysMst = new CmnHolidaysMstEntity();
									BeanUtils.copyProperties(commonHoliday, cmnHolidaysMst);
									Calendar calendar = Calendar.getInstance();
									calendar.setTime(commonHoliday.getHolidayDate());
									calendar.set(Integer.parseInt(yearName), commonHoliday.getHolidayDate().getMonth(), commonHoliday.getHolidayDate().getDate());
									cmnHolidaysMst.setHolidayDate(calendar.getTime());
									cmnHolidaysMst.setHolidayId(cmnTableSeqService.getNextSequence(ApplicationConstants.CMN_HOLIDAYS_MST, ApplicationConstants.HOLIDAY_ID));
									cmnHolidaysMst.setYearId(yearId);
									cmnHolidaysMst.setHolidayTypeLookupId(holidayTypeLookupId);
									BaseUtils.setBaseData(cmnHolidaysMst, loggedInUser);
									save(cmnHolidaysMst);
								}
							}
						}
					} else {
						status = false;
					}
				} else {
					status = false;
				}
			} else {
				status = false;
			}
			cmnHolidaysMstRS.setMessageDescription("Common holidays copied successfully to selected year");
		} catch(Exception e) {
			status = false;
		}
		if(!status) { 
			cmnHolidaysMstRS.setStatus(false);
			cmnHolidaysMstRS.setMessage("Error");
			cmnHolidaysMstRS.setMessageDescription("Failed to fetch the Holidays information.");
		}
		return cmnHolidaysMstRS;
	}

	@Override
	public boolean verifyDuplicateHolidayName(CmnHolidaysMstDTO cmnHolidaysMstDto) {
		if(cmnHolidaysMstDto != null) {
			CmnHolidaysMstEntity tempCmnHol = cmnHolidaysMstRepository.getHolidaysByHolidayIdAndNameAndYearIdAndHolidayType(cmnHolidaysMstDto.getHolidayName(), cmnHolidaysMstDto.getYearId(), cmnHolidaysMstDto.getHolidayTypeLookupId());
			if(tempCmnHol != null && cmnHolidaysMstDto.getHolidayId() != null && cmnHolidaysMstDto.getHolidayId() == 0)
				return true;
			else if(tempCmnHol != null && cmnHolidaysMstDto.getHolidayId() != null && cmnHolidaysMstDto.getHolidayId() > 0 && tempCmnHol.getHolidayId().longValue() != cmnHolidaysMstDto.getHolidayId().longValue())
				return true;
			else 
				return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean verifyDuplicateHolidayDate(CmnHolidaysMstDTO cmnHolidaysMstDto) {
		if(cmnHolidaysMstDto != null) {
			CmnHolidaysMstEntity tempCmnHol = cmnHolidaysMstRepository.getHolidaysByHolidayIdAndDateAndYearIdAndHolidayType(cmnHolidaysMstDto.getHolidayDate(), cmnHolidaysMstDto.getYearId(), cmnHolidaysMstDto.getHolidayTypeLookupId());
			if(tempCmnHol != null && cmnHolidaysMstDto.getHolidayId() != null && cmnHolidaysMstDto.getHolidayId() == 0)
				return true;
			else if(tempCmnHol != null && cmnHolidaysMstDto.getHolidayId() != null && cmnHolidaysMstDto.getHolidayId() > 0 && tempCmnHol.getHolidayId().longValue() != cmnHolidaysMstDto.getHolidayId().longValue())
				return true;
			else 
				return false;
		} else {
			return true;
		}
	}

}
