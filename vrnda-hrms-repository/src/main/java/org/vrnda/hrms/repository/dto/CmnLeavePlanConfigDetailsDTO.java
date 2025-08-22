package org.vrnda.hrms.repository.dto;

import lombok.Data;

@Data
public class CmnLeavePlanConfigDetailsDTO {

	private Long cmnLvPlnConfigDetlsId;

	private Long configurationId;

	private String minValue;

	private String maxValue;

	private String value;

	private Long leaveTypeId; 

	private String systemConfigFlag; 

	private Long statusLookupId;

}
