package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnAppRolesMstEntity;

@Repository
public interface CmnAppRolesMstRepository extends PagingAndSortingRepository<CmnAppRolesMstEntity, Long> {

	@Query("SELECT e FROM CmnAppRolesMstEntity as e WHERE appRoleId = :appRoleId")
	public CmnAppRolesMstEntity getAppRoleByAppRoleId(@Param("appRoleId") Long appRoleId);

	@Query("SELECT cmn FROM CmnAppRolesMstEntity as cmn WHERE   appRoleName = :appRoleName")
	public CmnAppRolesMstEntity getAppRoleByAppRoleName(@Param("appRoleName") String appRoleName);

	@Query("SELECT e FROM CmnAppRolesMstEntity as e order by appRoleName")
	public List<CmnAppRolesMstEntity> getAllAppRoles();

	@Query("SELECT e FROM CmnAppRolesMstEntity as e WHERE statusLookupId = :statusLookupId")
	public List<CmnAppRolesMstEntity> getAppRoleByStatusLookupId(@Param("statusLookupId") Long statusLookupId);

	@Query("SELECT crm.appRoleName FROM CmnAppRolesMstEntity as crm WHERE appRoleId = :appRoleId")
	public String getAppRoleNameByAppRoleId(@Param("appRoleId") Long appRoleId);

	@Query("select  crme  from CmnAppRolesMstEntity crme")
	public List<CmnAppRolesMstEntity> getRoleIdsAndNamesList();

}
