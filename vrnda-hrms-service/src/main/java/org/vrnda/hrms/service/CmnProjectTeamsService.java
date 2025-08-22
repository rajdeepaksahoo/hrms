package org.vrnda.hrms.service;

import java.util.List;

import org.vrnda.hrms.entity.CmnProjectTeamsEntity;
import org.vrnda.hrms.repository.dto.CmnProjectTeamsDTO;
import org.vrnda.hrms.service.resultset.CmnProjectTeamsResultSet;

public interface CmnProjectTeamsService extends GenericService<CmnProjectTeamsEntity, Long> {

	public CmnProjectTeamsResultSet saveorUpdateTeams(CmnProjectTeamsDTO cmnProjectTeamsDTO, String loggedInUser);

	public CmnProjectTeamsResultSet getAllProjectTeams();

	public CmnProjectTeamsResultSet getProjectTeamsByTeamId(Long teamId);

	public CmnProjectTeamsResultSet getAllProjectTeamsByStatusLookUpId(Long statusLookUpId);

	public CmnProjectTeamsResultSet getAllProjectTeamsByProjectIdAndStatusLookupId(Long projectId, Long statusLookupId);
	
	public CmnProjectTeamsResultSet getAllProjectTeamsByProjectId(Long projectId);

	public CmnProjectTeamsResultSet deleteProjectTeams(List<CmnProjectTeamsDTO> cmnProjectTeamsDTOList);

	public CmnProjectTeamsResultSet deleteProjectTeamByTeamId(Long teamId);

}
