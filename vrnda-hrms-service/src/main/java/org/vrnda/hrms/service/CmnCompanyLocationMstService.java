package org.vrnda.hrms.service;

import java.util.List;

import org.vrnda.hrms.entity.CmnCompanyLocationsMstEntity;
import org.vrnda.hrms.repository.dto.CmnCompanyLocationsMstDTO;
import org.vrnda.hrms.service.resultset.CompanyLocationResultSet;

public interface CmnCompanyLocationMstService extends GenericService<CmnCompanyLocationsMstEntity, Long>{

	public CompanyLocationResultSet saveOrUpdateLocation(CmnCompanyLocationsMstDTO cmnCompanyLocationsMstDTO, String loggedInUser);

	public CompanyLocationResultSet deleteLocationByLocationId(Long locationId);

	public CompanyLocationResultSet deleteLocationsList(List<CmnCompanyLocationsMstDTO> cmnCompanyLocationsMstDTOList);

	public CompanyLocationResultSet getAllCompanyLocations();

	public CompanyLocationResultSet getCompanyLocationByLocationId(Long locationId);

	public CompanyLocationResultSet getCompanyLocationsByStatusLookupId(Long statusLookupId);
	
	public boolean verifyDuplicateLocationName(CmnCompanyLocationsMstDTO companyLocation);

}
