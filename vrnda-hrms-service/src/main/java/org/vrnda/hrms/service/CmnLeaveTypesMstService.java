package org.vrnda.hrms.service;

import java.util.Map;

import org.vrnda.hrms.entity.CmnLeaveTypesMstEntity;
import org.vrnda.hrms.repository.dto.CmnLeaveTypesMstDTO;
import org.vrnda.hrms.service.resultset.CmnLeaveTypesMstResultSet;

public interface CmnLeaveTypesMstService  extends GenericService<CmnLeaveTypesMstEntity, String>{

	public CmnLeaveTypesMstResultSet getAllLeaveTypes();

	public CmnLeaveTypesMstResultSet saveOrUpdateLookup(CmnLeaveTypesMstDTO cmnLeaveTypesMstdto, String loggedInUser);
	
	public Map<String, Long> getLeaveTypeNameAndIdLeaveTypes(Long YearId);
	
	public Map<Long, String> getIdAndLeaveTypeNameLeaveTypes(Long YearId);
	
}
