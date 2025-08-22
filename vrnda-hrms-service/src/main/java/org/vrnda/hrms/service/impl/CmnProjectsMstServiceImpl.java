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
import org.vrnda.hrms.entity.CmnProjectTeamsEntity;
import org.vrnda.hrms.entity.CmnProjectsMstEntity;
import org.vrnda.hrms.entity.EmployeeDetailsEntity;
import org.vrnda.hrms.repository.CmnProjectTeamsRepository;
import org.vrnda.hrms.repository.CmnProjectsMstRepository;
import org.vrnda.hrms.repository.EmployeeDetailsRepository;
import org.vrnda.hrms.repository.dto.CmnProjectTeamsDTO;
import org.vrnda.hrms.repository.dto.CmnProjectsMstDTO;
import org.vrnda.hrms.service.CmnProjectTeamsService;
import org.vrnda.hrms.service.CmnProjectsMstService;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.TasksAndAllocationsService;
import org.vrnda.hrms.service.resultset.CmnProjectsMstResultSet;
import org.vrnda.hrms.service.resultset.TasksAndAllocationsResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class CmnProjectsMstServiceImpl extends GenericServiceImpl<CmnProjectsMstEntity, Long>
		implements CmnProjectsMstService {

	@Autowired
	private CmnProjectsMstRepository cmnProjectsMstRepository;

	@Autowired
	private CmnProjectTeamsRepository cmnProjectTeamsRepository;

	@Autowired
	private EmployeeDetailsRepository employeeDetailsRepository;

	@Autowired
	private CmnTableSeqService cmnTableSeqService;

	@Autowired
	private CmnProjectTeamsService cmnProjectTeamsService;

	@Autowired
	private TasksAndAllocationsService tasksAndAllocationsService;

	private Logger logger = LogManager.getLogger(CmnProjectsMstServiceImpl.class.getName());

	public CmnProjectsMstServiceImpl(PagingAndSortingRepository<CmnProjectsMstEntity, Long> typeRepository) {
		super(typeRepository);
	}

	@Override
	public CmnProjectsMstResultSet saveOrUpdateProject(CmnProjectsMstDTO cmnProjectsMstDTO, String loggedInUser) {
		CmnProjectsMstEntity cmnProjectsMstEntity = null;
		CmnProjectsMstResultSet cmnProjectsMstResultSet = new CmnProjectsMstResultSet();
		try {
			// update
			if (cmnProjectsMstDTO.getProjectId() != null && cmnProjectsMstDTO.getProjectId() > 0) {
				cmnProjectsMstEntity = new CmnProjectsMstEntity();
				BeanUtils.copyProperties(cmnProjectsMstDTO, cmnProjectsMstEntity);
				BaseUtils.setBaseData(cmnProjectsMstEntity, loggedInUser);
				save(cmnProjectsMstEntity);
				cmnProjectsMstResultSet.setMessageDescription("Record Updated successfully.");

			}
			// Insert
			if (cmnProjectsMstDTO.getProjectId() == null || cmnProjectsMstDTO.getProjectId() == 0) {
				cmnProjectsMstEntity = new CmnProjectsMstEntity();
				BeanUtils.copyProperties(cmnProjectsMstDTO, cmnProjectsMstEntity);
				cmnProjectsMstEntity.setProjectId(cmnTableSeqService
						.getNextSequence(ApplicationConstants.CMN_PROJECTS_MST, ApplicationConstants.PROJECT_ID));
				BaseUtils.setBaseData(cmnProjectsMstEntity, loggedInUser);
				cmnProjectsMstEntity.setCreatedBy(loggedInUser);
				save(cmnProjectsMstEntity);

				CmnProjectTeamsDTO cmnProjectTeamsDTO = new CmnProjectTeamsDTO();
				cmnProjectTeamsDTO.setProjectId(cmnProjectsMstEntity.getProjectId());
				cmnProjectTeamsDTO.setTeamName(cmnProjectsMstDTO.getTeamName());
				cmnProjectTeamsDTO.setStatusLookupId(cmnProjectsMstDTO.getStatusLookupId());
				cmnProjectTeamsDTO.setSystemConfigFlag(cmnProjectsMstDTO.getSystemConfigFlag());
				cmnProjectTeamsDTO.setProjectHead(cmnProjectsMstEntity.getProjectHead());
				cmnProjectTeamsDTO.setScrumMaster(cmnProjectsMstDTO.getScrumMaster());
				cmnProjectTeamsService.saveorUpdateTeams(cmnProjectTeamsDTO, loggedInUser);
				cmnProjectsMstResultSet.setMessageDescription("Record saved successfully.");
			}
		} catch (Exception e) {
			cmnProjectsMstResultSet.setStatus(false);
			cmnProjectsMstResultSet.setMessage("Exception");
			cmnProjectsMstResultSet.setMessageDescription(e.getMessage());
			logger.error("Exception occured in CmnProjectsMstServiceImpl.saveOrUpdateProject()", e);
		}
		return cmnProjectsMstResultSet;
	}

	@Override
	public CmnProjectsMstResultSet getAllProjectsDetails() {
		CmnProjectsMstResultSet cmnProjectsResultSet = new CmnProjectsMstResultSet();
		List<CmnProjectsMstDTO> cmnProjectsMstDtoList = new ArrayList<CmnProjectsMstDTO>();
		try {
			List<Object[]> cmnProjectsMstEntity = cmnProjectsMstRepository.getAllProjectsDetails();
			if (cmnProjectsMstEntity != null && cmnProjectsMstEntity.size() > 0) {
				for (Object[] result : cmnProjectsMstEntity) {
					CmnProjectsMstDTO cmnProjectsMstDTO = new CmnProjectsMstDTO();
					CmnProjectsMstEntity cmnProjectsMstEntity1 = (CmnProjectsMstEntity) result[0];
					BeanUtils.copyProperties(cmnProjectsMstEntity1, cmnProjectsMstDTO);
					cmnProjectsMstDTO.setProjectHeadFullName((String) result[1]);
					cmnProjectsMstDtoList.add(cmnProjectsMstDTO);
				}
				cmnProjectsResultSet.setCmnProjectMstDtoList(cmnProjectsMstDtoList);
			} else {
				cmnProjectsResultSet.setStatus(false);
				cmnProjectsResultSet.setMessage(ApplicationConstants.FAILED);
				cmnProjectsResultSet.setMessageDescription("Projects does not exist");
			}
		} catch (Exception e) {
			cmnProjectsResultSet.setStatus(false);
			cmnProjectsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			cmnProjectsResultSet.setMessageDescription(e.getMessage());
			logger.error("Exception occured in CmnProjectsMstServiceImpl.getAllProject()", e);
		}
		return cmnProjectsResultSet;
	}

	@Override
	public CmnProjectsMstResultSet getAllProjects() {
		List<EmployeeDetailsEntity> employeeDetailsEntity = new ArrayList<EmployeeDetailsEntity>();
		Map<Long, String> map = new HashMap<Long, String>();
		CmnProjectsMstResultSet cmnProjectsResultSet = new CmnProjectsMstResultSet();
		try {
			employeeDetailsEntity = employeeDetailsRepository.getAllEmployeeDetails();
			employeeDetailsEntity.forEach(objects -> {
				map.put(objects.getEmployeeId(),
						objects.getEmployeeLastName().concat(" " + objects.getEmployeeFirstName()));
			});
			List<CmnProjectsMstEntity> cmnProjectsMstEntity = cmnProjectsMstRepository.getAllProjects();
			if (cmnProjectsMstEntity != null && cmnProjectsMstEntity.size() > 0) {
				List<CmnProjectsMstDTO> cmnProjectsMstDtoList = new ArrayList<CmnProjectsMstDTO>();
				cmnProjectsMstEntity.forEach(objects -> {
					CmnProjectsMstDTO cmnProjectsMstDTO = new CmnProjectsMstDTO();
					BeanUtils.copyProperties(objects, cmnProjectsMstDTO);
					cmnProjectsMstDtoList.add(cmnProjectsMstDTO);
				});
				cmnProjectsResultSet.setCmnProjectMstDtoList(cmnProjectsMstDtoList);
			} else {
				cmnProjectsResultSet.setStatus(false);
				cmnProjectsResultSet.setMessage(ApplicationConstants.FAILED);
				cmnProjectsResultSet.setMessageDescription("Projects does not exist");
			}
		} catch (Exception e) {
			cmnProjectsResultSet.setStatus(false);
			cmnProjectsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			cmnProjectsResultSet.setMessageDescription(e.getMessage());
			logger.error("Exception occured in CmnProjectsMstServiceImpl.getAllProject()", e);
		}
		return cmnProjectsResultSet;
	}

	@Override
	public CmnProjectsMstResultSet getProjectByProjectId(Long projectId) {
		CmnProjectsMstResultSet cmnProjectsResultSet = new CmnProjectsMstResultSet();
		try {
			List<Object[]> resultObject = cmnProjectsMstRepository.getProjectByProjectId(projectId);
			if (resultObject != null) {
				for (Object[] obj : resultObject) {
					CmnProjectsMstEntity cmnProjectsMstEntity = (CmnProjectsMstEntity) obj[0];
					CmnProjectsMstDTO cmnProjectsMstDTO = new CmnProjectsMstDTO();
					BeanUtils.copyProperties(cmnProjectsMstEntity, cmnProjectsMstDTO);
					cmnProjectsMstDTO.setProjectHeadFullName((String) obj[1]);
					cmnProjectsResultSet.setCmnProjectsMstDTO(cmnProjectsMstDTO);

				}
			} else {
				cmnProjectsResultSet.setStatus(false);
				cmnProjectsResultSet.setMessage(ApplicationConstants.FAILED);
				cmnProjectsResultSet.setMessageDescription("Project does not exist for the given Project Id.");
			}
		} catch (Exception e) {
			cmnProjectsResultSet.setStatus(false);
			cmnProjectsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			cmnProjectsResultSet.setMessageDescription(e.getMessage());
			logger.error("Exception occured in CmnProjectsMstServiceImpl.getProjectByProjectId()", e);
		}
		return cmnProjectsResultSet;
	}

	@Override
	public CmnProjectsMstResultSet getAllProjectsByStatusLookUpId(Long statusLookUpId) {
		CmnProjectsMstResultSet cmnProjectsResultSet = new CmnProjectsMstResultSet();
		List<CmnProjectsMstEntity> cmnProjectsMstEntity = null;
		try {
			cmnProjectsMstEntity = cmnProjectsMstRepository.getAllProjectsByStatusLookUpId(statusLookUpId);
			if (cmnProjectsMstEntity != null) {
				List<CmnProjectsMstDTO> cmnProjectsMstDTOList = new ArrayList<CmnProjectsMstDTO>();
				cmnProjectsMstEntity.forEach(objects -> {
					CmnProjectsMstDTO cmnProjectsMstDTO = new CmnProjectsMstDTO();
					BeanUtils.copyProperties(objects, cmnProjectsMstDTO);
					cmnProjectsMstDTOList.add(cmnProjectsMstDTO);
				});
				cmnProjectsResultSet.setCmnProjectMstDtoList(cmnProjectsMstDTOList);
			} else {
				cmnProjectsResultSet.setStatus(false);
				cmnProjectsResultSet.setMessage(ApplicationConstants.FAILED);
				cmnProjectsResultSet.setMessageDescription("No Active Projects are present");
			}
		} catch (Exception e) {
			cmnProjectsResultSet.setStatus(false);
			cmnProjectsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			cmnProjectsResultSet.setMessageDescription(e.getMessage());
			logger.error("Exception occured in CmnProjectsMstServiceImpl.getAllProjectsByStatusLookUpId()", e);
		}
		return cmnProjectsResultSet;
	}

	@Override
	public CmnProjectsMstResultSet deleteProjectById(Long projectId) {
		CmnProjectsMstResultSet cmnProjectsResultSet = new CmnProjectsMstResultSet();
		CmnProjectsMstEntity cmnProjectsMstEntity = null;
		List<Object[]> cmnProjectTeamsEntity = null;
		try {
			if (projectId != null) {
				List<Object[]> resultObject = cmnProjectsMstRepository.getProjectByProjectId(projectId);
				if (resultObject != null) {
					for (Object[] obj : resultObject) {
						cmnProjectsMstEntity = (CmnProjectsMstEntity) obj[0];
					}
				}
				if (cmnProjectsMstEntity != null) {
					cmnProjectTeamsEntity = cmnProjectTeamsRepository.getProjectTeamByProjectId(projectId);
					if (cmnProjectTeamsEntity != null) {
						cmnProjectsResultSet.setStatus(false);
						cmnProjectsResultSet.setMessage(ApplicationConstants.FAILED);
						cmnProjectsResultSet.setMessageDescription(" Project assigned to a Team cannot be deleted.");
					} else {
						TasksAndAllocationsResultSet tasksAndAllocationsList = tasksAndAllocationsService
								.getAllTaskDetailsByProjectId(projectId);
						if (tasksAndAllocationsList != null
								&& tasksAndAllocationsList.getTasksAndAllocationsEntityList() != null
								&& tasksAndAllocationsList.getTasksAndAllocationsEntityList().size() > 0) {
							cmnProjectsResultSet.setStatus(false);
							cmnProjectsResultSet.setMessage(ApplicationConstants.FAILED);
							cmnProjectsResultSet
									.setMessageDescription(" Project assigned to a Team cannot be deleted.");
						} else {
							delete(cmnProjectsMstEntity);
							cmnProjectsResultSet.setMessageDescription("Project deleted successfully.");
						}
					}
				} else {
					cmnProjectsResultSet.setStatus(false);
					cmnProjectsResultSet.setMessage(ApplicationConstants.FAILED);
					cmnProjectsResultSet.setMessageDescription("Invalid Inputs.");
				}
			} else {
				cmnProjectsResultSet.setStatus(false);
				cmnProjectsResultSet.setMessage(ApplicationConstants.FAILED);
				cmnProjectsResultSet.setMessageDescription("Invalid Inputs.");
			}
		} catch (Exception e) {
			cmnProjectsResultSet.setStatus(false);
			cmnProjectsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			cmnProjectsResultSet.setMessageDescription(e.getMessage());
			logger.error("Exception occured in CmnProjectsMstServiceImpl.deleteProjectById()", e);
		}
		return cmnProjectsResultSet;
	}

	@Override
	public CmnProjectsMstResultSet deleteProjectDetailsList(List<CmnProjectsMstDTO> CmnProjectsMstDTOList) {
		CmnProjectsMstResultSet cmnProjectsResultSet = new CmnProjectsMstResultSet();
		List<CmnProjectsMstEntity> cmnProjectsMstEntityList = new ArrayList<CmnProjectsMstEntity>();
		List<CmnProjectTeamsEntity> cmnProjectTeamsEntity = null;
		TasksAndAllocationsResultSet tasksAndAllocationsList = null;
		List<Long> list = new ArrayList<Long>();
		try {
			if (CmnProjectsMstDTOList != null) {
				CmnProjectsMstDTOList.forEach(objects -> {
					list.add(objects.getProjectId());
				});
				cmnProjectTeamsEntity = cmnProjectTeamsRepository.getProjectTeamsByProjectIds(list);
				if (cmnProjectTeamsEntity != null && cmnProjectTeamsEntity.size() > 0) {
					cmnProjectsResultSet.setStatus(false);
					cmnProjectsResultSet.setMessage(ApplicationConstants.FAILED);
					cmnProjectsResultSet.setMessageDescription(" Project assigned to a Team cannot be deleted.");
				} else {
					tasksAndAllocationsList = tasksAndAllocationsService.getAllTaskDetailsByProjectId(list);
					if (tasksAndAllocationsList != null
							&& tasksAndAllocationsList.getTasksAndAllocationsEntityList() != null
							&& tasksAndAllocationsList.getTasksAndAllocationsEntityList().size() > 0) {
						cmnProjectsResultSet.setStatus(false);
						cmnProjectsResultSet.setMessage(ApplicationConstants.FAILED);
						cmnProjectsResultSet.setMessageDescription(" Project assigned to a Team cannot be deleted.");
					} else {
						CmnProjectsMstEntity cmnProjectsMstEntity = new CmnProjectsMstEntity();
						CmnProjectsMstDTOList.forEach(objects -> {
							BeanUtils.copyProperties(objects, cmnProjectsMstEntity);
							cmnProjectsMstEntityList.add(cmnProjectsMstEntity);
						});
						deleteAll(cmnProjectsMstEntityList);
						cmnProjectsResultSet.setMessageDescription("Projects deleted successfully.");
					}
				}
			} else {
				cmnProjectsResultSet.setStatus(false);
				cmnProjectsResultSet.setMessage(ApplicationConstants.FAILED);
				cmnProjectsResultSet.setMessageDescription("Invalid Inputs.");
			}

		} catch (Exception e) {
			cmnProjectsResultSet.setStatus(false);
			cmnProjectsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			cmnProjectsResultSet.setMessageDescription(e.getMessage());
			logger.error("Exception occured in CmnProjectsMstServiceImpl.deleteProjectDetailsList()", e);
		}
		return cmnProjectsResultSet;
	}
}
