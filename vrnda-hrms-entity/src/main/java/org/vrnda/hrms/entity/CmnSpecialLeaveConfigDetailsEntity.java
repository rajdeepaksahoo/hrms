package org.vrnda.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CMN_SPECIAL_LEAVE_CONFIG_DETAILS")
public class CmnSpecialLeaveConfigDetailsEntity  extends EntityBaseModel  {

	@Id
	@Column(name = "CMN_SPECIAL_LV_CONFIG_DETLS_ID")
	private Long cmnSpecialLvConfigDetlsId;

	@Column(name = "CONFIGURATION_ID")
	private Long configurationId;

	@Column(name = "SPL_LV_TYPE_LOOKUP_ID")
	private Long splLvTypeLookupId;

	@Column(name = "VALUE")
	private String value;

	@Column(name = "PER_DURATION_LOOKUP_ID")
	private Long perDurationLookupId;

	@Column(name = "SYSTEM_CONFIG_FLAG")
	private String systemConfigFlag;

}

