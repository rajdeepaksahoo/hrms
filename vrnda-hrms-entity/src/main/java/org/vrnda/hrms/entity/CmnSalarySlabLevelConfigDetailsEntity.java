package org.vrnda.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CMN_SALARY_SLAB_LEVEL_CONFIG_DETAILS")
public class CmnSalarySlabLevelConfigDetailsEntity extends EntityBaseModel {

	@Id
	@Column(name = "CMN_SALARY_SLAB_LEVEL_CONFIG_DETLS_ID")
	private Long cmnSalarySlabLevelConfigDetlsId;

	@Column(name = "CONFIGURATION_ID")
	private Long configurationId;

	@Column(name = "SLAB_LEVEL")
	private Long slabLevel;

	@Column(name = "MIN_SALARY")
	private Long minSalary;

	@Column(name = "MAX_SALARY")
	private Long maxSalary;

	@Column(name = "SYSTEM_CONFIG_FLAG")
	private String  systemConfigFlag;

}
