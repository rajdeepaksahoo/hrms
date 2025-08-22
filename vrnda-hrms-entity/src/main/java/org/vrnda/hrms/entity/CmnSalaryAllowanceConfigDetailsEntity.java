package org.vrnda.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CMN_SALARY_ALLOWANCE_CONFIG_DETAILS")
public class CmnSalaryAllowanceConfigDetailsEntity extends EntityBaseModel {

	@Id
	@Column(name = "CMN_SALARY_ALLOWANCE_CONFIG_DETLS_ID")
	private Long cmnSalaryAllowanceConfigDetlsId;

	@Column(name = "CONFIGURATION_ID")
	private Long configurationId;

	@Column(name = "SALARY_SLAB_LEVEL_ID")
	private Long salarySlabLevelId;

	@Column(name = "ALLOWANCE_TYPE_LOOKUP_ID")
	private Long allowanceTypeLookupId;
	
	@Column(name = "CALC_TYPE_ID")
	private Long calcTypeId;

	@Column(name = "VALUE")
	private Long value;
	
	@Column(name = "SYSTEM_CONFIG_FLAG")
	private String  systemConfigFlag;
	
	

}
