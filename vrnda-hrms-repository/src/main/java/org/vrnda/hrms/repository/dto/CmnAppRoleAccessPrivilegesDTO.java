package org.vrnda.hrms.repository.dto;

import lombok.Data;

@Data
public class CmnAppRoleAccessPrivilegesDTO {

	Long appRoleId;

	Long menuId;

	String description;

	String systemConfigFlag;

	Long statusLookupId;

	String menuName;

	String isAccess;
	String menuCode;

}
