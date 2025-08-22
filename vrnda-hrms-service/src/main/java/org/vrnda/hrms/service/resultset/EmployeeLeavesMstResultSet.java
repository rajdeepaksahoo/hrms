package org.vrnda.hrms.service.resultset;

import java.util.List;

import org.vrnda.hrms.entity.EmployeeLeavesMstEntity;
import org.vrnda.hrms.repository.dto.EmployeeDetailsDTO;
import org.vrnda.hrms.repository.dto.EmployeeLeavesDto;
import org.vrnda.hrms.repository.dto.LookupIdAndNameDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class EmployeeLeavesMstResultSet extends GenericResultSet {

	EmployeeLeavesMstEntity employeeLeavesMstEntity;

	List<EmployeeLeavesMstEntity> employeeLeavesMstEntityList;

	List<EmployeeLeavesDto> employeeLeavesMstDtoList;

	List<EmployeeDetailsDTO> leaveDetailsDtoList;

	List<LookupIdAndNameDTO> lookupIdAndNameDTOList;
}
