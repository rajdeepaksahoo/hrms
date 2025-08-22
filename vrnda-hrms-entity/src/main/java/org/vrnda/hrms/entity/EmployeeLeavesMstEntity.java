package org.vrnda.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "EMPLOYEE_LEAVES_MST")
public class EmployeeLeavesMstEntity extends EntityBaseModel  {

	@Id
	@Column(name ="EMP_LV_MST_ID")
	private Long empLvMstId;

	@Column(name="EMPLOYEE_ID")
	private Long employeeId;

	@Column(name="LEAVE_TYPE_ID")
	private Long leaveTypeId; 

	@Column(name="TOTAL_LEAVE_BALANCE")
	private Double totalLeaveBalance;
	
	@Column(name="YEAR_ID")
	private Long yearId;

	private Long perDurationLookupId;
}
