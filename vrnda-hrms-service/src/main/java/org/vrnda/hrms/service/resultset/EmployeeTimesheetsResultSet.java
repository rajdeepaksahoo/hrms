package org.vrnda.hrms.service.resultset;

import java.util.List;

import org.vrnda.hrms.repository.dto.CalendarEvents;
import org.vrnda.hrms.repository.dto.EmployeeTimesheetsDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class EmployeeTimesheetsResultSet extends GenericResultSet {

	List<EmployeeTimesheetsDTO> employeeTimesheetsDtoList;

	List<CalendarEvents> calendarEventsList;

}
