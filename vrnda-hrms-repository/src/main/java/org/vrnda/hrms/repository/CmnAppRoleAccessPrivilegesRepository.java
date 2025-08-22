package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnAppRoleAccessPrivilegesCompositeKey;
import org.vrnda.hrms.entity.CmnAppRoleAccessPrivilegesEntity;
import org.vrnda.hrms.repository.dto.CmnAppRoleAccessPrivilegesDTO;

@Repository
public interface CmnAppRoleAccessPrivilegesRepository
		extends PagingAndSortingRepository<CmnAppRoleAccessPrivilegesEntity, CmnAppRoleAccessPrivilegesCompositeKey> {

	@Query("select e from  CmnAppRoleAccessPrivilegesEntity as e where menuId = :menuId and appRoleId = :appRoleId "
			+ "and statusLookupId = :statusLookupId")
	public CmnAppRoleAccessPrivilegesEntity getAppRoleAccessPrivilegesByMenuIdAndRoleId(
			@Param(value = "menuId") Long menuId, @Param(value = "appRoleId") Long appRoleId);

	@Query("select e from  CmnAppRoleAccessPrivilegesEntity as e WHERE menuId = :menuId and appRoleId = :appRoleId "
			+ "and statusLookupId = :statusLookupId")
	public CmnAppRoleAccessPrivilegesEntity getRoleAccessPrivilegesByMenuIdAndAccessPrevIdAndActiveFlagId(
			@Param(value = "menuId") Long menuId, @Param(value = "appRoleId") Long appRoleId,
			@Param(value = "statusLookupId") Long statusLookupId);

	@Query("select e from  CmnAppRoleAccessPrivilegesEntity as e WHERE menuId = :menuId and appRoleId = :appRoleId")
	public CmnAppRoleAccessPrivilegesEntity getRoleAccessPrivilegesByMenuIdAndRoleId(
			@Param(value = "menuId") Long menuId, @Param(value = "appRoleId") Long appRoleId);

	@Query("select e from  CmnAppRoleAccessPrivilegesEntity as e WHERE appRoleId = :appRoleId order by menuId")
	public List<CmnAppRoleAccessPrivilegesEntity> getRoleAccessPrivilegesByRoleId(
			@Param(value = "appRoleId") Long appRoleId);

	@Query("select e from  CmnAppRoleAccessPrivilegesEntity as e WHERE menuId = :menuId")
	public List<CmnAppRoleAccessPrivilegesEntity> getRoleAccessPrivilegesByMenuId(@Param(value = "menuId") Long menuId);

	@Query("select e from  CmnAppRoleAccessPrivilegesEntity as e WHERE appRoleId = :appRoleId  and "
			+ "statusLookupId = :statusLookupId order by menuId")
	public List<CmnAppRoleAccessPrivilegesEntity> getActiveRoleAccessPrivilegesByRoleId(
			@Param(value = "appRoleId") Long appRoleId, @Param(value = "statusLookupId") Long statusLookupId);

	@Query("select cmm, ( select 'Y' from CmnAppRoleAccessPrivilegesEntity cap where cap.menuId = cmm.menuId and cap.appRoleId = :appRoleId and cap.statusLookupId=(select lookupId  from CmnLookupMstEntity WHERE parentLookupId =(select lookupId from CmnLookupMstEntity WHERE lookupName='STATUS')\r\n"
			+ "	and lookupName ='ACTIVE')) as isAccess from CmnMenusMstEntity cmm where cmm.menuType = ( select lookupId from CmnLookupMstEntity clm where clm.lookupName = 'SCREEN')")
	public List<Object[]> getRoleAccessPrivilegesByRoles(@Param(value = "appRoleId") Long appRoleId);

}
