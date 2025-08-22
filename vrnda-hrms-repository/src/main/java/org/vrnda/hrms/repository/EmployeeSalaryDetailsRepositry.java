package org.vrnda.hrms.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.EmployeeSalaryDetailsEntity;

@Repository
public interface EmployeeSalaryDetailsRepositry extends PagingAndSortingRepository<EmployeeSalaryDetailsEntity, Long> {

	@Query("select e from EmployeeSalaryDetailsEntity as e where employeeId = :employeeId")
	public ArrayList<EmployeeSalaryDetailsEntity> getEmployeeSalaryDetails(@Param("employeeId") Long employeeId);
}
