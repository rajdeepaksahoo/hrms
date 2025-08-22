package org.vrnda.hrms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CMN_HOLIDAYS_MST")
public class CmnHolidaysMstEntity extends EntityBaseModel {

	@Id
	@Column(name = "HOLIDAY_ID")
	private Long holidayId;

	@Column(name = "YEAR_ID")
	private Long yearId;

	@Column(name = "HOLIDAY_DATE")
	private Date holidayDate;

	@Column(name = "HOLIDAY_NAME")
	private String holidayName;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "HOLIDAY_TYPE_LOOKUP_ID")
	private Long holidayTypeLookupId;

	@Column(name = "SYSTEM_CONFIG_FLAG")
	private String  systemConfigFlag;

}
