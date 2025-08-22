package org.vrnda.hrms.service;

import java.util.List;

import org.vrnda.hrms.entity.CmnAppRolesMstEntity;
import org.vrnda.hrms.repository.dto.CmnAppRolesMstDTO;
import org.vrnda.hrms.service.resultset.AppRolesResultSet;

public interface CmnAppRolesMstService extends GenericService<CmnAppRolesMstEntity, Long> {

	public AppRolesResultSet saveOrUpdateAppRole(CmnAppRolesMstDTO cmnAppRolesMstDTO, String loggedInUser);

	public AppRolesResultSet deleteAppRoleByAppRoleId(Long appRoleId);

	public AppRolesResultSet deleteAppRolesList(List<CmnAppRolesMstDTO> cmnAppRoleMstDTOList);

	public AppRolesResultSet getAllAppRoles();

	public AppRolesResultSet getAppRoleByAppRoleId(Long appRoleId);

	public AppRolesResultSet getAppRoleByStatusLookupId(Long statusLookupId);

	public boolean verifyDuplicateRoleName(String appRoleName, Long appRoleId);

}
