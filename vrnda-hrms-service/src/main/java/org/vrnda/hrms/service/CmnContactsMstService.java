package org.vrnda.hrms.service;

import org.vrnda.hrms.entity.CmnContactsMstEntity;
import org.vrnda.hrms.repository.dto.CmnContactsMstDTO;
import org.vrnda.hrms.service.resultset.CmnContactsMstResultSet;

public interface CmnContactsMstService extends GenericService<CmnContactsMstEntity, Long> {
	
	public CmnContactsMstResultSet getCmnContactsByContactId(Long contactId);

	public CmnContactsMstResultSet saveOrUpdateCmnContactsMst(CmnContactsMstDTO cmnContactsMstDto,String loggedInUser);

}
