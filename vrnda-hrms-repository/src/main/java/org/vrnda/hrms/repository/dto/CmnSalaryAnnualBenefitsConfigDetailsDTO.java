package org.vrnda.hrms.repository.dto;

import lombok.Data;

@Data
public class CmnSalaryAnnualBenefitsConfigDetailsDTO {

	Long cmnSalaryAnnBenConfigDtlsId;

	Long configurationId;

	Long annBenTypeLookupId;

	Long value;
	
	Long calcTypeId;
	 
	String systemConfigFlag;

	Long statusLookupId;
	
}
