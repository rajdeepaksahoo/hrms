package org.vrnda.hrms.service;

import org.vrnda.hrms.entity.EmployeeLeaveDetailsEntity;
import org.vrnda.hrms.repository.dto.EmployeeLeaveDetailsDTO;
import org.vrnda.hrms.service.resultset.EmployeeLeaveDetailsResultSet;

public interface EmployeeLeaveDetailsService extends GenericService<EmployeeLeaveDetailsEntity, Long>{
	
	public EmployeeLeaveDetailsResultSet getAllLeaveRequests(Long approverId);
//	
	 public EmployeeLeaveDetailsResultSet getEmployeeLeaveRequests(Long employeeId);
////	
	public EmployeeLeaveDetailsResultSet saveOrUpdate(EmployeeLeaveDetailsDTO employeeLeaveDtlsDto,String loginUser);
	
//	public EmployeeLeaveDetailsResultSet cancelLeaveRequest(EmployeeLeaveDetailsDTO employeeLeaveDtlsDto);
//	
	public EmployeeLeaveDetailsResultSet approveRequest(EmployeeLeaveDetailsDTO employeeLeaveDtlsDto,String logginUser);
//	
//	 public EmployeeLeaveDetailsResultSet getEmployeeLeaveRequestsByUserId(String username);

}
