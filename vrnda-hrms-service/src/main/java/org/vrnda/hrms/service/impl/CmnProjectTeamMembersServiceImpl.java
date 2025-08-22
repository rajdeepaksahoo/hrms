package org.vrnda.hrms.service.impl;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.CmnProjectTeamMembersEntity;
import org.vrnda.hrms.entity.CmnProjectTeamMembersEntityPk;
import org.vrnda.hrms.entity.TasksAndAllocationsEntity;
import org.vrnda.hrms.repository.CmnProjectTeamMembersRepository;
import org.vrnda.hrms.repository.CmnProjectTeamsRepository;
import org.vrnda.hrms.repository.EmployeeDetailsRepository;
import org.vrnda.hrms.repository.TasksAndAllocationsRepository;
import org.vrnda.hrms.repository.dto.CmnProjectTeamMembersDTO;
import org.vrnda.hrms.service.CmnLookupMstService;
import org.vrnda.hrms.service.CmnProjectTeamMembersService;
import org.vrnda.hrms.service.resultset.CmnLookupMstResultSet;
import org.vrnda.hrms.service.resultset.CmnProjectTeamMembersResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;


@Slf4j
@Service
public class CmnProjectTeamMembersServiceImpl extends GenericServiceImpl<CmnProjectTeamMembersEntity, CmnProjectTeamMembersEntityPk>
		implements CmnProjectTeamMembersService {

	@Autowired
	CmnProjectTeamMembersRepository cmnProjectTeamMembersRepository;

	@Autowired
	EmployeeDetailsRepository employeeDetailsRepository;

	@Autowired
	CmnProjectTeamsRepository cmnProjectTeamsRepository;

	@Autowired
	TasksAndAllocationsRepository tasksAndAllocationsRepository;

	@Autowired
	private CmnLookupMstService cmnLookupMstService;

	@Autowired
	public CmnProjectTeamMembersServiceImpl(
			PagingAndSortingRepository<CmnProjectTeamMembersEntity, CmnProjectTeamMembersEntityPk> typeRepository) {
		super(typeRepository);
	}

	@Override
	public CmnProjectTeamMembersResultSet saveOrUpdateCmnProjectTeamMembers(CmnProjectTeamMembersDTO cmnProjectTeamMembersDTO,
			String loggedInUser) {
		CmnProjectTeamMembersResultSet cmnProjectTeamMembersResultSet = new CmnProjectTeamMembersResultSet();
		CmnProjectTeamMembersEntity cmnProjectTeamMembersEntity = null;
		try {
			if (cmnProjectTeamMembersDTO != null) {
				if (ApplicationConstants.Y.equals(cmnProjectTeamMembersDTO.getIsNewRecord())) {
					if ((cmnProjectTeamMembersDTO.getProjectHead() == cmnProjectTeamMembersDTO.getScrumMasterId())
							&& cmnProjectTeamMembersDTO.getProjectHead() != null
							&& cmnProjectTeamMembersDTO.getScrumMasterId() != null) {
						cmnProjectTeamMembersEntity = new CmnProjectTeamMembersEntity();
						BeanUtils.copyProperties(cmnProjectTeamMembersDTO, cmnProjectTeamMembersEntity);
						cmnProjectTeamMembersEntity.setScrumMaster("Y");
						cmnProjectTeamMembersEntity.setEmployeeId(cmnProjectTeamMembersDTO.getProjectHead());
						BaseUtils.setBaseData(cmnProjectTeamMembersEntity, loggedInUser);
						save(cmnProjectTeamMembersEntity);
						cmnProjectTeamMembersResultSet.setMessageDescription("Record Saved Successfully");
					} else if (cmnProjectTeamMembersDTO.getProjectHead() == null
							&& cmnProjectTeamMembersDTO.getScrumMasterId() == null) {
						cmnProjectTeamMembersEntity = new CmnProjectTeamMembersEntity();
						BeanUtils.copyProperties(cmnProjectTeamMembersDTO, cmnProjectTeamMembersEntity);
						BaseUtils.setBaseData(cmnProjectTeamMembersEntity, loggedInUser);
						save(cmnProjectTeamMembersEntity);
						cmnProjectTeamMembersResultSet.setMessageDescription("Record Saved Successfully");
					}
					else {
						cmnProjectTeamMembersEntity = new CmnProjectTeamMembersEntity();
						BeanUtils.copyProperties(cmnProjectTeamMembersDTO, cmnProjectTeamMembersEntity);
						cmnProjectTeamMembersEntity.setScrumMaster("Y");
						cmnProjectTeamMembersEntity.setEmployeeId(cmnProjectTeamMembersDTO.getScrumMasterId());
						BaseUtils.setBaseData(cmnProjectTeamMembersEntity, loggedInUser);
						save(cmnProjectTeamMembersEntity);

						cmnProjectTeamMembersEntity = new CmnProjectTeamMembersEntity();
						BeanUtils.copyProperties(cmnProjectTeamMembersDTO, cmnProjectTeamMembersEntity);
						cmnProjectTeamMembersEntity.setScrumMaster("N");
						cmnProjectTeamMembersEntity.setEmployeeId(cmnProjectTeamMembersDTO.getProjectHead());
						BaseUtils.setBaseData(cmnProjectTeamMembersEntity, loggedInUser);
						save(cmnProjectTeamMembersEntity);
						cmnProjectTeamMembersResultSet.setMessageDescription("Record Saved Successfully");
					}
				} else {
					cmnProjectTeamMembersEntity = new CmnProjectTeamMembersEntity();
					if (cmnProjectTeamMembersDTO.getProjectId() != null && cmnProjectTeamMembersDTO.getTeamId() != null
							&& cmnProjectTeamMembersDTO.getEmployeeId() != null) {
						cmnProjectTeamMembersEntity = cmnProjectTeamMembersRepository.getCmnProjectTeamMembersByProjIdModIdEmpId(
								cmnProjectTeamMembersDTO.getProjectId(), cmnProjectTeamMembersDTO.getTeamId(),
								cmnProjectTeamMembersDTO.getEmployeeId());
						CmnProjectTeamMembersEntity cmnProjectTeamMembersEntity1 = new CmnProjectTeamMembersEntity();
						BeanUtils.copyProperties(cmnProjectTeamMembersDTO, cmnProjectTeamMembersEntity1);
						BaseUtils.modifyBaseData(cmnProjectTeamMembersEntity1, loggedInUser);
						cmnProjectTeamMembersEntity1.setCreatedBy(loggedInUser);;
						save(cmnProjectTeamMembersEntity1);
						cmnProjectTeamMembersResultSet.setMessageDescription("Record Updated Successfully");
					}
				}
			} else {
				cmnProjectTeamMembersResultSet.setStatus(false);
				cmnProjectTeamMembersResultSet.setMessage(ApplicationConstants.FAILED);
				cmnProjectTeamMembersResultSet.setMessageDescription("Invalid Input.");
			}
		} catch (Exception e) {
			cmnProjectTeamMembersResultSet.setStatus(false);
			cmnProjectTeamMembersResultSet.setMessage(ApplicationConstants.EXCEPTION);
			cmnProjectTeamMembersResultSet.setMessageDescription(e.getMessage());
			log.error("Exception occured in " + this.getClass().getName() + " .saveOrUpdateCmnProjectTeamMembers() ");
		}

		return cmnProjectTeamMembersResultSet;
	}

	private boolean verifyDuplicateProjectId(CmnProjectTeamMembersDTO cmnProjectTeamMembersDTO) {
		CmnProjectTeamMembersEntity tempCmnConfig = cmnProjectTeamMembersRepository.getCmnProjectTeamMembersByProjIdModIdEmpId(
				cmnProjectTeamMembersDTO.getProjectId(), cmnProjectTeamMembersDTO.getTeamId(),
				cmnProjectTeamMembersDTO.getEmployeeId());
		if (tempCmnConfig != null) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public CmnProjectTeamMembersResultSet getAllCmnProjectTeamMembers() {
		CmnProjectTeamMembersResultSet cmnProjectTeamMembersResultSet = new CmnProjectTeamMembersResultSet();

		try {
			List<CmnProjectTeamMembersEntity> cmnProjectTeamMembersEntity = cmnProjectTeamMembersRepository
					.getAllCmnProjectTeamMembers();
			if (cmnProjectTeamMembersEntity != null) {
				List<CmnProjectTeamMembersDTO> list = new ArrayList<CmnProjectTeamMembersDTO>();
				cmnProjectTeamMembersEntity.forEach(objects -> {
					CmnProjectTeamMembersDTO cmnProjectTeamMembersDTO = new CmnProjectTeamMembersDTO();
					BeanUtils.copyProperties(objects, cmnProjectTeamMembersDTO);
					list.add(cmnProjectTeamMembersDTO);
				});
				cmnProjectTeamMembersResultSet.setCmnProjectTeamMembersDtoList(list);
			} else {
				cmnProjectTeamMembersResultSet.setStatus(false);
				cmnProjectTeamMembersResultSet.setMessage(ApplicationConstants.FAILED);
				cmnProjectTeamMembersResultSet.setMessageDescription("Project Members list not available");
			}
		} catch (Exception e) {
			e.printStackTrace();
			cmnProjectTeamMembersResultSet.setStatus(false);
			cmnProjectTeamMembersResultSet.setMessage(ApplicationConstants.EXCEPTION);
			cmnProjectTeamMembersResultSet.setMessageDescription(e.getMessage());
			log.error("Exception occured in " + this.getClass().getName() + " .getAllCmnProjectTeamMembers() ");
		}
		return cmnProjectTeamMembersResultSet;
	}

	@Override
	public CmnProjectTeamMembersResultSet getAllCmnProjectTeamMembersByStatusLookUpId(Long statusLookUpId) {
		List<CmnProjectTeamMembersEntity> cmnProjectTeamMembersEntity = null;
		CmnProjectTeamMembersResultSet cmnProjectTeamMembersResultSet = new CmnProjectTeamMembersResultSet();
		try {
			cmnProjectTeamMembersEntity = cmnProjectTeamMembersRepository
					.getAllCmnProjectTeamMembersByStatusLookUpId(statusLookUpId);
			if (cmnProjectTeamMembersEntity != null) {
				List<CmnProjectTeamMembersDTO> list = new ArrayList<CmnProjectTeamMembersDTO>();
				cmnProjectTeamMembersEntity.forEach(objects -> {
					CmnProjectTeamMembersDTO cmnProjectTeamMembersDTO = new CmnProjectTeamMembersDTO();
					BeanUtils.copyProperties(objects, cmnProjectTeamMembersDTO);
					list.add(cmnProjectTeamMembersDTO);
				});
				cmnProjectTeamMembersResultSet.setCmnProjectTeamMembersDtoList(list);
			} else {
				cmnProjectTeamMembersResultSet.setStatus(false);
				cmnProjectTeamMembersResultSet.setMessage(ApplicationConstants.FAILED);
				cmnProjectTeamMembersResultSet.setMessageDescription("Project Members list not available");
			}
		} catch (Exception e) {
			e.printStackTrace();
			cmnProjectTeamMembersResultSet.setStatus(false);
			cmnProjectTeamMembersResultSet.setMessage(ApplicationConstants.EXCEPTION);
			cmnProjectTeamMembersResultSet.setMessageDescription(e.getMessage());
			log.error("Exception occured in " + this.getClass().getName()
					+ " .getAllCmnProjectTeamMembersByStatusLookUpId() ");
		}
		return cmnProjectTeamMembersResultSet;

	}

	@Override
	public CmnProjectTeamMembersResultSet getAllCmnProjectTeamMembersByProjectId(Long projectId) {
		List<Object[]> cmnProjectTeamMembersEntity = null;
		CmnProjectTeamMembersResultSet cmnProjectTeamMembersResultSet = new CmnProjectTeamMembersResultSet();
		try {
			cmnProjectTeamMembersEntity = cmnProjectTeamMembersRepository.getAllCmnProjectTeamMembersByProjectId(projectId);
			if (cmnProjectTeamMembersEntity != null) {
				List<CmnProjectTeamMembersDTO> list = new ArrayList<CmnProjectTeamMembersDTO>();
				cmnProjectTeamMembersEntity.forEach(objects -> {
					CmnProjectTeamMembersEntity cmnProjectTeamMembersEntityObj = (CmnProjectTeamMembersEntity) objects[0];
					CmnProjectTeamMembersDTO cmnProjectTeamMembersDTO = new CmnProjectTeamMembersDTO();
					BeanUtils.copyProperties(cmnProjectTeamMembersEntityObj, cmnProjectTeamMembersDTO);
					cmnProjectTeamMembersDTO.setEmployeeFullName((String) objects[1]);
					list.add(cmnProjectTeamMembersDTO);
				});
				cmnProjectTeamMembersResultSet.setCmnProjectTeamMembersDtoList(list);
			} else {
				cmnProjectTeamMembersResultSet.setStatus(false);
				cmnProjectTeamMembersResultSet.setMessage(ApplicationConstants.FAILED);
				cmnProjectTeamMembersResultSet.setMessageDescription("Project Id does not exist");
			}
		} catch (Exception e) {
			cmnProjectTeamMembersResultSet.setStatus(false);
			cmnProjectTeamMembersResultSet.setMessage(ApplicationConstants.EXCEPTION);
			cmnProjectTeamMembersResultSet.setMessageDescription(e.getMessage());
			log.error(
					"Exception occured in " + this.getClass().getName() + " .getAllCmnProjectTeamMembersByProjectId() ");
		}
		return cmnProjectTeamMembersResultSet;
	}

	@Override
	public CmnProjectTeamMembersResultSet getAllCmnProjectTeamMembersByTeamId(Long teamId) {
		List<Object[]> cmnProjectTeamMembersEntity = null;

		CmnProjectTeamMembersResultSet cmnProjectTeamMembersResultSet = new CmnProjectTeamMembersResultSet();
		try {
			cmnProjectTeamMembersEntity = cmnProjectTeamMembersRepository.getAllCmnProjectTeamMembersByTeamId(teamId);
			if (cmnProjectTeamMembersEntity != null) {
				List<CmnProjectTeamMembersDTO> list = new ArrayList<CmnProjectTeamMembersDTO>();
				cmnProjectTeamMembersEntity.forEach(objects -> {
					CmnProjectTeamMembersEntity CmnProjectTeamMembersEntityObj = (CmnProjectTeamMembersEntity) objects[0];
					CmnProjectTeamMembersDTO cmnProjectTeamMembersDTO = new CmnProjectTeamMembersDTO();
					BeanUtils.copyProperties(CmnProjectTeamMembersEntityObj, cmnProjectTeamMembersDTO);
					cmnProjectTeamMembersDTO.setTeamName((String) objects[1]);
					list.add(cmnProjectTeamMembersDTO);
				});
				cmnProjectTeamMembersResultSet.setCmnProjectTeamMembersDtoList(list);
			} else {
				cmnProjectTeamMembersResultSet.setStatus(false);
				cmnProjectTeamMembersResultSet.setMessage(ApplicationConstants.FAILED);
				cmnProjectTeamMembersResultSet.setMessageDescription("Module Id does not exist");
			}
		} catch (Exception e) {
			cmnProjectTeamMembersResultSet.setStatus(false);
			cmnProjectTeamMembersResultSet.setMessage(ApplicationConstants.EXCEPTION);
			cmnProjectTeamMembersResultSet.setMessageDescription(e.getMessage());
			log.error(
					"Exception occured in " + this.getClass().getName() + " .getAllCmnProjectTeamMembersByModuleId() ");
		}
		return cmnProjectTeamMembersResultSet;
	}

	@Override
	public CmnProjectTeamMembersResultSet getAllProjectTeamMembersByProjectIdAndScrumMaster(Long projectId) {
		List<Object[]> cmnProjectTeamMembersEntity = null;
		CmnProjectTeamMembersResultSet cmnProjectTeamMembersResultSet = new CmnProjectTeamMembersResultSet();
		try {
			cmnProjectTeamMembersEntity = cmnProjectTeamMembersRepository
					.getAllProjectTeamMembersByProjectIdAndScrumMaster(projectId);
			if (cmnProjectTeamMembersEntity != null) {
				List<CmnProjectTeamMembersDTO> list = new ArrayList<CmnProjectTeamMembersDTO>();
				for (Object[] objects : cmnProjectTeamMembersEntity) {
					CmnProjectTeamMembersEntity CmnProjectTeamMembersEntityObj = (CmnProjectTeamMembersEntity) objects[0];
					CmnProjectTeamMembersDTO cmnProjectTeamMembersDTO = new CmnProjectTeamMembersDTO();
					BeanUtils.copyProperties(CmnProjectTeamMembersEntityObj, cmnProjectTeamMembersDTO);
					cmnProjectTeamMembersDTO.setEmployeeFullName((String) objects[1]);
					list.add(cmnProjectTeamMembersDTO);
				}
				;
				cmnProjectTeamMembersResultSet.setCmnProjectTeamMembersDtoList(list);
			} else {
				cmnProjectTeamMembersResultSet.setStatus(false);
				cmnProjectTeamMembersResultSet.setMessage(ApplicationConstants.FAILED);
				cmnProjectTeamMembersResultSet.setMessageDescription("Project Id does not exist");
			}
		} catch (Exception e) {
			cmnProjectTeamMembersResultSet.setStatus(false);
			cmnProjectTeamMembersResultSet.setMessage(ApplicationConstants.EXCEPTION);
			cmnProjectTeamMembersResultSet.setMessageDescription(e.getMessage());
			log.error("Exception occured in " + this.getClass().getName()
					+ " .getAllProjectTeamMembersByProjectIdAndScrumMaster() ");
		}
		return cmnProjectTeamMembersResultSet;

	}

	@Override
	public CmnProjectTeamMembersResultSet getAllCmnProjectTeamMembersBySearchParams(CmnProjectTeamMembersDTO cmnProjectTeamMembersDTO) {
		CmnProjectTeamMembersResultSet cmnProjectTeamMembersResultSet = new CmnProjectTeamMembersResultSet();

		return cmnProjectTeamMembersResultSet;
	}

	@Override
	public CmnProjectTeamMembersResultSet getAllCmnProjectTeamMembersByProjectIdModuleId(Long projectId, Long teamId) {
		CmnProjectTeamMembersResultSet cmnProjectTeamMembersResultSet = new CmnProjectTeamMembersResultSet();
		List<CmnProjectTeamMembersDTO> cmnProjectTeamMembersDTOList = new ArrayList<CmnProjectTeamMembersDTO>();
		try {
			List<Object[]> cmnProjectTeamMembersEntity = cmnProjectTeamMembersRepository
					.getAllCmnProjectTeamMembersByTeamId(projectId, teamId);
			if (cmnProjectTeamMembersEntity != null && cmnProjectTeamMembersEntity.size() > 0) {
				for (Object[] result : cmnProjectTeamMembersEntity) {
					CmnProjectTeamMembersDTO cmnProjectTeamMembersDTO = new CmnProjectTeamMembersDTO();
					CmnProjectTeamMembersEntity cmnProjectsMstEntity1 = (CmnProjectTeamMembersEntity) result[0];
					BeanUtils.copyProperties(cmnProjectsMstEntity1, cmnProjectTeamMembersDTO);
					cmnProjectTeamMembersDTO.setEmployeeFullName((String) result[1]);
					cmnProjectTeamMembersDTO.setProjectName((String) result[2]);
					cmnProjectTeamMembersDTO.setTeamName((String) result[3]);
					cmnProjectTeamMembersDTO.setRoleName((String) result[4]);
					cmnProjectTeamMembersDTOList.add(cmnProjectTeamMembersDTO);
				}
				cmnProjectTeamMembersResultSet.setCmnProjectTeamMembersDtoList(cmnProjectTeamMembersDTOList);
			} else {
				cmnProjectTeamMembersResultSet.setStatus(false);
				cmnProjectTeamMembersResultSet.setMessage(ApplicationConstants.FAILED);
				cmnProjectTeamMembersResultSet.setMessageDescription("Projects does not exist");
			}
		} catch (Exception e) {
			cmnProjectTeamMembersResultSet.setStatus(false);
			cmnProjectTeamMembersResultSet.setMessage(ApplicationConstants.EXCEPTION);
			cmnProjectTeamMembersResultSet.setMessageDescription(e.getMessage());
			log.error("Exception occured in " + this.getClass().getName()
					+ " .getAllCmnProjectTeamMembersByProjectIdModuleId() ");
		}
		return cmnProjectTeamMembersResultSet;
	}

	@Override
	public CmnProjectTeamMembersResultSet getAllDetails() {
		CmnProjectTeamMembersResultSet cmnProjectTeamMembersResultSet = new CmnProjectTeamMembersResultSet();
		List<CmnProjectTeamMembersDTO> cmnProjectTeamMembersDTOList = new ArrayList<CmnProjectTeamMembersDTO>();
		try {
			List<Object[]> cmnProjectTeamMembersEntity = cmnProjectTeamMembersRepository.getAllDetails();
			if (cmnProjectTeamMembersEntity != null && cmnProjectTeamMembersEntity.size() > 0) {
				for (Object[] result : cmnProjectTeamMembersEntity) {
					CmnProjectTeamMembersDTO cmnProjectTeamMembersDTO = new CmnProjectTeamMembersDTO();
					CmnProjectTeamMembersEntity cmnProjectsMstEntity1 = (CmnProjectTeamMembersEntity) result[0];
					BeanUtils.copyProperties(cmnProjectsMstEntity1, cmnProjectTeamMembersDTO);
					cmnProjectTeamMembersDTO.setEmployeeFullName((String) result[1]);
					cmnProjectTeamMembersDTO.setProjectName((String) result[2]);
					cmnProjectTeamMembersDTO.setTeamName((String) result[3]);
					cmnProjectTeamMembersDTO.setRoleName((String) result[4]);
					cmnProjectTeamMembersDTOList.add(cmnProjectTeamMembersDTO);
				}
				cmnProjectTeamMembersResultSet.setCmnProjectTeamMembersDtoList(cmnProjectTeamMembersDTOList);
			}
		} catch (Exception e) {
			log.error("Exception occured in " + this.getClass().getName() + " .getAllDetails() ");
			cmnProjectTeamMembersResultSet.setStatus(false);
			cmnProjectTeamMembersResultSet.setMessage(ApplicationConstants.EXCEPTION);
			cmnProjectTeamMembersResultSet.setMessageDescription(e.getMessage());
		}
		return cmnProjectTeamMembersResultSet;
	}

	public CmnProjectTeamMembersResultSet deleteByProjectId(CmnProjectTeamMembersDTO projectmembers) {
		CmnProjectTeamMembersResultSet cmnProjectTeamMembersResultSet = new CmnProjectTeamMembersResultSet();
		List<TasksAndAllocationsEntity> tasksAndAllocationsEntity = null;
		List<Long> list = new ArrayList<Long>();
		try {
			if (projectmembers != null) {
				CmnLookupMstResultSet cmnLookupMstTaskStatusResultSet = cmnLookupMstService
						.getLookupIdAndNameListByParentLookupName(ApplicationConstants.TASK_STATUS);
				if (cmnLookupMstTaskStatusResultSet != null
						&& cmnLookupMstTaskStatusResultSet.getCmnLookupMstDtoList() != null) {
					cmnLookupMstTaskStatusResultSet.getCmnLookupMstDtoList().forEach(e -> {
						if (ApplicationConstants.DELIVERED.equalsIgnoreCase(e.getLookupName())) {
							list.add(e.getLookupId());
						} else if (ApplicationConstants.CLOSED.equalsIgnoreCase(e.getLookupName())) {
							list.add(e.getLookupId());
						}
					});
				}
				tasksAndAllocationsEntity = tasksAndAllocationsRepository.getTasksAllocationDetailsByEmp(
						projectmembers.getProjectId(), projectmembers.getEmployeeId(), list);
				if (tasksAndAllocationsEntity != null && tasksAndAllocationsEntity.size() > 0) {
					cmnProjectTeamMembersResultSet.setStatus(false);
					cmnProjectTeamMembersResultSet.setMessage(ApplicationConstants.FAILED);
					cmnProjectTeamMembersResultSet.setMessageDescription(
							"Unable to delete has Active Tasks are Allocated to selected Member. ");

				} else {
					CmnProjectTeamMembersEntity cmnProjectTeamMembersEntity = new CmnProjectTeamMembersEntity();
					BeanUtils.copyProperties(projectmembers, cmnProjectTeamMembersEntity);
					delete(cmnProjectTeamMembersEntity);
					cmnProjectTeamMembersResultSet.setStatus(true);
					cmnProjectTeamMembersResultSet.setMessage(ApplicationConstants.SUCCESS);
					cmnProjectTeamMembersResultSet.setMessageDescription("Record deleted successfully");
				}
			}

		} catch (Exception e) {
			cmnProjectTeamMembersResultSet.setStatus(false);
			cmnProjectTeamMembersResultSet.setMessage(ApplicationConstants.FAILED);
			cmnProjectTeamMembersResultSet.setMessageDescription("Unable to delete Projet Member");
			log.error("Exception occured in " + this.getClass().getName() + " .deleteByProjectId() ");
		}

		return cmnProjectTeamMembersResultSet;
	}

	@Override
	public CmnProjectTeamMembersResultSet deleteProjectTeamMembersDetailsList(List<CmnProjectTeamMembersDTO> projectmembers) {
		int successCount = 0;
		int failureCount = 0;
		CmnProjectTeamMembersResultSet cmnProjectTeamMembersResultSet = new CmnProjectTeamMembersResultSet();
		List<CmnProjectTeamMembersEntity> cmnProjectTeamMembersEntityList = new ArrayList<CmnProjectTeamMembersEntity>();
		List<TasksAndAllocationsEntity> tasksAndAllocationsEntity = null;
		List<Long> list = new ArrayList<Long>();
		try {
			CmnLookupMstResultSet cmnLookupMstTaskStatusResultSet = cmnLookupMstService
					.getLookupIdAndNameListByParentLookupName(ApplicationConstants.TASK_STATUS);
			if (cmnLookupMstTaskStatusResultSet != null
					&& cmnLookupMstTaskStatusResultSet.getCmnLookupMstDtoList() != null) {
				cmnLookupMstTaskStatusResultSet.getCmnLookupMstDtoList().forEach(e -> {
					if (ApplicationConstants.DELIVERED.equalsIgnoreCase(e.getLookupName())) {
						list.add(e.getLookupId());
					} else if (ApplicationConstants.CLOSED.equalsIgnoreCase(e.getLookupName())) {
						list.add(e.getLookupId());
					}
				});
			}
			for (CmnProjectTeamMembersDTO objects : projectmembers) {
				tasksAndAllocationsEntity = tasksAndAllocationsRepository
						.getTasksAllocationDetailsByEmp(objects.getProjectId(), objects.getEmployeeId(), list);
				if (tasksAndAllocationsEntity != null && tasksAndAllocationsEntity.size() > 0) {
					cmnProjectTeamMembersResultSet.setStatus(false);
					cmnProjectTeamMembersResultSet.setMessage(ApplicationConstants.FAILED);
					cmnProjectTeamMembersResultSet.setMessageDescription(
							"Unable to delete has Active Tasks are Allocated to selected Member. ");
					failureCount++;
				} else {
					CmnProjectTeamMembersEntity cmnProjectTeamMembersEntity = new CmnProjectTeamMembersEntity();
					BeanUtils.copyProperties(objects, cmnProjectTeamMembersEntity);
					cmnProjectTeamMembersEntityList.add(cmnProjectTeamMembersEntity);
					successCount++;
				}
			}
			deleteAll(cmnProjectTeamMembersEntityList);
			if (successCount == 0) {
				cmnProjectTeamMembersResultSet.setMessage(ApplicationConstants.FAILED);
				cmnProjectTeamMembersResultSet.setMessageDescription(
						"Unable to delete rows. \n*Employee  Roles assigned to a user cannot be deleted.");
			} else if (failureCount > 0) {
				cmnProjectTeamMembersResultSet.setMessage("Partially success");
				cmnProjectTeamMembersResultSet.setMessageDescription(successCount + " rows deleted and failed to delete "
						+ failureCount + " rows. \n*Employee  Roles assigned to a user cannot be deleted.");
			} else {
				cmnProjectTeamMembersResultSet.setMessage(ApplicationConstants.SUCCESS);
				cmnProjectTeamMembersResultSet.setMessageDescription(successCount + " row deleted successfully.");
			}
			cmnProjectTeamMembersResultSet.setSuccessCount(successCount);
			cmnProjectTeamMembersResultSet.setFailureCount(failureCount);
		} catch (Exception e) {
			cmnProjectTeamMembersResultSet.setStatus(false);
			cmnProjectTeamMembersResultSet.setMessage(ApplicationConstants.FAILED);
			cmnProjectTeamMembersResultSet.setMessageDescription("Unable to delete Projet Member");
			log.error(
					"Exception occured in " + this.getClass().getName() + " .deleteProjectTeamMembersDetailsList() ");
		}

		return cmnProjectTeamMembersResultSet;
	}

}
