package org.vrnda.hrms.service.resultset;

import java.util.List;

import org.vrnda.hrms.entity.EmployeeCheckinCheckoutEntity;
import org.vrnda.hrms.repository.dto.EmployeeCheckinCheckoutDTO;
import org.vrnda.hrms.repository.dto.EmployeeTimesheetsDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class EmployeeCheckinCheckoutResultSet extends GenericResultSet {

	List<EmployeeCheckinCheckoutDTO> employeeCheckinCheckoutDtoList;

	EmployeeCheckinCheckoutEntity employeeCheckinCheckoutEntity;

	List<EmployeeCheckinCheckoutEntity> employeeCheckinCheckoutEntityList;

	List<EmployeeTimesheetsDTO> employeeTimesheetsDtoList;

}
