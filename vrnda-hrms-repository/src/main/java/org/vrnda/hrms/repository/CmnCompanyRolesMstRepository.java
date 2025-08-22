package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnCompanyRolesMstEntity;

@Repository
public interface CmnCompanyRolesMstRepository extends PagingAndSortingRepository<CmnCompanyRolesMstEntity, Long> {

	@Query("SELECT e FROM CmnCompanyRolesMstEntity as e WHERE companyRoleId = :companyRoleId")
	public CmnCompanyRolesMstEntity getCompanyRoleByCompanyRoleId(@Param("companyRoleId") Long companyRoleId);

	@Query("SELECT cmn FROM CmnCompanyRolesMstEntity as cmn WHERE   companyRoleName = :companyRoleName")
	public CmnCompanyRolesMstEntity getCompanyRoleByCompanyRoleName(@Param("companyRoleName") String companyRoleName);

	@Query("SELECT e from CmnCompanyRolesMstEntity as e order by companyRoleName")
	public List<CmnCompanyRolesMstEntity> getAllCompanyRoles();

	@Query("SELECT e from CmnCompanyRolesMstEntity as e WHERE statusLookupId = :statusLookupId order by companyRoleName")
	public List<CmnCompanyRolesMstEntity> getAllCompanyRolesByStatusLookupId(Long statusLookupId);

	@Query("SELECT e FROM CmnCompanyRolesMstEntity as e WHERE statusLookupId = :statusLookupId")
	public List<CmnCompanyRolesMstEntity> getCompanyRolesByStatusLookupId(@Param("statusLookupId") Long statusLookupId);

	@Query("select e from CmnCompanyRolesMstEntity as e where departmentId =:departmentId")
	public List<CmnCompanyRolesMstEntity> getCompanyRoleByDepartmentId(@Param("departmentId") Long departmentId);

	@Query("select e from CmnCompanyRolesMstEntity as e where isLeadOrManager ='Y'")
	public List<CmnCompanyRolesMstEntity> getCompanyRolesByLeadOrManager();

	@Query("select e from CmnCompanyRolesMstEntity as e where isHr ='Y'")
	public List<CmnCompanyRolesMstEntity> getCompanyRolesByHr();

	@Query("SELECT e FROM CmnCompanyRolesMstEntity as e WHERE statusLookupId = :statusLookupId and isLeadOrManager ='Y'")
	public List<CmnCompanyRolesMstEntity> getCompanyRolesByStatusLookupIdAndManager(
			@Param("statusLookupId") Long statusLookupId);

	@Query("SELECT e FROM CmnCompanyRolesMstEntity as e WHERE statusLookupId = :statusLookupId and isHr ='Y'")
	public List<CmnCompanyRolesMstEntity> getCompanyRolesByStatusLookupIdAndHr(
			@Param("statusLookupId") Long statusLookupId);

	@Query("SELECT cmn FROM CmnCompanyRolesMstEntity as cmn WHERE   companyRoleName in (:companyRoleName) and statusLookupId = :statusLookupId")
	public List<CmnCompanyRolesMstEntity> getCompanyRoleByCompanyRoleNames(
			@Param("companyRoleName") List<String> companyRoleName, @Param("statusLookupId") Long statusLookupId);

	@Query("SELECT cmn FROM CmnCompanyRolesMstEntity as cmn WHERE companyRoleName = :companyRoleName ")
	public CmnCompanyRolesMstEntity getCompanyRoleIdByRoleName(@Param("companyRoleName") String companyRoleName);

	@Query("select e from CmnCompanyRolesMstEntity as e where departmentId =:departmentId and statusLookupId = :statusLookupId")
	public List<CmnCompanyRolesMstEntity> getCompanyRolesByStatusLookupIdAndDepartmentId(
			@Param("statusLookupId") Long statusLookupId, @Param("departmentId") Long departmentId);

	@Query("SELECT e FROM CmnCompanyRolesMstEntity as e WHERE departmentId =:departmentId and statusLookupId = :statusLookupId and isLeadOrManager ='Y'")
	public List<CmnCompanyRolesMstEntity> getCompanyRolesByStatusLookupIdAndManagerAndDepartId(
			@Param("statusLookupId") Long statusLookupId, @Param("departmentId") Long departmentId);

	@Query("SELECT e FROM CmnCompanyRolesMstEntity as e WHERE departmentId =:departmentId and statusLookupId = :statusLookupId and isHr ='Y'")
	public List<CmnCompanyRolesMstEntity> getAllLeadsAndHRsByStatusLookUpIdAndDepartMentId(
			@Param("statusLookupId") Long statusLookupId, @Param("departmentId") Long departmentId);

}
