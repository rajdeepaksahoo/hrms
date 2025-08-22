package org.vrnda.hrms.service;

import org.vrnda.hrms.entity.EmployeeContactPersonsEntity;
import org.vrnda.hrms.repository.dto.EmployeeContactPersonsDTO;
import org.vrnda.hrms.service.resultset.EmployeeContactPersonsResultSet;

public interface EmployeeContactPersonsService extends GenericService<EmployeeContactPersonsEntity, Long> {

	public EmployeeContactPersonsResultSet getEmployeeContactPersonsByPersonId(Long employeePrsnId);

	public EmployeeContactPersonsResultSet saveOrUpdateEmployeeContactPersons(
			EmployeeContactPersonsDTO employeeContactPersonsDto, String loggedInUser);

}
