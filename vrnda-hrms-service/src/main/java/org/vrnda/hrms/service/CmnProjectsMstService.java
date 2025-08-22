package org.vrnda.hrms.service;

import java.util.List;

import org.vrnda.hrms.entity.CmnProjectsMstEntity;
import org.vrnda.hrms.repository.dto.CmnProjectsMstDTO;
import org.vrnda.hrms.service.resultset.CmnProjectsMstResultSet;

public interface CmnProjectsMstService extends GenericService<CmnProjectsMstEntity, Long> {

	public CmnProjectsMstResultSet saveOrUpdateProject(CmnProjectsMstDTO CmnProjectsMstDTO, String loggedInUser);

	public CmnProjectsMstResultSet getAllProjects();

	public CmnProjectsMstResultSet getProjectByProjectId(Long projectId);

	public CmnProjectsMstResultSet getAllProjectsByStatusLookUpId(Long statusLookUpId);

	public CmnProjectsMstResultSet getAllProjectsDetails();

	public CmnProjectsMstResultSet deleteProjectById(Long projectId);
	
	public CmnProjectsMstResultSet deleteProjectDetailsList(List<CmnProjectsMstDTO> CmnProjectsMstDTOList);

}
