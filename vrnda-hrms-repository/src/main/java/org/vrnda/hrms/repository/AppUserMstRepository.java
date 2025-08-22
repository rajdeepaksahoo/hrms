package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.AppUsersMstEntity;
import org.vrnda.hrms.repository.dto.AppUsersMstDTO;

@Repository
public interface AppUserMstRepository extends JpaRepository<AppUsersMstEntity, String> {

	@Query("select e from AppUsersMstEntity as e where userId= :userId")
	public AppUsersMstEntity getAppUserByUserId(
			@Param("userId") Long userId);

	@Query("select e from AppUsersMstEntity as e where username= :username")
	public AppUsersMstEntity getAppUserByUserName(
			@Param("username") String username);

	@Query("select new org.vrnda.hrms.repository.dto.AppUsersMstDTO("
			+ "e.userId, e.employeeId, e.username, e.appRoleId, e.statusLookupId, e.systemConfigFlag) "
			+ "from AppUsersMstEntity as e")
	public List<AppUsersMstDTO> getAllAppUsers();

	@Query("select new org.vrnda.hrms.repository.dto.AppUsersMstDTO("
			+ "e.userId, e.employeeId, e.username, e.appRoleId, e.statusLookupId, e.systemConfigFlag) "
			+ "from AppUsersMstEntity as e where statusLookupId = :statusLookupId")
	public List<AppUsersMstDTO> getAppUsersByStatusLookupId(
			@Param("statusLookupId") Long statusLookupId);

	@Query("select e from AppUsersMstEntity as e where username= :userName and password = :password")
	public AppUsersMstEntity getAppUserByUserNameAndPassword(
			@Param("userName") String userName,
			@Param("password") String password);

	@Query("select e from AppUsersMstEntity as e where appRoleId= :appRoleId")
	public AppUsersMstEntity getAppUserByAppRoleId(
			@Param("appRoleId") Long appRoleId);
	
	@Query("select e.userId from AppUsersMstEntity as e where username = :username")
	public Long getAppUserIdByUserName(
			@Param("username") String username);

}
