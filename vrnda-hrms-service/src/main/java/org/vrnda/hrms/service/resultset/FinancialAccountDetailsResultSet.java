package org.vrnda.hrms.service.resultset;

import java.util.List;

import org.vrnda.hrms.entity.EmployeeFinancialAccountDetailsEntity;
import org.vrnda.hrms.repository.dto.FinancialAccountDetailsDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class FinancialAccountDetailsResultSet extends GenericResultSet {
	
    Long finAcctDetId;

	FinancialAccountDetailsDto financialAccountDetailsDto;

	List<EmployeeFinancialAccountDetailsEntity> employeeFinancialAccountDetailsEntityList;
	
	EmployeeFinancialAccountDetailsEntity employeeFinancialAccountDetailsEntity;

}
