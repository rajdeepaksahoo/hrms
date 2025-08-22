package org.vrnda.hrms.service;


import java.util.List;

import org.vrnda.hrms.entity.CmnLeavePlanConfigDetailsEntity;
import org.vrnda.hrms.repository.dto.CmnLeavePlanConfigDetailsDTO;
import org.vrnda.hrms.service.resultset.CmnLeavePlanConfigDetailsResultSet;
import org.vrnda.hrms.service.resultset.CompanyLocationResultSet;

public interface CmnLeavePlanConfigDetailsService extends GenericService<CmnLeavePlanConfigDetailsEntity, String>{

	public CmnLeavePlanConfigDetailsResultSet getLeavePlanConfigDetailsByConfigurationIdAndleaveTypeId(Long configurationId, Long leaveTypeId );
	
	public CmnLeavePlanConfigDetailsResultSet saveOrUpdateLeavePlans(CmnLeavePlanConfigDetailsDTO cmnLeavePlanConfigDetailsDto,String loggedInUser);
	
	public CmnLeavePlanConfigDetailsResultSet deleteLeavePlanByCmnlvplnConfigdeltsId(Long CmnlvplnConfigdeltsId);
	
	public CmnLeavePlanConfigDetailsResultSet deleteLeavePlans(List<CmnLeavePlanConfigDetailsDTO> cmnLeavePlanConfigDetailsDto);
}
