package org.vrnda.hrms.repository.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class EmployeeLeaveDetailsDTO {

	Long employeeLeaveId;

	String employeeFullName;

	Long employeeId;

	Long leaveTypeId;

	String lvTypeName;

	Date leaveFromDate;

	Date leaveToDate;

	String isHalfday;

	String halfDayLeaveSession;

	Double totalLeaveDays;

	String reason;

	Long leaveStatusLookupId;

	Long parentEmpLeaveId;

	String approvalStatus;

	Long approverId;

	Long hrId;

	Long childEmpLvId;

	Double usedleaves;

	Double lopLeaves;

	Long statusLookupId;
	
	String employeeFirstName;
	
	String employeeLastName;
	
	String isLop;
	
	Double availableLeaves;
	
	String Designation;

}
