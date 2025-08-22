package org.vrnda.hrms.repository.dto;

import lombok.Data;

@Data
public class LeaveConfigsDTO {

	Long leaveTypeId;
	
	String leaveTypeDescription;

	int leaves;
	
	Long leaveDurationLookup;
	
	int monthlyCarryOver;
	
	int yearlyCarryOver;
	
}
