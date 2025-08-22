package org.vrnda.hrms.service;

import java.util.List;

import org.vrnda.hrms.entity.CmnLeaveConfigDetailsEntity;
import org.vrnda.hrms.repository.dto.CmnLeaveConfigDetailsDTO;
import org.vrnda.hrms.service.resultset.CmnLeaveConfigDetailsResultSet;

public interface CmnLeaveConfigDetailsService extends GenericService<CmnLeaveConfigDetailsEntity, Long> {

	public CmnLeaveConfigDetailsResultSet getLeaveConfigDetailsByConfigurationId(Long configurationId);
	
	public CmnLeaveConfigDetailsResultSet saveAndUpdateLeaveConfigDetails(List<CmnLeaveConfigDetailsDTO> leaveconfigdetailsList,String loginUser);
	
	public CmnLeaveConfigDetailsResultSet deleteLeaveConfigDetailsbyConfigurationId(Long configurationId);
}
