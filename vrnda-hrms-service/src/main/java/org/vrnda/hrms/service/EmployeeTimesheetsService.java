package org.vrnda.hrms.service;

import java.util.List;

import org.vrnda.hrms.entity.EmployeeTimesheetsEntity;
import org.vrnda.hrms.repository.dto.EmployeeTimesheetsDTO;
import org.vrnda.hrms.service.resultset.EmployeeTimesheetsResultSet;

public interface EmployeeTimesheetsService extends GenericService<EmployeeTimesheetsEntity, Long> {

	EmployeeTimesheetsResultSet saveTimesheets(List<EmployeeTimesheetsDTO> employeeTimesheetsDTO, String loggedInUser);

	EmployeeTimesheetsResultSet getAllEmployeeTimeSheetData(Long employeeId);

	EmployeeTimesheetsResultSet getAllEmployeeTimeSheetDataBasedOnCheckIns(List<Long> empCheckinIds);

}
