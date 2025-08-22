package org.vrnda.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CMN_LEAVE_TYPES_MST")
public class CmnLeaveTypesMstEntity  extends EntityBaseModel {
	@Id
	@Column(name= "LEAVE_TYPE_ID")
	private Long leaveTypeId;

	@Column(name= "LEAVE_TYPE_NAME")
	private String leaveTypeName;

	@Column(name= "LEAVE_TYPE_DESCRIPTION")
	private String leaveTypeDescription;

	@Column(name = "SYSTEM_CONFIG_FLAG")
	private String  systemConfigFlag;
	
	@Column(name = "MONTHLY_CARRYOVER")
	private String  monthlyCarryOver;
	
	@Column(name = "YEARLY_CARRYOVER")
	private String  yearlyCarryOver;

	@Column(name = "STATUS_LOOKUP_ID")
	private Long statusLookupId;

	@Column(name = "YEAR_ID")
	private Long yearId;

}
