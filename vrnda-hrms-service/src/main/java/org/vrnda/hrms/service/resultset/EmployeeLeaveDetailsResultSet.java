package org.vrnda.hrms.service.resultset;

import java.util.List;

import org.vrnda.hrms.entity.EmployeeLeaveDetailsEntity;
import org.vrnda.hrms.repository.dto.EmployeeLeaveDetailsDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class EmployeeLeaveDetailsResultSet extends GenericResultSet {

	List<EmployeeLeaveDetailsEntity> employeeLeaveDetailsEntityList;

	List<EmployeeLeaveDetailsDTO> employeeLeaveDetailsDtoList;

}
