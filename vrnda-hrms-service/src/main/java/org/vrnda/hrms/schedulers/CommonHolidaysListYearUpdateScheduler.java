package org.vrnda.hrms.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.vrnda.hrms.repository.CmnHolidaysMstRepository;
import org.vrnda.hrms.repository.CmnLookupMstRepository;

@Configuration
@EnableScheduling
public class CommonHolidaysListYearUpdateScheduler {

	@Autowired
	CmnHolidaysMstRepository cmnHolidaysMstRepository;

	@Autowired
	CmnLookupMstRepository cmnLookupMstRepository;
	
//	@Scheduled(cron = "0 0 0 1 1 ?")
//	public void updateLeaveBalanceOfAllEmployees() {
//		Long commonHolidaysLookupId = null;
//		List<HolidaysEntity> holidaysList = null;
//		try {
//			CmnLookupMstEntity cmnLookupMst = cmnLookupMstRepository.getCmnLookupDetails("COMMON");
//			if(cmnLookupMst != null) {
//				commonHolidaysLookupId = cmnLookupMst.getLookupId();
//			}
//			if(commonHolidaysLookupId != null) {
//				holidaysList = holidaysRepository.getCommonHolidaysListForUpdate(commonHolidaysLookupId);
//				if(holidaysList != null && holidaysList.size() > 0) {
//					holidaysList.forEach( (holiday) ->{
//						Date holidateDate = holiday.getHolidayDate();
//						Date todayDate = new Date();
//						if(holidateDate != null) {
//							holidateDate.setYear(todayDate.getYear());
//						}
//				    });  
//				}
//				holidaysRepository.saveAll(holidaysList);
//			}
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//	}

	
}
