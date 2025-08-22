package org.vrnda.hrms.repository.dto;

import lombok.Data;

@Data
public class CmnSalarySlabLevelConfigDetailsDTO {

	Long cmnSalarySlabLevelConfigDetlsId;

	Long configurationId;

	Long slabLevel;

	Long minSalary;

	Long maxSalary;

	String systemConfigFlag;

	Long statusLookupId;

}
