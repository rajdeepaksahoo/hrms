package org.vrnda.hrms.service;
import java.util.List;
import org.vrnda.hrms.entity.EmployeeLeaveDetailsEntity;
import org.vrnda.hrms.service.resultset.EmployeeLeaveDetailsResultSet;

public interface DashboadService extends GenericService<EmployeeLeaveDetailsEntity, Long>{
	
	
	public EmployeeLeaveDetailsResultSet getEmployeeOnLeaveDatails(Long employeeId);
	
	
	public EmployeeLeaveDetailsResultSet getDashBoadLeaveRequestsDetails(Long employeeId);

}
