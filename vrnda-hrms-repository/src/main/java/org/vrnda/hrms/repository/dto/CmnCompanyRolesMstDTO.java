package org.vrnda.hrms.repository.dto;

import lombok.Data;

@Data
public class CmnCompanyRolesMstDTO {

	Long companyRoleId;

	String companyRoleName;

	String companyRoleDescription;

	Long companyRoleTypeLookupId;

	Long departmentId;

	Long statusLookupId;
	
	String systemConfigFlag;
	
	String isLeadOrManager;
	
	String isHr;

}
