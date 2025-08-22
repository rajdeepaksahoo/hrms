package org.vrnda.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CMN_SALARY_EARNINGS_CONFIG_DETAILS")
public class CmnSalaryEarningsConfigDetailsEntity extends EntityBaseModel {

	@Id
	@Column(name = "CMN_SALARY_EARNINGS_CONFIG_DETLS_ID")
	private Long cmnSalaryEarningsConfigDetailsId;

	@Column(name = "CONFIGURATION_ID")
	private Long configurationId;

	@Column(name = "EARNINGS_TYPE_LOOKUP_ID")
	private Long lookupId;

	@Column(name = "VALUE")
	private Long value;

	@Column(name = "CALC_TYPE_ID")
	private Long calculateTypeId;

	@Column(name = "SYSTEM_CONFIG_FLAG")
	private String systemConfigFlag;
}
