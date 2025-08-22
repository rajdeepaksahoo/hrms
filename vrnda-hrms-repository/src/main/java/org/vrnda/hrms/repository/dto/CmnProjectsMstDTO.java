package org.vrnda.hrms.repository.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CmnProjectsMstDTO {

	Long projectId;
	
	Long teamId;

	String projectName;
	
	String teamName;

	String clientName;

	Date startDate;

	Long statusLookupId;

	String systemConfigFlag;

	Long projectHead;

	String jiraName;

	String organization;

	String jiraURL;

	String projectHeadFullName;
	
	Long scrumMaster;
	


}
