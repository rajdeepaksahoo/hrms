package org.vrnda.hrms.service;

import java.util.List;

import org.vrnda.hrms.entity.CmnConfigurationsMstEntity;
import org.vrnda.hrms.repository.dto.CmnConfigurationsMstDTO;
import org.vrnda.hrms.service.resultset.CmnConfigurationsMstResultSet;


public interface CmnConfigurationsMstService extends GenericService<CmnConfigurationsMstEntity, String> {

	public CmnConfigurationsMstResultSet saveOrUpdateConfiguration(CmnConfigurationsMstDTO cmnConfiguration, String loggedInUser);
	
	public CmnConfigurationsMstResultSet deleteConfigurationByConfigurationId(Long configurationId);
	
	public CmnConfigurationsMstResultSet deleteConfigurationsList(List<CmnConfigurationsMstDTO> cmnConfigurationsMstDtoList);
	
	public CmnConfigurationsMstResultSet getConfigurationByConfigurationId(Long configurationId);
	
	public CmnConfigurationsMstResultSet getConfigurationsByConfigTypeLookupIdAndYearId(String configTypeLookupName, Long yearId);
	
	public CmnConfigurationsMstResultSet getConfigurationsByConfigurationTypeLookupIdAndYearIdAndStatusLookupId(Long configTypeLookupId, Long yearId, Long statusLookupId);
	
	public boolean verifyDuplicateConfiguratoinName(CmnConfigurationsMstDTO cmnConfigurationsMstDto);

	public CmnConfigurationsMstResultSet getConfigurationsByYearId(Long yearId);
	
}
