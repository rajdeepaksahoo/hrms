package org.vrnda.hrms.service;

import java.util.List;

import org.vrnda.hrms.repository.dto.CmnCompanyRolesMstDTO;
import org.vrnda.hrms.service.resultset.CmnCompanyRolesMstResultSet;

public interface CmnCompanyRolesMstService {

	public CmnCompanyRolesMstResultSet saveOrUpdateCompanyRole(CmnCompanyRolesMstDTO cmnCompanyRoleMstDto, String loggedInUser);

	public CmnCompanyRolesMstResultSet deleteCompanyRoleByRoleId(Long companyRoleId);

	public CmnCompanyRolesMstResultSet deleteCompanyRolesList(List<CmnCompanyRolesMstDTO> cmnCompanyRoleMstDtoList);

	public CmnCompanyRolesMstResultSet getAllCompanyRoles();

	CmnCompanyRolesMstResultSet getAllCompanyRolesByStatusLookupId(Long statusLookupId);

	public CmnCompanyRolesMstResultSet getCompanyRoleByCompanyRoleId(Long companyRoleId);

	public CmnCompanyRolesMstResultSet getCompanyRolesByStatusLookupId(Long statusLookupId);

	public boolean verifyDuplicateRoleName(String roleName, Long roleId);

	public CmnCompanyRolesMstResultSet getCompanyRolesByLeadOrManager();

	public CmnCompanyRolesMstResultSet getCompanyRolesByHr();

	public CmnCompanyRolesMstResultSet getCompanyRolesByStatusLookupIdAndManager(Long statusLookupId);

	public CmnCompanyRolesMstResultSet getCompanyRolesByStatusLookupIdAndHr(Long statusLookupId);

	public CmnCompanyRolesMstResultSet getCompanyRoleIdByRoleName(String companyRoleName);

	public CmnCompanyRolesMstResultSet getCompanyRolesByStatusLookupIdAndDepartmentId(Long statusLookupId, Long departMentID);

}
