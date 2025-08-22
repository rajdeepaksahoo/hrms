package org.vrnda.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CMN_CONFIGURATIONS_MST")
public class CmnConfigurationsMstEntity extends EntityBaseModel {

	@Id
	@Column(name = "CONFIGURATION_ID")
	private Long configurationId;

	@Column(name = "CONFIGURATION_NAME")
	private String configurationName;

	@Column(name = "CONFIGURATION_DESCRIPTION")
	private String configurationDescription;

	@Column(name = "CONFIG_TYPE_LOOKUP_ID")
	private Long configTypeLookupId;

	@Column(name = "SYSTEM_CONFIG_FLAG")
	private String systemConfigFlag;

	@Column(name = "YEAR_ID")
	private Long yearId;
	
	@Column(name = "DEFAULT_CONFIG")
	private String defaultConfig;

}
