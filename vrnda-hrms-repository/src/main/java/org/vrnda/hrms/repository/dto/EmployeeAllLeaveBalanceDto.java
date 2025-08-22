package org.vrnda.hrms.repository.dto;

import lombok.Data;

@Data
public class EmployeeAllLeaveBalanceDto {
	
	Long leaveTypeId;
	
	String leaveName;
	
	String leaveDescription;
	
	Long yearId;
	
	Double totalLeaveBalance;
	
	
}
