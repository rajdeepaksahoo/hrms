package org.vrnda.hrms.repository.dto;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

@Data
public class CmnYearsMstDTO {

	Long yearId;

	String yearName;

	Date yearStartDate;

	String yearStartDateString;

	Date yearEndDate;

	String yearEndDateString;

	Date applicableFrom;

	String applicableFromString;

	Long yearTypeLookupId;

	Long statusLookupId;

	String systemConfigFlag;

	public CmnYearsMstDTO(Long yearId, String yearName, Date yearStartDate, Date yearEndDate, Date applicableFrom, Long yearTypeLookupId, Long statusLookupId, String systemConfigFlag, Date createdDate) {
		this.yearId = yearId;
		this.yearName = yearName;
		this.yearStartDate = yearStartDate;
		this.yearEndDate = yearEndDate;
		this.applicableFrom = applicableFrom;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			this.applicableFromString = dateFormat.format(applicableFrom);
			this.applicableFrom = new SimpleDateFormat("yyyy-MM-dd").parse(this.applicableFromString);

			this.yearStartDateString = dateFormat.format(yearStartDate);
			this.yearStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(this.yearStartDateString);

			this.yearEndDateString = dateFormat.format(yearEndDate);
			this.yearEndDate = new SimpleDateFormat("yyyy-MM-dd").parse(this.yearEndDateString);
		} catch (ParseException e) {
			this.applicableFrom = null;
			this.yearStartDate = null;
			this.yearEndDate = null;
			e.printStackTrace();
		}
		this.yearTypeLookupId = yearTypeLookupId;
		this.statusLookupId = statusLookupId;
		this.systemConfigFlag = systemConfigFlag;
	}

	public CmnYearsMstDTO() {

	}

}
