package org.vrnda.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CMN_LEAVE_PLAN_CONFIG_DETAILS")
public class CmnLeavePlanConfigDetailsEntity extends EntityBaseModel{

	@Id
	@Column(name = "CMN_LV_PLN_CONFIG_DETLS_ID")
	private Long cmnLvPlnConfigDetlsId;

	@Column(name = "CONFIGURATION_ID")
	private Long configurationId;

	@Column(name = "MIN_VALUE")
	private String minValue;

	@Column(name = "MAX_VALUE")
	private String maxValue;

	@Column(name = "VALUE")
	private String value;

	@Column(name = "LEAVE_TYPE_ID")
	private Long leaveTypeId; 

	@Column(name = "SYSTEM_CONFIG_FLAG")
	private String systemConfigFlag; 
}
