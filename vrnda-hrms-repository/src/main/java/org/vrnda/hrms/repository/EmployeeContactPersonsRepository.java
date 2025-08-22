package org.vrnda.hrms.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.EmployeeContactPersonsEntity;

@Repository
public interface EmployeeContactPersonsRepository extends PagingAndSortingRepository<EmployeeContactPersonsEntity, Long> {
	
	@Query("SELECT e FROM EmployeeContactPersonsEntity as e WHERE employeeCnctPrsnId = :employeeCnctPrsnId")
	public EmployeeContactPersonsEntity getEmployeeContactPersonByPersonId(@Param("employeeCnctPrsnId") Long employeeCnctPrsnId);

}
