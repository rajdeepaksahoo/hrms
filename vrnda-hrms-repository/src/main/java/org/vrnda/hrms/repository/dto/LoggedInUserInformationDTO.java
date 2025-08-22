package org.vrnda.hrms.repository.dto;

import lombok.Data;

@Data
public class LoggedInUserInformationDTO {

	String username;

	Long employeeId;

	Long roleId;

	String roleName;

	String lastName;

	String firstName;

	String fullName;
	
	Long userId;

}
