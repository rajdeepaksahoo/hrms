package org.vrnda.hrms.service;

import org.vrnda.hrms.entity.AppUsersMstEntity;
import org.vrnda.hrms.repository.dto.AppUsersMstDTO;
import org.vrnda.hrms.service.resultset.AllUserDetailsResultSet;
import org.vrnda.hrms.service.resultset.AppUsersMstResultSet;

public interface AppUsersMstService extends GenericService<AppUsersMstEntity, String> {

	public AppUsersMstResultSet doLogin(String userName, String password);

	public AppUsersMstResultSet saveOrUpdateAppUser(AppUsersMstDTO appUsersMstDTO, String loggedInUser);

	public AppUsersMstResultSet getAppUserByUserId(Long appUserId);

	public AppUsersMstResultSet deleteAppUserByAppUserId(Long appUserId);

	public AppUsersMstResultSet getAllAppUsers();

	public AppUsersMstResultSet getAppUsersByStatusLookupId(Long statusLookupId);

	public AllUserDetailsResultSet getAppUserAllDetails(String username);

	public Long getAppUserIdByUserName(String username);

	public AppUsersMstResultSet changeUserPassword(AppUsersMstDTO appUsersMstDTO, String loggedInUser);

	public AppUsersMstResultSet firstTimeLoginChangePass(AppUsersMstDTO appUsersMstDTO);

}
