package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.EmployeeAllConfigDetailsEntity;

@Repository
public interface EmployeeAllConfigDetailsRepository  extends PagingAndSortingRepository<EmployeeAllConfigDetailsEntity, String>{

	@Query(" select e from EmployeeAllConfigDetailsEntity as e where yearId = :yearId")
	public List<EmployeeAllConfigDetailsEntity> getAllEmployeesConfigsByYearId(@Param("yearId") Long yearId);
	
	@Query("select e from EmployeeAllConfigDetailsEntity as e where employeeId = :employeeId ")
	public EmployeeAllConfigDetailsEntity getEmployeeAllConfigDetailsByEmployeeId(@Param("employeeId") Long employeeId );
	
	@Query("select e from EmployeeAllConfigDetailsEntity as e where yearId=:yearId and employeeId = :employeeId ")
	public EmployeeAllConfigDetailsEntity getEmployeeAllConfigDetailsByYearIdandEmployeeId(@Param("yearId") Long yearId ,@Param("employeeId") Long employeeId );
	
	@Query(" select e from EmployeeAllConfigDetailsEntity as e where empAllConfigId = :empAllConfigId")
	public EmployeeAllConfigDetailsEntity getEmployeeAllConfigDetailsByempAllConfigId(@Param("empAllConfigId") Long empAllConfigId);
}
