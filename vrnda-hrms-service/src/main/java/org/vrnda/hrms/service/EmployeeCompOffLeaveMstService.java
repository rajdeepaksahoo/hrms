package org.vrnda.hrms.service;

import org.vrnda.hrms.entity.EmployeeCompOffLeaveMstEntity;
import org.vrnda.hrms.repository.dto.EmployeeCompOffLeaveMstDTO;
import org.vrnda.hrms.service.resultset.EmployeeCompOffLeaveMstResultSet;

public interface EmployeeCompOffLeaveMstService extends GenericService<EmployeeCompOffLeaveMstEntity, Long>{

	public EmployeeCompOffLeaveMstResultSet getEmployeeCompOffLeavesDetails(Long employeeId);

	public EmployeeCompOffLeaveMstResultSet saveOrUpdateCompoffLeaves(EmployeeCompOffLeaveMstDTO compoffleavesdto,String logginUser);

	public EmployeeCompOffLeaveMstResultSet getCompoffLeave();
	
	public EmployeeCompOffLeaveMstResultSet getCompoffLeaveHistory();

//	public List<EmployeeCompOffLeaveMstEntity> getLeaveApprovalsData();
	
}
