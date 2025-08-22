package org.vrnda.hrms.service.resultset;

import java.util.List;

import org.vrnda.hrms.entity.EmployeeSalaryDetailsEntity;
import org.vrnda.hrms.repository.dto.EmployeeSalaryDetailsDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class EmployeeSalaryDetailsResultSet extends GenericResultSet  {

	List<EmployeeSalaryDetailsEntity> employeeSalaryDetailsEntityList;

	List<EmployeeSalaryDetailsDTO> employeeSalaryDetailsDtoList;

}
