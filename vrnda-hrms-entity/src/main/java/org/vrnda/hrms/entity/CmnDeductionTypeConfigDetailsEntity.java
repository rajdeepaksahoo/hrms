package org.vrnda.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CMN_SALARY_DEDUCTIONS_CONFIG_DETAILS")
public class CmnDeductionTypeConfigDetailsEntity extends EntityBaseModel {
	@Id
	@Column(name = "CMN_SALARY_DED_CONFIG_DETLS_ID")
	private Long cmnDtConfigDetlsId;

	@Column(name = "CONFIGURATION_ID")
	private Long configurationId;

	@Column(name = "DEDUCTIONS_TYPE_LOOKUP_ID")
	private Long lookupId;

	@Column(name = "VALUE")
	private Double value;

	@Column(name = "CALC_TYPE_ID")
	private Long calTypeLookupId;

	@Column(name = "SYSTEM_CONFIG_FLAG")
	private String systemConfigFlag;
}
