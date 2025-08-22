package org.vrnda.hrms.service.resultset;

import java.util.List;

import org.vrnda.hrms.entity.CmnGeneralLoanConfigDetailsEntity;
import org.vrnda.hrms.entity.EmployeeLoanDetailsEntity;
import org.vrnda.hrms.repository.dto.CmnGeneralLoanConfigDetailsDTO;
import org.vrnda.hrms.repository.dto.EmployeeLoanDetailsDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class EmployeeLoanDetailsResultSet extends GenericResultSet {

	List<EmployeeLoanDetailsEntity> employeeLoanDetailsEntityList;

	List<EmployeeLoanDetailsDTO> employeeLoanDetailsDtoList;
	
	List<CmnGeneralLoanConfigDetailsDTO> cmnGeneralLoanConfigDetailsDTO;
	
	List<CmnGeneralLoanConfigDetailsEntity> cmnGeneralLoanConfigDetailsEntityList;


}
