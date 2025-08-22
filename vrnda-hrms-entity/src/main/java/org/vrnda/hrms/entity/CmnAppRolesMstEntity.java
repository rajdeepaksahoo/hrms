package org.vrnda.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CMN_APP_ROLES_MST")
public class CmnAppRolesMstEntity extends EntityBaseModel{

	@Id
	@Column(name = "APP_ROLE_ID")
	private Long appRoleId;

	@Column(name = "APP_ROLE_NAME")
	private String  appRoleName;

	@Column(name = "APP_ROLE_DESCRIPTION")
	private String appRoleDescription;

	@Column(name = "SYSTEM_CONFIG_FLAG")
	private String  systemConfigFlag;

}
