package org.vrnda.hrms.service.resultset;

import java.util.List;

import org.vrnda.hrms.entity.EmployeeAllConfigDetailsEntity;
import org.vrnda.hrms.repository.dto.EmployeeAllConfigDetailsDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class EmployeeAllConfigDetailsResultSet extends GenericResultSet{

	private Long empAllConfigDetailsId;

	List<EmployeeAllConfigDetailsEntity> employeeAllConfigDetailsEntityList;

	List<EmployeeAllConfigDetailsDTO> employeeAllConfigDetailsDtoList;
	
	EmployeeAllConfigDetailsEntity employeeDetailsConfigEntity;
	
	EmployeeAllConfigDetailsDTO employeeAllConfigDetailsDTO;

}
