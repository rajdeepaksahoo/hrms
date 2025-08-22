package org.vrnda.hrms.repository.dto;

import lombok.Data;

@Data
public class CmnSalaryAllowanceConfigDetailsDTO {

	Long cmnSalaryAllowanceConfigDetlsId;

	Long configurationId;

	Long salarySlabLevelId;

	Long allowanceTypeLookupId;

	Long value;
	
	Long calcTypeId;
	 
	String systemConfigFlag;

	Long statusLookupId;

}
