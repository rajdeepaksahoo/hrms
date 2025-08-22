package org.vrnda.hrms.service;

import java.util.List;

import org.vrnda.hrms.entity.CmnDesignationsMstEntity;
import org.vrnda.hrms.repository.dto.CmnDesignationsMstDTO;
import org.vrnda.hrms.service.resultset.CmnDesignationsMstResultSet;

public interface CmnDesignationsMstService extends GenericService<CmnDesignationsMstEntity, Long> {

	public CmnDesignationsMstResultSet saveOrUpdateDesignation(CmnDesignationsMstDTO cmnDesignationsMstDto, String loggedInUser);

	public CmnDesignationsMstResultSet deleteDesignationByDesignationId(Long designationId);

	public CmnDesignationsMstResultSet deleteDesignationsList(List<CmnDesignationsMstDTO> cmnDesignationMstDtoList);

	public CmnDesignationsMstResultSet getAllDesignations();

	public CmnDesignationsMstResultSet getDesignationByDesignationId(Long designationId);	

	public CmnDesignationsMstResultSet getDesignationsByStatusLookupId(Long statusLookupId);

	public CmnDesignationsMstResultSet getAllDesignationIdsAndDesignationNames();

	public boolean verifyDuplicateDesignationName(CmnDesignationsMstDTO cmnDesignationsMstDto);

}
