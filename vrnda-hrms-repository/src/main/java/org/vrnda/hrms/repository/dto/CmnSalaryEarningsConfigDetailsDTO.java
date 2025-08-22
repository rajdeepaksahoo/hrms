package org.vrnda.hrms.repository.dto;

import lombok.Data;

@Data
public class CmnSalaryEarningsConfigDetailsDTO {

	Long cmnSalaryEarningsConfigDetailsId;

	Long configurationId;

	Long lookupId;

	Long value;

	Long calculateTypeId;

	Long statusLookupId;

	String systemConfigFlag;
}
