package org.vrnda.hrms.repository.dto;

import lombok.Data;

@Data
public class CmnDeductionTypesConfigDetailsDTO {
	
		Long cmnDtConfigDetlsId;

		Long configurationId;

		Long lookupId;

		Double value;

		Long calTypeLookupId;

		String systemConfigFlag;
		
		Long statusLookupId;

}
