//package org.vrnda.hrms.schedulers;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.apache.commons.lang3.time.DateUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.vrnda.hrms.entity.EmployeeLeaveDetailsEntity;
//import org.vrnda.hrms.entity.EmployeeLeavePlanApprovalsMappingEntity;
//import org.vrnda.hrms.entity.LeaveApprovalConfigMstEntity;
//import org.vrnda.hrms.repository.CmnLookupMstRepository;
//import org.vrnda.hrms.repository.EmployeeLeaveDetailsRepository;
//import org.vrnda.hrms.repository.EmployeeLeavePlanApprovalsMappingRepository;
//import org.vrnda.hrms.service.LeaveApprovalConfigMstService;
//import org.vrnda.hrms.utils.BaseUtils;
//import org.vrnda.hrms.utils.commutils.MailDto;
//
//@Configuration
//@EnableScheduling
//public class EmailSchedulers {
//
//	@Autowired
//	EmployeeLeaveDetailsRepository employeeLeaveDetailsRepository;
//
//	@Autowired
//	EmployeeLeavePlanApprovalsMappingRepository employeeLeavePlanApprovalsMappingRepository;
//
//	@Autowired
//	LeaveApprovalConfigMstService leaveApprovalCOnfigService;
//
//	@Autowired
//	CmnLookupMstRepository cmnLookupMstRepository;
//
//	@Scheduled(cron = "0 0 * * * ?")
//	public void triggerLeaveEmails() {
//		List<EmployeeLeaveDetailsEntity> leavesList = new ArrayList<EmployeeLeaveDetailsEntity>();
//		List<LeaveApprovalConfigMstEntity> leaveApprovalConfigList = new ArrayList<LeaveApprovalConfigMstEntity>();
//		Integer level;
//		Long statusLookupId = null;
//		try {
//			statusLookupId = cmnLookupMstRepository.getActiveLookupIdFromCmnLookupMst();
//			leavesList = employeeLeaveDetailsRepository.getLeavesForMail(statusLookupId);
//			for (EmployeeLeaveDetailsEntity leave : leavesList) {
//				EmployeeLeavePlanApprovalsMappingEntity empLvPlApprMapEntity = new EmployeeLeavePlanApprovalsMappingEntity();
//				empLvPlApprMapEntity = employeeLeavePlanApprovalsMappingRepository.getEmployeeLeavePlanApprovalsMappingByEmployeeId(leave.getEmployeeId());
//				leaveApprovalConfigList = leaveApprovalCOnfigService.getLvApprovalRecordWithConfigIdAndLevel(empLvPlApprMapEntity.getLvApprovalConfigId(), leave.getLvTypeLookupId()).getLeaveApprovalConfigMstEntity();
//				Integer duration = 0;
//				level = 0;
//				for (LeaveApprovalConfigMstEntity obj : leaveApprovalConfigList) {
//					if (obj.getLevel() <= leave.getApprovalLevel()) {
//						duration += obj.getDuration();
//						level = obj.getLevel();
//					}
//				}
//				Date dateTosendMail = (DateUtils.addHours(leave.getCreatedDate(), duration));
//				if (dateTosendMail != null && dateTosendMail.compareTo(new Date()) <= 0) {
//					List<LeaveApprovalConfigMstEntity> lvapprConf = (leaveApprovalConfigList.stream()
//							.filter(i -> i.getLevel() == leave.getApprovalLevel() + 1)
//							.collect(Collectors.toList()));
//					;
//					if (lvapprConf != null && lvapprConf.size() > 0) {
//						LeaveApprovalConfigMstEntity temp = lvapprConf.get(0);
//						leave.setApprovalLevel(level + 1);
//						if (temp.getMoveToNext() == 'Y') {
//							if (leave.getLevel1ApproverId().equals(empLvPlApprMapEntity.getApprover1Id())) {
//								leave.setLevel1ApproverId(empLvPlApprMapEntity.getApprover2Id());
//								BaseUtils.modifyBaseData(leave);
//								MailDto m = new MailDto();
//								m.setSenderMail("yogendranath.mandadapu@vrnda.com");
//								//no-reply@vrnda.com 
//								m.setPassword("Mandadapu!06092021");
//								m.setMessage("Test Mail from Java");
//								m.setCcMail("rakesh.narra@vrnda.com");
//								m.setRecieverMail("prashanth.kempu@vrnda.com");
//								//								MailUtility.leaveApplicationMail(m);
//							}
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//			
//		}
//	}
//
//}
