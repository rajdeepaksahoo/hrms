package org.vrnda.hrms.service.resultset;

import java.util.List;

import org.vrnda.hrms.entity.EmployeeDetailsEntity;
import org.vrnda.hrms.repository.dto.EmployeeDetailsDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class EmployeeDtlsResultSet extends GenericResultSet {

	Long employeeId;
	
	EmployeeDetailsEntity employeeDetailsEntity;

	List<EmployeeDetailsEntity> employeeDtlsMst;

	List<EmployeeDetailsDTO> employeeDetailsDTOList;

	List<EmployeeDetailsDTO> managerEmployeeDetailsDTOList;

	List<EmployeeDetailsDTO> commonEmployeeDtlsDtoList;	
	
	EmployeeDetailsDTO employeeDetailsDTO;

}
