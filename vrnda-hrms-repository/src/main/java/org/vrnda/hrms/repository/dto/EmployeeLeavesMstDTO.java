package org.vrnda.hrms.repository.dto;

import lombok.Data;

@Data
public class EmployeeLeavesMstDTO {

	Long empLvId;

	Long employeeId;

	Long leaveTypeId; 

	Double totalLeaveBalance;
	
	String statusLookupId;
	
	Boolean lvBalChange;
	
	Double noOfDays;
	
	Long yearId;

}
