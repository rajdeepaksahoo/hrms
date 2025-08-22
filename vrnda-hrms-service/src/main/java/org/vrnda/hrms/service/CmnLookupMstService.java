package org.vrnda.hrms.service;

import java.util.List;
import java.util.Map;

import org.vrnda.hrms.entity.CmnLookupMstEntity;
import org.vrnda.hrms.repository.dto.CmnLookupMstDTO;
import org.vrnda.hrms.service.resultset.CmnLookupMstResultSet;

public interface CmnLookupMstService extends GenericService<CmnLookupMstEntity, String>{

	public CmnLookupMstResultSet saveOrUpdateLookup(CmnLookupMstDTO cmnLookupMstDto, String loggedInUser);

	public CmnLookupMstResultSet getLookupIdAndNameListByParentLookupName(String parentLookupName);

	public CmnLookupMstResultSet getLookupIdAndNameListByParentLookupNameAndParentLookupId(String parentLookupName, Long parentLookupId);

	public Long getLookupIdByLookupNameAndParentLookupName(String lookupName, String parentLookupName);

	public CmnLookupMstResultSet getLookupByLookupName(String lookupName);

	public CmnLookupMstResultSet getLookupByParentLookupName(String parentLookupName);

	public CmnLookupMstResultSet deleteLookupByLookupId(Long lookupId);

	public CmnLookupMstResultSet deleteLookupByLookup(CmnLookupMstDTO cmnLookupMstDto);

	public CmnLookupMstResultSet deleteLookupByLookupList(List<CmnLookupMstDTO> cmnLookupMstDtoList);

	public Map<String, Long> getLookupNameAndLookupIdMapingsByParentLookupName(String parentLookupName);

	public Map<Long, String> getLookupIdAndLookupNameMapingsByParentLookupName(String parentLookupName);

	public boolean verifyDuplicateLookup(CmnLookupMstDTO cmnLookupMstDto);
	
	public CmnLookupMstResultSet getLookupDetailsByLookupName(String parentLookupName);

}
