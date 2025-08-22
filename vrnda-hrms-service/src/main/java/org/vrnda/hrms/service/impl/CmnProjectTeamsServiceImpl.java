package org.vrnda.hrms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.CmnCompanyRolesMstEntity;
import org.vrnda.hrms.entity.CmnProjectTeamMembersEntity;
import org.vrnda.hrms.entity.CmnProjectTeamsEntity;
import org.vrnda.hrms.entity.CmnProjectsMstEntity;
import org.vrnda.hrms.repository.CmnCompanyRolesMstRepository;
import org.vrnda.hrms.repository.CmnProjectTeamMembersRepository;
import org.vrnda.hrms.repository.CmnProjectTeamsRepository;
import org.vrnda.hrms.repository.CmnProjectsMstRepository;
import org.vrnda.hrms.repository.dto.CmnProjectTeamMembersDTO;
import org.vrnda.hrms.repository.dto.CmnProjectTeamsDTO;
import org.vrnda.hrms.service.CmnProjectTeamMembersService;
import org.vrnda.hrms.service.CmnProjectTeamsService;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.resultset.CmnProjectTeamsResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class CmnProjectTeamsServiceImpl extends GenericServiceImpl<CmnProjectTeamsEntity, Long>
		implements CmnProjectTeamsService {

	public CmnProjectTeamsServiceImpl(PagingAndSortingRepository<CmnProjectTeamsEntity, Long> typeRepository) {
		super(typeRepository);
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private CmnProjectsMstRepository cmnProjectsMstRepository;

	@Autowired
	private CmnProjectTeamsRepository cmnProjectTeamsRepository;

	@Autowired
	private CmnCompanyRolesMstRepository cmnCompanyRolesMstRepository;

	@Autowired
	private CmnProjectTeamMembersRepository cmnProjectTeamMembersRepository;

	@Autowired
	private CmnProjectTeamMembersService cmnProjectTeamMembersService;

	@Autowired
	private CmnTableSeqService cmnTableSeqService;

	private Logger logger = LogManager.getLogger(CmnProjectTeamsServiceImpl.class.getName());

	@Override
	public CmnProjectTeamsResultSet saveorUpdateTeams(CmnProjectTeamsDTO cmnProjectTeamsDTO, String loggedInUser) {
		CmnProjectTeamsEntity cmnProjectTeamsEntity = null;
		CmnProjectTeamsResultSet cmnProjectTeamsResultSet = new CmnProjectTeamsResultSet();
		try {
			// Update
			if (cmnProjectTeamsDTO.getTeamId() != null && cmnProjectTeamsDTO.getTeamId() > 0) {
				cmnProjectTeamsEntity = new CmnProjectTeamsEntity();
				BeanUtils.copyProperties(cmnProjectTeamsDTO, cmnProjectTeamsEntity);
				BaseUtils.modifyBaseData(cmnProjectTeamsEntity, loggedInUser);
				cmnProjectTeamsEntity.setCreatedBy(loggedInUser);
				save(cmnProjectTeamsEntity);
			}
			// Insert
			if (cmnProjectTeamsDTO.getTeamId() == null || cmnProjectTeamsDTO.getTeamId() == 0) {
				cmnProjectTeamsEntity = new CmnProjectTeamsEntity();
				BeanUtils.copyProperties(cmnProjectTeamsDTO, cmnProjectTeamsEntity);
				cmnProjectTeamsEntity.setTeamId(cmnTableSeqService
						.getNextSequence(ApplicationConstants.CMN_PROJECT_TEAMS, ApplicationConstants.TEAM_ID));
				BaseUtils.setBaseData(cmnProjectTeamsEntity, loggedInUser);
				cmnProjectTeamsEntity.setCreatedBy(loggedInUser);
				save(cmnProjectTeamsEntity);

				CmnCompanyRolesMstEntity cmnCompanyRolesMstEntity = cmnCompanyRolesMstRepository
						.getCompanyRoleIdByRoleName(ApplicationConstants.DEVELOPER);
				if (cmnCompanyRolesMstEntity != null) {
					CmnProjectTeamMembersDTO cmnProjectTeamMembersDTO = new CmnProjectTeamMembersDTO();
					cmnProjectTeamMembersDTO.setProjectId(cmnProjectTeamsDTO.getProjectId());
					cmnProjectTeamMembersDTO.setTeamId(cmnProjectTeamsEntity.getTeamId());
					cmnProjectTeamMembersDTO.setProjectHead(cmnProjectTeamsDTO.getProjectHead());
					cmnProjectTeamMembersDTO.setScrumMasterId(cmnProjectTeamsDTO.getScrumMaster());
					cmnProjectTeamMembersDTO.setStatusLookupId(cmnProjectTeamsDTO.getStatusLookupId());
					cmnProjectTeamMembersDTO.setRoleId(cmnCompanyRolesMstEntity.getCompanyRoleId());
					cmnProjectTeamMembersDTO.setSystemConfigFlag("Y");
					cmnProjectTeamMembersDTO.setIsNewRecord("Y");
					cmnProjectTeamMembersService.saveOrUpdateCmnProjectTeamMembers(cmnProjectTeamMembersDTO, loggedInUser);
				}
			}
		} catch (Exception e) {
			cmnProjectTeamsResultSet.setStatus(false);
			cmnProjectTeamsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			cmnProjectTeamsResultSet.setMessageDescription(e.getMessage());
			logger.error("Exception occured in CmnProjectTeamsServiceImpl.saveorUpdateTeams()", e);
		}
		return cmnProjectTeamsResultSet;
	}

	@Override
	public CmnProjectTeamsResultSet getAllProjectTeamsByProjectId(Long projectId) {
		List<CmnProjectsMstEntity> cmnProjectsMstEntityList = new ArrayList<CmnProjectsMstEntity>();
		Map<Long, String> map = new HashMap<Long, String>();
		CmnProjectTeamsResultSet cmnProjectTeamsResultSet = new CmnProjectTeamsResultSet();
		try {
			if (projectId != null) {
				cmnProjectsMstEntityList = cmnProjectsMstRepository.getAllProjects();
				cmnProjectsMstEntityList.forEach(objects -> {
					map.put(objects.getProjectId(), objects.getProjectName());
				});
				List<Object[]> cmnProjectTeamsEntity = cmnProjectTeamsRepository.getProjectTeamByProjectId(projectId);
				if (cmnProjectTeamsEntity != null) {
					List<CmnProjectTeamsDTO> cmnProjectTeamsDTOList = new ArrayList<CmnProjectTeamsDTO>();
					for (Object[] result : cmnProjectTeamsEntity) {
						CmnProjectTeamsDTO cmnProjectTeamsDTO = new CmnProjectTeamsDTO();
						CmnProjectTeamsEntity cmnProjectTeamsEntityResult = new CmnProjectTeamsEntity();
						cmnProjectTeamsEntityResult = (CmnProjectTeamsEntity) result[0];
						BeanUtils.copyProperties(cmnProjectTeamsEntityResult, cmnProjectTeamsDTO);
						cmnProjectTeamsDTO.setEmployeeId((Long) result[1]);
						cmnProjectTeamsDTO.setProjectName(map.get(projectId));
						cmnProjectTeamsDTOList.add(cmnProjectTeamsDTO);
					}
					cmnProjectTeamsResultSet.setCmnProjectTeamsDTOList(cmnProjectTeamsDTOList);
				}
			} else {
				cmnProjectTeamsResultSet.setStatus(false);
				cmnProjectTeamsResultSet.setMessage(ApplicationConstants.FAILED);
				cmnProjectTeamsResultSet.setMessageDescription("No Active Teams are present for given projectId");
			}

		} catch (Exception e) {
			cmnProjectTeamsResultSet.setStatus(false);
			cmnProjectTeamsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			cmnProjectTeamsResultSet.setMessageDescription(e.getMessage());
			logger.error("Exception occured in CmnProjectTeamsServiceImpl.getAllProjectTeamsByProjectId()", e);
		}
		return cmnProjectTeamsResultSet;
	}

	@Override
	public CmnProjectTeamsResultSet getAllProjectTeamsByProjectIdAndStatusLookupId(Long projectId,
			Long statusLookupId) {
		CmnProjectTeamsResultSet cmnProjectTeamsResultSet = new CmnProjectTeamsResultSet();
		try {
			if (projectId != null && statusLookupId != null) {
				List<CmnProjectTeamsEntity> cmnProjectTeamsEntity = cmnProjectTeamsRepository
						.getProjectTeamByProjectIdAndStatusLookupId(projectId, statusLookupId);
				if (cmnProjectTeamsEntity != null) {
					List<CmnProjectTeamsDTO> CmnProjectTeamsDTOList = new ArrayList<CmnProjectTeamsDTO>();
					cmnProjectTeamsEntity.forEach(objects -> {
						CmnProjectTeamsDTO cmnProjectTeamsDTO = new CmnProjectTeamsDTO();
						BeanUtils.copyProperties(objects, cmnProjectTeamsDTO);
						CmnProjectTeamsDTOList.add(cmnProjectTeamsDTO);
					});
					cmnProjectTeamsResultSet.setCmnProjectTeamsDTOList(CmnProjectTeamsDTOList);
				}
			} else {
				cmnProjectTeamsResultSet.setStatus(false);
				cmnProjectTeamsResultSet.setMessage(ApplicationConstants.FAILED);
				cmnProjectTeamsResultSet.setMessageDescription("No Active Teams are present");
			}
		} catch (Exception e) {
			cmnProjectTeamsResultSet.setStatus(false);
			cmnProjectTeamsResultSet.setMessage("Exception");
			cmnProjectTeamsResultSet.setMessageDescription(e.getMessage());
			logger.error("Exception occured in CmnProjectTeamsServiceImpl.getProjectTeamByProjectIdAndStatusLookupId()",
					e);
		}
		return cmnProjectTeamsResultSet;
	}

	@Override
	public CmnProjectTeamsResultSet getAllProjectTeams() {
		List<CmnProjectsMstEntity> cmnProjectsMstEntityList = new ArrayList<CmnProjectsMstEntity>();
		Map<Long, String> map = new HashMap<Long, String>();
		CmnProjectTeamsResultSet cmnProjectTeamsResultSet = new CmnProjectTeamsResultSet();
		try {
			cmnProjectsMstEntityList = cmnProjectsMstRepository.getAllProjects();
			cmnProjectsMstEntityList.forEach(objects -> {
				map.put(objects.getProjectId(), objects.getProjectName());
			});
			List<CmnProjectTeamsEntity> cmnProjectTeamsEntity = cmnProjectTeamsRepository.getAllProjectTeams();
			if (cmnProjectTeamsEntity.size() > 0 && cmnProjectTeamsEntity != null) {
				List<CmnProjectTeamsDTO> CmnProjectTeamsDTOList = new ArrayList<CmnProjectTeamsDTO>();
				cmnProjectTeamsEntity.forEach(objects -> {
					CmnProjectTeamsDTO cmnProjectTeamsDTO = new CmnProjectTeamsDTO();
					BeanUtils.copyProperties(objects, cmnProjectTeamsDTO);
					cmnProjectTeamsDTO.setProjectName(map.get(objects.getProjectId()));
					CmnProjectTeamsDTOList.add(cmnProjectTeamsDTO);
				});
				cmnProjectTeamsResultSet.setCmnProjectTeamsDTOList(CmnProjectTeamsDTOList);
			} else {
				cmnProjectTeamsResultSet.setStatus(false);
				cmnProjectTeamsResultSet.setMessage(ApplicationConstants.FAILED);
				cmnProjectTeamsResultSet.setMessageDescription("Project Teams does not exist");
			}
		} catch (Exception e) {
			cmnProjectTeamsResultSet.setStatus(false);
			cmnProjectTeamsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			cmnProjectTeamsResultSet.setMessageDescription(e.getMessage());
			logger.error("Exception occured in CmnProjectTeamsServiceImpl.getAllProjectTeams()", e);
		}
		return cmnProjectTeamsResultSet;
	}

	@Override
	public CmnProjectTeamsResultSet getProjectTeamsByTeamId(Long teamId) {
		CmnProjectTeamsResultSet cmnProjectTeamsResultSet = new CmnProjectTeamsResultSet();
		try {
			CmnProjectTeamsEntity cmnProjectTeamsEntity = cmnProjectTeamsRepository.getProjectTeamsByTeamId(teamId);
			if (cmnProjectTeamsEntity != null) {
				CmnProjectTeamsDTO cmnProjectTeamsDTO = new CmnProjectTeamsDTO();
				BeanUtils.copyProperties(cmnProjectTeamsEntity, cmnProjectTeamsDTO);
				cmnProjectTeamsResultSet.setCmnProjectTeamsDTO(cmnProjectTeamsDTO);
			} else {
				cmnProjectTeamsResultSet.setStatus(false);
				cmnProjectTeamsResultSet.setMessage(ApplicationConstants.FAILED);
				cmnProjectTeamsResultSet.setMessageDescription("Team does not exist for the given Team Id.");
			}
		} catch (Exception e) {
			cmnProjectTeamsResultSet.setStatus(false);
			cmnProjectTeamsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			cmnProjectTeamsResultSet.setMessageDescription(e.getMessage());
			logger.error("Exception occured in CmnProjectTeamsServiceImpl.getProjectTeamByTeamId()", e);
		}
		return cmnProjectTeamsResultSet;
	}

	@Override
	public CmnProjectTeamsResultSet getAllProjectTeamsByStatusLookUpId(Long statusLookUpId) {
		CmnProjectTeamsResultSet cmnProjectTeamsResultSet = new CmnProjectTeamsResultSet();
		List<CmnProjectTeamsEntity> cmnProjectTeamsEntity = null;
		try {
			cmnProjectTeamsEntity = cmnProjectTeamsRepository.getAllProjectTeamsByStatusLookUpId(statusLookUpId);
			if (cmnProjectTeamsEntity != null) {
				List<CmnProjectTeamsDTO> CmnProjectTeamsDTOList = new ArrayList<CmnProjectTeamsDTO>();
				cmnProjectTeamsEntity.forEach(objects -> {
					CmnProjectTeamsDTO cmnProjectTeamsDTO = new CmnProjectTeamsDTO();
					BeanUtils.copyProperties(objects, cmnProjectTeamsDTO);
					CmnProjectTeamsDTOList.add(cmnProjectTeamsDTO);
				});
				cmnProjectTeamsResultSet.setCmnProjectTeamsDTOList(CmnProjectTeamsDTOList);
			} else {
				cmnProjectTeamsResultSet.setStatus(false);
				cmnProjectTeamsResultSet.setMessage(ApplicationConstants.FAILED);
				cmnProjectTeamsResultSet.setMessageDescription("No Active Teams are present");
			}
		} catch (Exception e) {
			cmnProjectTeamsResultSet.setStatus(false);
			cmnProjectTeamsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			cmnProjectTeamsResultSet.setMessageDescription(e.getMessage());
			logger.error("Exception occured in CmnProjectTeamsServiceImpl.getAllProjectTeamsByStatusLookUpId()", e);
		}
		return cmnProjectTeamsResultSet;
	}

	@Override
	public CmnProjectTeamsResultSet deleteProjectTeamByTeamId(Long TeamId) {
		CmnProjectTeamsResultSet cmnProjectTeamsResultSet = new CmnProjectTeamsResultSet();
		CmnProjectTeamsEntity cmnProjectTeamsEntity = null;
		List<Object[]> cmnProjectTeamMembersEntityList = null;
		try {
			if (TeamId != null) {
				cmnProjectTeamsEntity = cmnProjectTeamsRepository.getProjectTeamsByTeamId(TeamId);
				if (cmnProjectTeamsEntity != null) {
					cmnProjectTeamMembersEntityList = cmnProjectTeamMembersRepository.getAllCmnProjectTeamMembersByTeamId(TeamId);
					if (cmnProjectTeamMembersEntityList != null) {
						cmnProjectTeamsResultSet.setStatus(false);
						cmnProjectTeamsResultSet.setMessage(ApplicationConstants.FAILED);
						cmnProjectTeamsResultSet
								.setMessageDescription("Project Team assigned to a Project Member cannot be deleted.");
					} else {
						delete(cmnProjectTeamsEntity);
						cmnProjectTeamsResultSet.setMessageDescription("Project Team deleted successfully.");
					}
				}
			} else {
				cmnProjectTeamsResultSet.setStatus(false);
				cmnProjectTeamsResultSet.setMessage(ApplicationConstants.FAILED);
				cmnProjectTeamsResultSet.setMessageDescription("Invalid Inputs.");
			}
		} catch (Exception e) {
			cmnProjectTeamsResultSet.setStatus(false);
			cmnProjectTeamsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			cmnProjectTeamsResultSet.setMessageDescription(e.getMessage());
			logger.error("Exception occured in CmnProjectTeamsServiceImpl.deleteProjectTeamByTeamId()", e);
		}
		return cmnProjectTeamsResultSet;
	}

	@Override
	public CmnProjectTeamsResultSet deleteProjectTeams(List<CmnProjectTeamsDTO> cmnProjectTeamsDTOList) {
		CmnProjectTeamsResultSet cmnProjectTeamsResultSet = new CmnProjectTeamsResultSet();
		List<CmnProjectTeamsEntity> cmnProjectTeamsEntityList = new ArrayList<CmnProjectTeamsEntity>();
		List<CmnProjectTeamMembersEntity> cmnProjectTeamMembersEntityList = null;
		List<Long> list = new ArrayList<Long>();
		try {
			if (cmnProjectTeamsDTOList != null) {
				cmnProjectTeamsDTOList.forEach(objects -> {
					list.add(objects.getTeamId());
				});
				cmnProjectTeamMembersEntityList = cmnProjectTeamMembersRepository.getAllCmnProjectTeamMembersByTeamIds(list);
				if (cmnProjectTeamMembersEntityList != null) {
					cmnProjectTeamsResultSet.setStatus(false);
					cmnProjectTeamsResultSet.setMessage(ApplicationConstants.FAILED);
					cmnProjectTeamsResultSet
							.setMessageDescription("Project Teams assigned to a Project Members cannot be deleted.");
				} else {
					CmnProjectTeamsEntity cmnProjectTeamsEntity = new CmnProjectTeamsEntity();
					cmnProjectTeamsDTOList.forEach(objects -> {
						BeanUtils.copyProperties(objects, cmnProjectTeamsEntity);
						cmnProjectTeamsEntityList.add(cmnProjectTeamsEntity);
					});
					deleteAll(cmnProjectTeamsEntityList);
					cmnProjectTeamsResultSet.setMessageDescription("Project Teams deleted successfully.");
				}
			} else {
				cmnProjectTeamsResultSet.setStatus(false);
				cmnProjectTeamsResultSet.setMessage(ApplicationConstants.FAILED);
				cmnProjectTeamsResultSet.setMessageDescription("Invalid Inputs.");
			}
		} catch (Exception e) {
			cmnProjectTeamsResultSet.setStatus(false);
			cmnProjectTeamsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			cmnProjectTeamsResultSet.setMessageDescription(e.getMessage());
			logger.error("Exception occured in CmnProjectTeamsServiceImpl.deleteProjectTeams()", e);
		}

		return cmnProjectTeamsResultSet;
	}

}
