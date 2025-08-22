package org.vrnda.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CMN_SALARY_ANNUAL_BENEFITS_CONFIG_DETAILS")
public class CmnSalaryAnnualBenefitsConfigDetailsEntity  extends EntityBaseModel {

	@Id
	@Column(name = "CMN_SALARY_ANN_BEN_CONFIG_DTLS_ID")
	private Long cmnSalaryAnnBenConfigDtlsId;

	@Column(name = "CONFIGURATION_ID")
	private Long configurationId;

	@Column(name = "ANN_BEN_TYPE_LOOKUP_ID")
	private Long annBenTypeLookupId;
	
	@Column(name = "CALC_TYPE_ID")
	private Long calcTypeId;

	@Column(name = "VALUE")
	private Long value;
	
	@Column(name = "SYSTEM_CONFIG_FLAG")
	private String  systemConfigFlag;
	
}
