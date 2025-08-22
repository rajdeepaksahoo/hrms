package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnProjectsMstEntity;

@Repository
public interface CmnProjectsMstRepository extends PagingAndSortingRepository<CmnProjectsMstEntity, Long> {

	@Query("SELECT p  FROM  CmnProjectsMstEntity as p")
	public List<CmnProjectsMstEntity> getAllProjects();

	@Query("select p,(select CONCAT(ed.employeeLastName, ' ', ed.employeeFirstName) as employee_full_nam	from EmployeeDetailsEntity ed where ed.employeeId = p.projectHead) as project_head_full_name"
			+ "      from CmnProjectsMstEntity as p WHERE  projectId = :projectId")
	public List<Object[]> getProjectByProjectId(@Param("projectId") Long projectId);

	@Query("SELECT p FROM CmnProjectsMstEntity as p WHERE statusLookupId = :statusLookupId")
	public List<CmnProjectsMstEntity> getAllProjectsByStatusLookUpId(@Param("statusLookupId") Long statusLookupId);

	@Query("select p,(select CONCAT(ed.employeeLastName, ' ', ed.employeeFirstName) as employee_full_nam  from EmployeeDetailsEntity ed where ed.employeeId = p.projectHead) as project_head_full_name"
			+ "  from CmnProjectsMstEntity as p")
	public List<Object[]> getAllProjectsDetails();

	@Query("SELECT p FROM CmnProjectsMstEntity as p WHERE projectName = :projectName")
	public CmnProjectsMstEntity getAllProjectsByProjectName(@Param("projectName") String projectName);

	@Query("SELECT p FROM CmnProjectsMstEntity as p WHERE projectHead = :projectHead")
	public List<CmnProjectsMstEntity> getAllProjectsByProjectHead(@Param("projectHead") Long projectHead);

}
