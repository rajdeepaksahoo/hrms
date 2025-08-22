package org.vrnda.hrms.repository.dto;

import java.util.Date;

import lombok.Data;

@Data
public class AppUsersMstDTO {

	Long userId;

	String username;

	Long appRoleId;

	Long employeeId;

	Long activateFlag;

	char alwaysLogin;

	char firstLogin;

	Integer invalidLoginCount;

	String ipAddress;

	String ipLogin;

	String password;

	Date passwordChangedDate;

	Long secretQuestionId;

	String secretQuestionOther;

	String secretAnswer;

	String  systemConfigFlag;

	Long statusLookupId;
	
	String employeeFullName;
	
	String appRoleName;
	
	String newPassword;
	
	String currentPassword;

	public AppUsersMstDTO(Long userId, Long employeeId, String username, Long appRoleId, Long statusLookupId, String systemConfigFlag) {
		this.userId = userId;
		this.employeeId = employeeId;
		this.username = username;
		this.appRoleId = appRoleId;
		this.statusLookupId = statusLookupId;
		this.systemConfigFlag = systemConfigFlag;		
	}

}
