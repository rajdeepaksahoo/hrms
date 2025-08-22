package org.vrnda.hrms.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.EmployeeLoanDetailsEntity;

@Repository
public interface EmployeeLoanDetailsRepository extends PagingAndSortingRepository<EmployeeLoanDetailsEntity, Long> {

	@Query("select e from EmployeeLoanDetailsEntity as e where employeeId = :employeeId ")
	public ArrayList<EmployeeLoanDetailsEntity> getEmployeeLoanDetailsWithLoanId(@Param("employeeId") Long employeeId);

	@Query(value = "select YEAR_ID from CMN_YEARS_MST cym where YEAR_TYPE_LOOKUP_ID = (select LOOKUP_ID from CMN_LOOKUP_MST clm where LOOKUP_NAME = 'FINANCIAL_YEAR') AND CURRENT_DATE() BETWEEN YEAR_START_DATE AND YEAR_END_DATE", nativeQuery = true)
	public Long getFinancialYearId();

	@Query("select l,(select CONCAT(e.employeeLastName, ' ', e.employeeFirstName ) from EmployeeDetailsEntity e where e.employeeId = l.employeeId ) AS employeeFullName from EmployeeLoanDetailsEntity as l where loanStatus = (SELECT lookupId from CmnLookupMstEntity clm  where lookupName = 'APPLIED')")
	public List<Object[]> getAllActiveLoanRequests();

	@Query("select e from EmployeeLoanDetailsEntity as e where loanId = :loanId ")
	public EmployeeLoanDetailsEntity getEmployeeLoanDetailsWithLoneId(@Param("loanId") Long loanId);


}
