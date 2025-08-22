package org.vrnda.hrms.service;

import org.vrnda.hrms.entity.EmployeeLeavesMstEntity;
import org.vrnda.hrms.repository.dto.EmployeeLeavesMstDTO;
import org.vrnda.hrms.service.resultset.EmployeeLeavesMstResultSet;

public interface EmployeeLeavesMstService extends GenericService<EmployeeLeavesMstEntity, Long> {

	public EmployeeLeavesMstResultSet getLeaveBalance(Long employeeId);

	public EmployeeLeavesMstResultSet updateLeaveBalance(EmployeeLeavesMstDTO employeeLeavesMstDto,String loggedInUser);
	
	public EmployeeLeavesMstResultSet getEmployeeLvBalance();
	
	public EmployeeLeavesMstResultSet getLeaveBalById(Long empId, Long lvTypeLookupId,Long statusLookupId);
	
	public EmployeeLeavesMstResultSet getLeaveBalanceByUserId(String username);
	
	public EmployeeLeavesMstResultSet getEmployeeLeavesByYearId(Long yearId);

}
