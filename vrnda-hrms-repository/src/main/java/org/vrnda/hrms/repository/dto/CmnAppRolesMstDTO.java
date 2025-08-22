package org.vrnda.hrms.repository.dto;

import lombok.Data;

@Data
public class CmnAppRolesMstDTO {

	Long appRoleId;

	String  appRoleName;

	String appRoleDescription;

	String  systemConfigFlag;

	Long statusLookupId;

}
