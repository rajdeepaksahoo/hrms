package org.vrnda.hrms.service;

import org.vrnda.hrms.entity.EmployeeDetailsEntity;
import org.vrnda.hrms.repository.dto.EmployeeDetailsDTO;
import org.vrnda.hrms.service.resultset.EmployeeDtlsResultSet;

public interface EmployeeDetailsService extends GenericService<EmployeeDetailsEntity, Long> {

	EmployeeDtlsResultSet getAllEmployeeDtls();

	EmployeeDtlsResultSet getAllLeadsAndManagers();

	EmployeeDtlsResultSet getAllHrs();

	EmployeeDtlsResultSet getEmployeeDtlsById(Long employeeId);

	EmployeeDtlsResultSet getAllLeadsAndManagersByStatusLookUpId(Long statusLookUpId);

	EmployeeDtlsResultSet getAllHrsByStatusLookUpId(Long statusLookUpId);

	EmployeeDtlsResultSet createOrUpdateEmployeeDtls(EmployeeDetailsDTO employeeDetailsDTO, String loggedInUser);

	EmployeeDtlsResultSet getEmployeeCompleteDetailsByEmployeeId(Long employeeId);

	EmployeeDtlsResultSet getEmployeeDetailsBySearchParams(EmployeeDetailsDTO employeeDetailsDTO);

	EmployeeDtlsResultSet getAllActiveEmployees();

	EmployeeDtlsResultSet getAllActiveEmployeesAndUserIsNull(Long statusLookupId);

	public EmployeeDtlsResultSet getEmployeeDetailsByManagerIdOrHrId(Long employeeId);

	EmployeeDtlsResultSet getAllLeadsAndManagersByStatusLookUpIdAndDepartMentId(Long statusLookUpId, Long departMentId);

	EmployeeDtlsResultSet getAllLeadsAndHRsByStatusLookUpIdAndDepartMentId(Long statusLookUpId, Long departMentId);
}
