package org.vrnda.hrms.repository.dto;

import com.mysql.cj.jdbc.Blob;

import lombok.Data;

@Data
public class EmployeeAllConfigDetailsDTO {

	Long empAllConfigId;

	Long employeeId;

	Long yearId;

	Long statusLookupId;
	
	Blob allConfigs;
	
	String allConfigsbyte;
	
	String allConfigsString;

}

