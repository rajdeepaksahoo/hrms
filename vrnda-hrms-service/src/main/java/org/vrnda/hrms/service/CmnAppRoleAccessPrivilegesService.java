package org.vrnda.hrms.service;

import java.util.List;

import org.vrnda.hrms.entity.CmnAppRoleAccessPrivilegesCompositeKey;
import org.vrnda.hrms.entity.CmnAppRoleAccessPrivilegesEntity;
import org.vrnda.hrms.repository.dto.CmnAppRoleAccessPrivilegesDTO;
import org.vrnda.hrms.service.resultset.RoleAccessPrivilegesResultSet;

public interface CmnAppRoleAccessPrivilegesService
		extends GenericService<CmnAppRoleAccessPrivilegesEntity, CmnAppRoleAccessPrivilegesCompositeKey> {

	public RoleAccessPrivilegesResultSet saveOrUpdateRoleAccessAccessprivileges(
			List<CmnAppRoleAccessPrivilegesDTO> cmnAppRoleAccessPrivilegesDTOList, String loggedInUser);

	public RoleAccessPrivilegesResultSet getRoleAccessPrivilegesByRole(Long roleId);

	public RoleAccessPrivilegesResultSet deleteRoleAccessbyRoleId(Long roleId);

	public RoleAccessPrivilegesResultSet getRoleAccessPrivilegesRoels(Long roleId);

	public RoleAccessPrivilegesResultSet saveOrUpdateRoleAccessAccessprivilegesChild(
			List<CmnAppRoleAccessPrivilegesDTO> cmnAppRoleAccessPrivilegesDTOList, String loggedInUser);
}
