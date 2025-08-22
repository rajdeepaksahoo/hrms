package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnProjectTeamsEntity;

@Repository
public interface CmnProjectTeamsRepository extends PagingAndSortingRepository<CmnProjectTeamsEntity, Long> {

	@Query("SELECT m  FROM  CmnProjectTeamsEntity as m")
	public List<CmnProjectTeamsEntity> getAllProjectTeams();

	@Query("SELECT m  FROM  CmnProjectTeamsEntity as m WHERE  teamId = :teamId")
	public CmnProjectTeamsEntity getProjectTeamsByTeamId(@Param("teamId") Long teamId);

	@Query("SELECT m,(select employeeId from CmnProjectTeamMembersEntity as cpme  where cpme.projectId = m.projectId and cpme.teamId = m.teamId and cpme.scrumMaster = 'Y') "
			+" FROM  CmnProjectTeamsEntity as m WHERE  projectId = :projectId")
	public List<Object[]> getProjectTeamByProjectId(@Param("projectId") Long projectId);

	@Query("SELECT m  FROM  CmnProjectTeamsEntity as m WHERE  projectId in ( :projectId)")
	public List<CmnProjectTeamsEntity> getProjectTeamsByProjectIds(@Param("projectId") List<Long> projectId);

	@Query("SELECT m  FROM  CmnProjectTeamsEntity as m WHERE  statusLookupId = :statusLookupId")
	public List<CmnProjectTeamsEntity> getAllProjectTeamsByStatusLookUpId(
			@Param("statusLookupId") Long statusLookupId);

	@Query("SELECT m FROM  CmnProjectTeamsEntity as m WHERE  projectId = :projectId and statusLookupId = :statusLookupId")
	public List<CmnProjectTeamsEntity> getProjectTeamByProjectIdAndStatusLookupId(
			@Param("projectId") Long projectId, @Param("statusLookupId") Long statusLookupId);

}
