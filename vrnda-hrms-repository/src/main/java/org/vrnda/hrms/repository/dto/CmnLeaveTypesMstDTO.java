package org.vrnda.hrms.repository.dto;

import lombok.Data;

@Data
public class CmnLeaveTypesMstDTO {
	
	Long leaveTypeId;

	String leaveTypeName;

	String leaveTypeDescription;

	String systemConfigFlag;

	Long statusLookupId;
	
	String  monthlyCarryOver;
	
	String  yearlyCarryOver;
	
}
