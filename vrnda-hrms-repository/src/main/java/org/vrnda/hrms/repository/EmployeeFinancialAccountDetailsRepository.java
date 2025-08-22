package org.vrnda.hrms.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.EmployeeFinancialAccountDetailsEntity;

@Repository
public interface EmployeeFinancialAccountDetailsRepository extends PagingAndSortingRepository<EmployeeFinancialAccountDetailsEntity, Long>{

	@Query("SELECT e FROM EmployeeFinancialAccountDetailsEntity as e WHERE finAcctDetId = :finAcctDetId")
	public EmployeeFinancialAccountDetailsEntity getFinancialAccountDetailsById(@Param("finAcctDetId") Long finAcctDetId);
}
