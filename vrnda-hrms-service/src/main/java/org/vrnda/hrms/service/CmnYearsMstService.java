package org.vrnda.hrms.service;

import java.util.List;

import org.vrnda.hrms.entity.CmnYearsMstEntity;
import org.vrnda.hrms.repository.dto.CmnYearsMstDTO;
import org.vrnda.hrms.service.resultset.CmnYearsMstResultSet;

public interface CmnYearsMstService extends GenericService<CmnYearsMstEntity, String>{

	public CmnYearsMstResultSet saveOrUpdateYears(CmnYearsMstDTO cmnYearsMstDto, String loggedInUser);

	public CmnYearsMstResultSet deleteYearByYearId(Long yearId);

	public CmnYearsMstResultSet deleteYearsList(List<CmnYearsMstDTO> cmnYearsMstDtoList);

	public CmnYearsMstResultSet getYearsByYearType(String yearType);

	public CmnYearsMstResultSet  getDefaultYearsByYearType(String yearType);

	public CmnYearsMstResultSet getYearIdForCurrentYear(String yearName);

	public CmnYearsMstEntity getCmnYearsMstByYearId(Long yearId);
	
	public CmnYearsMstResultSet deleteAllYearsList(List<CmnYearsMstDTO> cmnYearsMstDtoList);

}
