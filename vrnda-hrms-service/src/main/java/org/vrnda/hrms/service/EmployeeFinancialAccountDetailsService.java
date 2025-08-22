package org.vrnda.hrms.service;

import org.vrnda.hrms.entity.EmployeeFinancialAccountDetailsEntity;
import org.vrnda.hrms.repository.dto.EmployeeFinancialAccountDetailsDTO;
import org.vrnda.hrms.service.resultset.FinancialAccountDetailsResultSet;

public interface EmployeeFinancialAccountDetailsService
		extends GenericService<EmployeeFinancialAccountDetailsEntity, Long> {

	public FinancialAccountDetailsResultSet getFinancialAccountDetailsByFinAcctDetId(Long finAcctDetId);

	public FinancialAccountDetailsResultSet saveOrUpdateFinancialAccountDetails(
			EmployeeFinancialAccountDetailsDTO financialAccountDetailsDto, String loggedInUser);

}
