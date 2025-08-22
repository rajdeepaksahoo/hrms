package org.vrnda.hrms.service;



import org.vrnda.hrms.entity.EmployeeLoanDetailsEntity;
import org.vrnda.hrms.repository.dto.EmployeeLoanDetailsDTO;
import org.vrnda.hrms.service.resultset.EmployeeLoanDetailsResultSet;

public interface EmployeeLoanDetailsService extends GenericService<EmployeeLoanDetailsEntity, Long> {

	
	public EmployeeLoanDetailsResultSet getEmployeeLoanDetailsById(Long employeeId);
	
	public EmployeeLoanDetailsResultSet saveEmployeeLoanDetails(EmployeeLoanDetailsDTO employeeLoanDto) throws Throwable;

	public EmployeeLoanDetailsResultSet getEmployeeLoanConfigDetails(Long employeeId, String year);

	Long getFinancialYearId();

	public EmployeeLoanDetailsResultSet getAllLoanRequests();

	public EmployeeLoanDetailsResultSet approveEmployeeLoan(EmployeeLoanDetailsDTO employeeLoanDto);
	
	
}
