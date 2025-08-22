package org.vrnda.hrms.repository.dto;

import lombok.Data;

@Data
public class CmnProjectTeamsDTO {

	Long projectId;

	Long teamId;

	Long employeeId;
	
	Long projectHead;

	String projectName;

	String teamName;

	Long statusLookupId;

	String systemConfigFlag;

	Long scrumMaster;

}
