package org.vrnda.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CMN_COMPANY_ROLES_MST")
public class CmnCompanyRolesMstEntity extends EntityBaseModel {
	@Id
	@Column(name = "COMPANY_ROLE_ID")
	private Long companyRoleId;

	@Column(name = "COMPANY_ROLE_NAME")
	private String companyRoleName;

	@Column(name = "COMPANY_ROLE_DESCRIPTION")
	private String companyRoleDescription;

	@Column(name = "COMPANY_ROLE_TYPE_LOOKUP_ID")
	private Long companyRoleTypeLookupId;

	@Column(name = "DEPARTMENT_ID")
	private Long departmentId;
	
	@Column(name = "SYSTEM_CONFIG_FLAG")
	private String systemConfigFlag;
	
	@Column(name = "IS_LEAD_OR_MANAGER")
	private String isLeadOrManager;
	
	@Column(name = "IS_HR")
	private String isHr;

}
