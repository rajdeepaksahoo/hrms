package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnDepartmentsMstEntity;

@Repository
public interface CmnDepartmentsMstRepository extends PagingAndSortingRepository<CmnDepartmentsMstEntity, Long>{

	@Query("select e from CmnDepartmentsMstEntity as e where departmentId = :departmentId")
	public CmnDepartmentsMstEntity getDepartmentByDepartmentId(
			@Param("departmentId") Long departmentId);

	@Query("select e from CmnDepartmentsMstEntity as e where departmentName =:departmentName ")
	public CmnDepartmentsMstEntity getDepartmentByDepartmentName(
			@Param("departmentName") String departmentName);

	@Query("select e from CmnDepartmentsMstEntity as e order by departmentName")
	public List<CmnDepartmentsMstEntity> getAllDepartments();

	@Query("select e from CmnDepartmentsMstEntity as e where statusLookupId=:statusLookupId")
	public List<CmnDepartmentsMstEntity>  getDepartmentByStatusLookupId(
			@Param("statusLookupId") Long statusLookupId);

}
