package org.vrnda.hrms.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "EMPLOYEE_LEAVE_DETAILS")
public class EmployeeLeaveDetailsEntity extends EntityBaseModel  {

	@Id
	@Column(name = "EMPLOYEE_LEAVE_ID")
	private Long employeeLeaveId;

	@Column(name = "EMPLOYEE_ID")
	private Long employeeId;

	@Column(name = "LEAVE_TYPE_ID")
	private Long leaveTypeId;

	@Column(name = "LEAVE_FROM_DATE")
	private Date leaveFromDate;

	@Column(name = "LEAVE_TO_DATE")
	private Date leaveToDate;

	@Column(name = "IS_HALFDAY")
	private String isHalfday;

	@Column(name = "HALF_DAY_LEAVE_SESSION")
	private String halfDayLeaveSession;

	@Column(name = "TOTAL_LEAVE_DAYS")
	private Double totalLeaveDays;

	@Column(name = "REASON")
	private String reason;

	@Column(name = "LEAVE_STATUS_LOOKUP_ID")
	private Long leaveStatusLookupId;

	@Column(name ="PARENT_EMP_LEAVE_ID")
	private Long parentEmpLeaveId;

	@Column( name = "APPROVER_ID")
	private Long approverId;

	@Column( name = "HR_ID")
	private Long hrId;
	
	@Column( name = "YEAR_ID")
	private Long yearId;

	@Column (name ="CHILD_EMPLOYEE_LEAVE_ID")
	private Long childEmpLvId;

}
