package org.vrnda.hrms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CMN_YEARS_MST")
public class CmnYearsMstEntity extends EntityBaseModel{

	@Id
	@Column(name= "YEAR_ID")
	private Long yearId;

	@Column(name= "YEAR_NAME")
	private String yearName;

	@Column(name= "YEAR_START_DATE")
	private Date yearStartDate;

	@Column(name= "YEAR_END_DATE")
	private Date yearEndDate;

	@Column(name= "APPLICABLE_FROM")
	private Date applicableFrom;

	@Column(name= "YEAR_TYPE_LOOKUP_ID")
	private Long yearTypeLookupId;

	@Column(name = "SYSTEM_CONFIG_FLAG")
	private String  systemConfigFlag;

}
