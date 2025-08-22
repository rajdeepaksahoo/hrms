package org.vrnda.hrms.repository.dto;

import lombok.Data;

@Data
public class CmnProjectTeamMembersDTO {

	private Long projectId;

	private Long employeeId;

	private Long teamId;

	private Long statusLookupId;

	private String systemConfigFlag;

	private String isNewRecord;

	private Long projectHead;
	
	private Long scrumMasterId;

	private String scrumMaster;

	private String employeeFullName;
	
	private String projectName;

	private String teamName;

	private String jiraUserName;
	
	private String jiraUserToken;
	
	private Long roleId;
	
	private String roleName;

}
