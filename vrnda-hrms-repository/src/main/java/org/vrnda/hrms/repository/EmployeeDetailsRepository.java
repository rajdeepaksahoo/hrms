package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.EmployeeDetailsEntity;

@Repository
public interface EmployeeDetailsRepository extends PagingAndSortingRepository<EmployeeDetailsEntity, Long>,
		JpaSpecificationExecutor<EmployeeDetailsEntity> {

	@Query("select e from EmployeeDetailsEntity as e where companyDesigId= :designationId")
	public List<EmployeeDetailsEntity> getEmployeeDetailsByDesignationId(@Param("designationId") Long designationId);

	@Query("select e from EmployeeDetailsEntity as e where e.companyRoleId in (:roleId)")
	public List<EmployeeDetailsEntity> getEmployeeDetailsBycompanyRoleId(@Param("roleId") Long roleId);

	@Query("SELECT e  FROM  EmployeeDetailsEntity as e order by employeeId")
	public List<EmployeeDetailsEntity> getAllEmployeeDetails();

	@Query("SELECT e, (select d.designationDescription from CmnDesignationsMstEntity d where d.designationId = e.companyDesigId) as desginationName,"
			+ "(select l.locationName from CmnCompanyLocationsMstEntity l where l.locationId = e.locationId) as locationName,"
			+ "(select c.companyRoleDescription from CmnCompanyRolesMstEntity c where c.companyRoleId = e.companyRoleId) as companyRoleDescription, "
			+ " (select cd.departmentDescription from CmnDepartmentsMstEntity cd where cd.departmentId = e.departmentId) as departmentDescription, "
			+ "(select CONCAT(ed.employeeLastName, ' ', ed.employeeFirstName)  from EmployeeDetailsEntity ed where ed.employeeId = e.managerId) as managerFullName, "
			+ "(select CONCAT(ed.employeeLastName, ' ', ed.employeeFirstName)  from EmployeeDetailsEntity ed where ed.employeeId = e.hrId) as hrFullName,"
			+ "(select lookupDesc from CmnLookupMstEntity cl where cl.lookupId = e.employmentType) as employmentTypeName "
			+ "FROM EmployeeDetailsEntity as e WHERE  employeeId= :employeeId")
	public List<Object[]> getEmployeeDetailsWithEmployeeId(@Param("employeeId") Long employeeId);

	@Query("SELECT e FROM EmployeeDetailsEntity as e WHERE  employeeId= :employeeId")
	public EmployeeDetailsEntity getEmployeeDetailsByEmployeeId(@Param("employeeId") Long employeeId);

	@Query("SELECT e FROM EmployeeDetailsEntity as e WHERE  e.companyRoleId in (:roleId)")
	public List<EmployeeDetailsEntity> getAllLeadsAndManagers(@Param("roleId") List<Long> roleId);

	@Query("SELECT e FROM EmployeeDetailsEntity as e WHERE e.statusLookupId = :statusLookupId and  e.companyRoleId in (:roleId)")
	public List<EmployeeDetailsEntity> getAllLeadsAndManagersByStatusLookUpId(
			@Param("statusLookupId") Long statusLookupId, @Param("roleId") List<Long> roleId);

	@Query("SELECT e FROM EmployeeDetailsEntity as e WHERE  statusLookupId= :statusLookupId")
	public List<EmployeeDetailsEntity> getAllActiveEmployees(@Param("statusLookupId") Long statusLookupId);

	@Query("SELECT e, (select c.professionalEmail from CmnContactsMstEntity c where c.contactId = e.contactId) as professionalEmail FROM EmployeeDetailsEntity as e WHERE  statusLookupId= :statusLookupId and not exists (select app.employeeId from AppUsersMstEntity app where app.employeeId = e.employeeId)")
	public List<Object[]> getAllActiveEmployeesAndUserIsNull(@Param("statusLookupId") Long statusLookupId);
	
	@Query("SELECT e FROM EmployeeDetailsEntity as e WHERE  managerId= :employeeId or hrId= :employeeId")
	public List<EmployeeDetailsEntity> getEmployeeDetailsByManagerIdOrHrId(@Param("employeeId") Long employeeId);
	
	@Query("SELECT e FROM EmployeeDetailsEntity as e WHERE  locationId= :locationId")
	public List<EmployeeDetailsEntity> getEmployeeDetailsLocationIds(@Param("locationId") Long locationId);
	

}
