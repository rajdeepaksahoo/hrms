package org.vrnda.hrms.schedulers;


import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.vrnda.hrms.entity.CmnConfigurationsMstEntity;
import org.vrnda.hrms.entity.CmnLeaveConfigDetailsEntity;
import org.vrnda.hrms.entity.CmnYearsMstEntity;
import org.vrnda.hrms.entity.EmployeeAllConfigDetailsEntity;
import org.vrnda.hrms.entity.EmployeeDetailsEntity;
import org.vrnda.hrms.entity.EmployeeLeavesMstEntity;
import org.vrnda.hrms.repository.CmnConfigurationsMstRepository;
import org.vrnda.hrms.repository.CmnLeaveConfigDetailsRepository;
import org.vrnda.hrms.repository.CmnLeaveTypesMstRepository;
import org.vrnda.hrms.repository.CmnLookupMstRepository;
import org.vrnda.hrms.repository.CmnYearsMstRepository;
import org.vrnda.hrms.repository.EmployeeAllConfigDetailsRepository;
import org.vrnda.hrms.repository.EmployeeDetailsRepository;
import org.vrnda.hrms.repository.EmployeeLeavesMstRepository;
import org.vrnda.hrms.repository.dto.CmnLeaveConfigDetailsDTO;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableScheduling
public class EmployeeLeaveBalanceScheduler {

	@Autowired
	CmnYearsMstRepository cmnYearsMstRepository;

	@Autowired
	CmnLookupMstRepository cmnLookupMstRepository;

	@Autowired
	CmnLeaveTypesMstRepository cmnLeaveTypesMstRepository;

	@Autowired
	EmployeeDetailsRepository employeeDetailsRepository;

	@Autowired
	CmnConfigurationsMstRepository cmnConfigurationMstRepository;

	@Autowired
	EmployeeAllConfigDetailsRepository employeeAllConfigDetailsRepository;

	@Autowired
	CmnLeaveConfigDetailsRepository cmnLeaveConfigDetailsRepository;

	@Autowired
	EmployeeLeavesMstRepository employeeLeaveMstRepository;

	@Autowired
	CmnTableSeqService cmnTableSeqService;

	//	@Scheduled(cron = "0 0 0 1 * ?")
	@Scheduled(cron = "10 * * * * ?")
	public void updateLeaveBalanceOfAllEmployees() {
		Long activeFlagId = null;
		Long leaveConfigTypeId = null;
		Long leaveConfigId = null;
		Long defaultLeaveConfigId = null;
		String year = null;
		Long yearId = null;
		Integer month = null;
		Long noneLookupId = null;
		Long monthlyLookupId = null;
		Long quarterlyLookupId = null;
		Long halfyearlyLookupId = null;
		Long yearlyLookupId = null;
		Long wfhLeaveTypeId = null;
		Long lopLeaveTypeId = null;
		boolean addLeaves = false;

		try {
			Calendar cal = Calendar.getInstance();
			year = String.valueOf(cal.get(Calendar.YEAR));
			//			month = cal.get(Calendar.MONTH)+1;
			month = 1;
			if(year != null) {
				CmnYearsMstEntity cmnYearsMstEntity = cmnYearsMstRepository.getYearByYearName(year);
				if(cmnYearsMstEntity != null) {
					yearId = cmnYearsMstEntity.getYearId();
				}
				activeFlagId = cmnLookupMstRepository.getLookupIdByLookupNameAndParentLookupName(ApplicationConstants.ACTIVE, ApplicationConstants.STATUS);
				leaveConfigTypeId = cmnLookupMstRepository.getLookupIdByLookupNameAndParentLookupName(ApplicationConstants.CMN_LEAVE_CONFIG, ApplicationConstants.CONFIG_TYPE);
				noneLookupId = cmnLookupMstRepository.getLookupIdByLookupNameAndParentLookupName(ApplicationConstants.NONE, ApplicationConstants.LEAVE_DURATION);
				monthlyLookupId = cmnLookupMstRepository.getLookupIdByLookupNameAndParentLookupName(ApplicationConstants.MONTHLY, ApplicationConstants.LEAVE_DURATION);
				quarterlyLookupId = cmnLookupMstRepository.getLookupIdByLookupNameAndParentLookupName(ApplicationConstants.QUARTERLY, ApplicationConstants.LEAVE_DURATION);
				halfyearlyLookupId = cmnLookupMstRepository.getLookupIdByLookupNameAndParentLookupName(ApplicationConstants.HALFYEARLY, ApplicationConstants.LEAVE_DURATION);
				yearlyLookupId = cmnLookupMstRepository.getLookupIdByLookupNameAndParentLookupName(ApplicationConstants.YEARLY, ApplicationConstants.LEAVE_DURATION);
				wfhLeaveTypeId = cmnLeaveTypesMstRepository.getLeaveTypeIdByLeaveTypeName(ApplicationConstants.WFH);
				lopLeaveTypeId = cmnLeaveTypesMstRepository.getLeaveTypeIdByLeaveTypeName(ApplicationConstants.LOP);
				if(yearId != null && activeFlagId != null && leaveConfigTypeId != null && monthlyLookupId != null 
						&& quarterlyLookupId != null && halfyearlyLookupId != null && yearlyLookupId != null) {
					List<CmnConfigurationsMstEntity> cmnConfigurationsMstList  = cmnConfigurationMstRepository.getDefaultConfigurationsByConfigTypeLookupIdAndYearId(leaveConfigTypeId, yearId);
					if(cmnConfigurationsMstList != null && cmnConfigurationsMstList.size() == 1) {
						defaultLeaveConfigId = cmnConfigurationsMstList.get(0).getConfigurationId();
						List<EmployeeDetailsEntity>  employeeDtlsList = employeeDetailsRepository.getAllActiveEmployees(activeFlagId);
						if(employeeDtlsList != null && employeeDtlsList.size() > 0){
							for (EmployeeDetailsEntity employeeDtls : employeeDtlsList) {
								leaveConfigId = null;
								EmployeeAllConfigDetailsEntity employeeAllConfigDetailsEntity = employeeAllConfigDetailsRepository.getEmployeeAllConfigDetailsByEmployeeId(employeeDtls.getEmployeeId());
								if(employeeAllConfigDetailsEntity != null && employeeAllConfigDetailsEntity.getAllConfigs()!= null) {
									String allConfigsString = new String(employeeAllConfigDetailsEntity.getAllConfigs().getBytes(1, (int) employeeAllConfigDetailsEntity.getAllConfigs().length()));
									HashMap<String, Object> workFlowMap = new ObjectMapper().readValue(allConfigsString, HashMap.class);
									if(workFlowMap.containsKey(ApplicationConstants.CMN_LEAVE_CONFIG)) {
										leaveConfigId = Long.valueOf(workFlowMap.get(ApplicationConstants.CMN_LEAVE_CONFIG).toString());
									} else {
										leaveConfigId = defaultLeaveConfigId;
									}
								} else {
									leaveConfigId = defaultLeaveConfigId;
								}
								if(leaveConfigId != null) {
									List<CmnLeaveConfigDetailsEntity> cmnLeaveConfigDetailsList = cmnLeaveConfigDetailsRepository.getLeaveConfigDetailsListByConfigurationId(leaveConfigId);
									if(cmnLeaveConfigDetailsList != null && cmnLeaveConfigDetailsList.size() > 0) {
										for(CmnLeaveConfigDetailsEntity cmnLeaveConfigDetails : cmnLeaveConfigDetailsList) {
											addLeaves = false;
											String leaveConfigs = new String(cmnLeaveConfigDetails.getLeaveConfigs().getBytes(1, (int) cmnLeaveConfigDetails.getLeaveConfigs().length()));
											CmnLeaveConfigDetailsDTO cmnLeaveConfigDetailsDTO = new ObjectMapper().readValue(leaveConfigs, CmnLeaveConfigDetailsDTO.class);
											EmployeeLeavesMstEntity employeeLeaveMst = null;
//											if(!cmnLeaveConfigDetails.getPerDurationLookupId().equals(noneLookupId) && cmnLeaveConfigDetails.getValue() > 0) {
												if(cmnLeaveConfigDetailsDTO.getLeaveConfigs().get(0).getLeaveDurationLookup().equals(monthlyLookupId)) {
													addLeaves = true;
												} else if(cmnLeaveConfigDetailsDTO.getLeaveConfigs().get(0).getLeaveDurationLookup().equals(quarterlyLookupId)) {
													if(month % 3 == 1)
														addLeaves = true;
												} else if(cmnLeaveConfigDetailsDTO.getLeaveConfigs().get(0).getLeaveDurationLookup().equals(halfyearlyLookupId)) {
													if(month % 6 == 1)
														addLeaves = true;
												} else if(cmnLeaveConfigDetailsDTO.getLeaveConfigs().get(0).getLeaveDurationLookup().equals(yearlyLookupId)) {
													if(month % 12 == 1)
														addLeaves = true;
												}
												if(addLeaves) {
													employeeLeaveMst = employeeLeaveMstRepository.getEmployeeLeaveMstByEmployeeIdAndLeaveTypeIdAndYearId(employeeDtls.getEmployeeId(), cmnLeaveConfigDetailsDTO.getLeaveConfigs().get(0).getLeaveTypeId(), yearId);
													if(employeeLeaveMst != null) {
														if((wfhLeaveTypeId != null && wfhLeaveTypeId.equals(cmnLeaveConfigDetailsDTO.getLeaveConfigs().get(0).getLeaveTypeId())) || (lopLeaveTypeId != null && lopLeaveTypeId.equals(cmnLeaveConfigDetailsDTO.getLeaveConfigs().get(0).getLeaveTypeId()))) {
															employeeLeaveMst.setTotalLeaveBalance(Double.valueOf(cmnLeaveConfigDetailsDTO.getLeaveConfigs().get(0).getLeaves()));
														}
														employeeLeaveMst.setTotalLeaveBalance(employeeLeaveMst.getTotalLeaveBalance() + Double.valueOf(cmnLeaveConfigDetailsDTO.getLeaveConfigs().get(0).getLeaves()));
														BaseUtils.modifyBaseData(employeeLeaveMst, "hrms.admin@vrnda.com");
													} else {
														employeeLeaveMst = new EmployeeLeavesMstEntity();
														employeeLeaveMst.setYearId(yearId);
														employeeLeaveMst.setStatusLookupId(activeFlagId);
														employeeLeaveMst.setTotalLeaveBalance(Double.valueOf(cmnLeaveConfigDetailsDTO.getLeaveConfigs().get(0).getLeaves()));
														employeeLeaveMst.setEmployeeId(employeeDtls.getEmployeeId());
														employeeLeaveMst.setEmpLvMstId(cmnTableSeqService.getNextSequence(ApplicationConstants.EMPLOYEE_LEAVES_MST, ApplicationConstants.EMP_LV_MST_ID));
														employeeLeaveMst.setLeaveTypeId(cmnLeaveConfigDetailsDTO.getLeaveConfigs().get(0).getLeaveTypeId());
														BaseUtils.setBaseData(employeeLeaveMst, "hrms.admin@vrnda.com");
													}
												}
//											}
											if(employeeLeaveMst != null) {
												employeeLeaveMstRepository.save(employeeLeaveMst);
											}
										}
									}
								} else {
									System.out.println("Invalid Configurations");
								}
							}
						}
					}
				} else {
					System.out.println("Invalid Configurations");
				}
			} else {
				System.out.println("Invalid Configurations");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
