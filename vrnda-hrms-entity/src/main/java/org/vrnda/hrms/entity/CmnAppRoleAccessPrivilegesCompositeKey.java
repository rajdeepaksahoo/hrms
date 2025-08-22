package org.vrnda.hrms.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class CmnAppRoleAccessPrivilegesCompositeKey implements Serializable {

	private Long appRoleId;

	private Long menuId;

}
