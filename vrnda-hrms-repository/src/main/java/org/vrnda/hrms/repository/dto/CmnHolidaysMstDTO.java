package org.vrnda.hrms.repository.dto;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

@Data
public class CmnHolidaysMstDTO {

	Long holidayId;

	Long yearId;

	Date holidayDate;

	String holidayDateString;

	String holidayName;

	String description;

	String systemConfigFlag;

	Long statusLookupId;

	Long holidayTypeLookupId;

	public CmnHolidaysMstDTO(Long holidayId, Long yearId, Date holidayDate, String holidayName, String description, Long statusLookupId, String systemConfigFlag, Long holidayTypeLookupId, Date createdDate) {
		this.holidayId = holidayId;
		this.yearId = yearId;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			this.holidayDateString = dateFormat.format(holidayDate);
			this.holidayDate = new SimpleDateFormat("yyyy-MM-dd").parse(this.holidayDateString);
		} catch (ParseException e) {
			this.holidayDate = null;
			e.printStackTrace();
		}
		this.holidayName = holidayName;
		this.description = description;
		this.statusLookupId = statusLookupId;
		this.systemConfigFlag = systemConfigFlag;
		this.holidayTypeLookupId = holidayTypeLookupId;
	}

	public CmnHolidaysMstDTO() {

	}

}
