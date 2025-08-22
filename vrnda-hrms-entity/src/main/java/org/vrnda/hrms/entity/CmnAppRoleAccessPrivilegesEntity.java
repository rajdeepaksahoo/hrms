package org.vrnda.hrms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@IdClass(CmnAppRoleAccessPrivilegesCompositeKey.class)
@Table(name = "CMN_APP_ROLE_ACCESS_PRIVILEGES")
public class CmnAppRoleAccessPrivilegesEntity extends EntityBaseModel implements Serializable {

	@Id
	@Column(name = "APP_ROLE_ID")
	private Long appRoleId;

	@Id
	@Column(name = "MENU_ID")
	private Long menuId;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "SYSTEM_CONFIG_FLAG")
	private String systemConfigFlag;

}
