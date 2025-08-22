package org.vrnda.hrms.service;

import org.vrnda.hrms.entity.CmnLeaveTypesMstEntity;
import org.vrnda.hrms.service.resultset.EmployeeLeaveBalanceResultset;

public interface EmployeeLeaveBalanceService extends GenericService<CmnLeaveTypesMstEntity, String>{

	public EmployeeLeaveBalanceResultset getAllEmpLeaveBalance(String currentYear, Long activeFlagId, Long employeeId);
	
}
