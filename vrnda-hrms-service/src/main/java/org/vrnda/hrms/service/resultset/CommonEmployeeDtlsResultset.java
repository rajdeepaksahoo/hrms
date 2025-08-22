package org.vrnda.hrms.service.resultset;

import java.util.List;

import org.vrnda.hrms.entity.CmnAddressesMstEntity;
import org.vrnda.hrms.entity.CmnContactsMstEntity;
import org.vrnda.hrms.entity.EmployeeDetailsEntity;
import org.vrnda.hrms.entity.EmployeeFinancialAccountDetailsEntity;
import org.vrnda.hrms.entity.EmployeeContactPersonsEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class CommonEmployeeDtlsResultset extends GenericResultSet {

	List<CmnAddressesMstEntity> cmnAddressesMstEntityList;

	List<CmnContactsMstEntity> cmnContactMstEntityList;

	List<EmployeeDetailsEntity> employeeDetailsEntityList;

	List<EmployeeContactPersonsEntity> employeePersonMpgEntityList;

	List<EmployeeFinancialAccountDetailsEntity> employeeFinancialAccountDetailsEntity;

}
