package org.vrnda.hrms.repository.dto;

import lombok.Data;

@Data
public class CmnConfigurationsMstDTO  {

	Long configurationId;

	String configurationName;

	String configurationDescription;
	
	String defaultConfig;

	Long configTypeLookupId;

	String systemConfigFlag;

	Long yearId;

	String configTypeLookupName;

	private Long statusLookupId;
	
	String isOverride;
	
	Long previousConfigId;

}
