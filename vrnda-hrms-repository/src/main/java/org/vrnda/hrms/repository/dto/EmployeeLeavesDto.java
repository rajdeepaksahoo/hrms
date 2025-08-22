package org.vrnda.hrms.repository.dto;

import java.util.List;

import lombok.Data;
@Data
public class EmployeeLeavesDto {

	Long employeeId;

	String employeeFullName;

	List<LeaveDetailsDTO> leaveDetailsList;
	
//		public EmployeeLeavesDto(String employeeFirstName, String employeeMiddleName,
//			String employeeLastName) {
//		//this.employeeId = employeeId;
//		this.employeeFullName = (employeeFirstName != null ? employeeFirstName + " " : "")
//				+ (employeeMiddleName != null ? employeeMiddleName + " " : "")
//				+ (employeeLastName != null ? employeeLastName : "");
//	}


}
