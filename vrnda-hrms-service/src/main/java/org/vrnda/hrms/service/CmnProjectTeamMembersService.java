package org.vrnda.hrms.service;

import java.util.List;

import org.vrnda.hrms.entity.CmnProjectTeamMembersEntity;
import org.vrnda.hrms.entity.CmnProjectTeamMembersEntityPk;
import org.vrnda.hrms.repository.dto.CmnProjectTeamMembersDTO;
import org.vrnda.hrms.service.resultset.CmnProjectTeamMembersResultSet;

public interface CmnProjectTeamMembersService extends GenericService<CmnProjectTeamMembersEntity, CmnProjectTeamMembersEntityPk> {

	CmnProjectTeamMembersResultSet saveOrUpdateCmnProjectTeamMembers(CmnProjectTeamMembersDTO cmnProjectMembersDTO,
			String loggedInUser);

	CmnProjectTeamMembersResultSet getAllCmnProjectTeamMembers();

	CmnProjectTeamMembersResultSet getAllCmnProjectTeamMembersByStatusLookUpId(Long statusLookUpId);

	CmnProjectTeamMembersResultSet getAllCmnProjectTeamMembersByProjectId(Long projectId);

	CmnProjectTeamMembersResultSet getAllCmnProjectTeamMembersByTeamId(Long teamId);

	CmnProjectTeamMembersResultSet getAllProjectTeamMembersByProjectIdAndScrumMaster(Long projectId);

	CmnProjectTeamMembersResultSet getAllCmnProjectTeamMembersBySearchParams(CmnProjectTeamMembersDTO cmnProjectMembersDTO);

	CmnProjectTeamMembersResultSet getAllCmnProjectTeamMembersByProjectIdModuleId(Long projectId, Long teamId);

	CmnProjectTeamMembersResultSet getAllDetails();

	CmnProjectTeamMembersResultSet deleteByProjectId(CmnProjectTeamMembersDTO projectmembers);

	CmnProjectTeamMembersResultSet deleteProjectTeamMembersDetailsList(List<CmnProjectTeamMembersDTO> projectmembers);

}
