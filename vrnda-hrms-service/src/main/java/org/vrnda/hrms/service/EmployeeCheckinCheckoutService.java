package org.vrnda.hrms.service;

import java.util.List;

import org.vrnda.hrms.entity.EmployeeCheckinCheckoutEntity;
import org.vrnda.hrms.repository.dto.EmployeeCheckinCheckoutDTO;
import org.vrnda.hrms.repository.dto.EmployeeTimesheetsDTO;
import org.vrnda.hrms.service.resultset.EmployeeCheckinCheckoutResultSet;

public interface EmployeeCheckinCheckoutService extends GenericService<EmployeeCheckinCheckoutEntity, Long> {

	EmployeeCheckinCheckoutResultSet getEmployeeCheckinDetails(Long employeeId);

	EmployeeCheckinCheckoutResultSet employeeCheckIn(EmployeeCheckinCheckoutDTO employeeCheckinCheckoutDTO,
			String loggedInUser);

	EmployeeCheckinCheckoutResultSet employeeCheckOut(List<EmployeeTimesheetsDTO> employeeTimesheetsDTO,
			String loggedInUser);

}
