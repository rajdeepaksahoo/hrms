package org.vrnda.hrms.repository.dto;

import java.util.List;

import lombok.Data;

@Data
public class CmnLeaveConfigDetailsDTO {

	Long configurationId;

	List<LeaveConfigsDTO> leaveConfigs;
	
	Long statusLookupId;
	
	String systemConfigFlag;
	
}
