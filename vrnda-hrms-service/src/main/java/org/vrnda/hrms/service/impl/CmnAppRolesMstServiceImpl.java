package org.vrnda.hrms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.AppUsersMstEntity;
import org.vrnda.hrms.entity.CmnAppRolesMstEntity;
import org.vrnda.hrms.entity.CmnMenusMstEntity;
import org.vrnda.hrms.repository.AppUserMstRepository;
import org.vrnda.hrms.repository.CmnAppRolesMstRepository;
import org.vrnda.hrms.repository.CmnLookupMstRepository;
import org.vrnda.hrms.repository.CmnMenusMstRepository;
import org.vrnda.hrms.repository.dto.CmnAppRoleAccessPrivilegesDTO;
import org.vrnda.hrms.repository.dto.CmnAppRolesMstDTO;
import org.vrnda.hrms.service.CmnAppRoleAccessPrivilegesService;
import org.vrnda.hrms.service.CmnAppRolesMstService;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.resultset.AppRolesResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class CmnAppRolesMstServiceImpl extends GenericServiceImpl<CmnAppRolesMstEntity, Long>
		implements CmnAppRolesMstService {

	@Autowired
	private CmnAppRolesMstRepository cmnRoleMstRepository;

	@Autowired
	AppUserMstRepository appUserMstRepository;

	@Autowired
	CmnLookupMstRepository cmnLookupMstRepository;

	@Autowired
	CmnMenusMstRepository cmnMenusMstRepository;

	@Autowired
	CmnAppRoleAccessPrivilegesService cmnAppRoleAccessPrivilegesService;

	@Autowired
	CmnTableSeqService cmnTableSeqService;

	public CmnAppRolesMstServiceImpl(PagingAndSortingRepository<CmnAppRolesMstEntity, Long> typeRepository) {
		super(typeRepository);
	}

	@Override
	public AppRolesResultSet saveOrUpdateAppRole(CmnAppRolesMstDTO cmnAppRolesMstDTO, String loggedInUser) {
		AppRolesResultSet appRolesResultSet = new AppRolesResultSet();
		CmnAppRolesMstEntity cmnRoleMstEntity = null;
		try {
			Long inActiveFlagId = cmnLookupMstRepository.getLookupIdByLookupNameAndParentLookupName(
					ApplicationConstants.INACTIVE, ApplicationConstants.STATUS);
			if (cmnAppRolesMstDTO != null && cmnAppRolesMstDTO.getAppRoleId() == 0) {
				Long screenItemId = cmnLookupMstRepository.getLookupIdByLookupNameAndParentLookupName(
						ApplicationConstants.SCREEN_ITEM, ApplicationConstants.MENU_ITEM);
				List<CmnMenusMstEntity> menusList = new ArrayList<CmnMenusMstEntity>();
				menusList = cmnMenusMstRepository.getMenusByMenuTypeId(screenItemId);
				if (!verifyDuplicateRoleName(cmnAppRolesMstDTO.getAppRoleName(), 0l)) {
					cmnRoleMstEntity = new CmnAppRolesMstEntity();
					BeanUtils.copyProperties(cmnAppRolesMstDTO, cmnRoleMstEntity);
					cmnRoleMstEntity.setAppRoleId(cmnTableSeqService
							.getNextSequence(ApplicationConstants.CMN_APP_ROLES_MST, ApplicationConstants.APP_ROLE_ID));
					BaseUtils.setBaseData(cmnRoleMstEntity, loggedInUser);
					save(cmnRoleMstEntity);
					List<CmnAppRoleAccessPrivilegesDTO> cmnAppRoleAccessPrivilegesDTOList = new ArrayList<CmnAppRoleAccessPrivilegesDTO>();
					if (menusList != null && !menusList.isEmpty()) {
						for (CmnMenusMstEntity menuObj : menusList) {
							CmnAppRoleAccessPrivilegesDTO cmnAppRoleAccessPrivilegesDTO = new CmnAppRoleAccessPrivilegesDTO();
							cmnAppRoleAccessPrivilegesDTO.setAppRoleId(cmnRoleMstEntity.getAppRoleId());
							cmnAppRoleAccessPrivilegesDTO.setMenuId(menuObj.getMenuId());
							cmnAppRoleAccessPrivilegesDTO.setStatusLookupId(inActiveFlagId);
							cmnAppRoleAccessPrivilegesDTO.setDescription(menuObj.getDescription());
							cmnAppRoleAccessPrivilegesDTO.setSystemConfigFlag(menuObj.getSystemConfigFlag());
							cmnAppRoleAccessPrivilegesDTOList.add(cmnAppRoleAccessPrivilegesDTO);
						}
						cmnAppRoleAccessPrivilegesService.saveOrUpdateRoleAccessAccessprivileges(
								cmnAppRoleAccessPrivilegesDTOList, loggedInUser);
					}
					appRolesResultSet.setMessageDescription("Application Role saved successfully.");
				} else {
					appRolesResultSet.setStatus(false);
					appRolesResultSet.setMessage("Failed");
					appRolesResultSet.setMessageDescription(
							"An Application role already exists with same Application Role Name.");
					return appRolesResultSet;
				}
			} else if (cmnAppRolesMstDTO != null && cmnAppRolesMstDTO.getAppRoleId() > 0) {
				cmnRoleMstEntity = cmnRoleMstRepository.getAppRoleByAppRoleId(cmnAppRolesMstDTO.getAppRoleId());
				if (cmnRoleMstEntity != null) {
					if (verifyDuplicateRoleName(cmnAppRolesMstDTO.getAppRoleName(), cmnRoleMstEntity.getAppRoleId())) {
						appRolesResultSet.setStatus(false);
						appRolesResultSet.setMessage("Failed");
						appRolesResultSet.setMessageDescription(
								"An Application role already exists with same Application Role Name.");
						return appRolesResultSet;
					}
					if (cmnRoleMstEntity != null) {
						AppUsersMstEntity auEntity = appUserMstRepository
								.getAppUserByAppRoleId(cmnAppRolesMstDTO.getAppRoleId());
						if (auEntity != null) {
							if (!cmnAppRolesMstDTO.getStatusLookupId().equals(cmnRoleMstEntity.getStatusLookupId())
									&& inActiveFlagId == cmnAppRolesMstDTO.getStatusLookupId()) {
								appRolesResultSet.setStatus(false);
								appRolesResultSet.setMessage("Failed.");
								appRolesResultSet.setMessageDescription(
										"Application role assigned to a user cannot be deactivated.");
								return appRolesResultSet;
							}
						}
						BeanUtils.copyProperties(cmnAppRolesMstDTO, cmnRoleMstEntity);
						BaseUtils.modifyBaseData(cmnRoleMstEntity, loggedInUser);
						save(cmnRoleMstEntity);
						appRolesResultSet.setMessageDescription("Application Role updated successfully");
						return appRolesResultSet;
					}
				} else {
					appRolesResultSet.setStatus(false);
					appRolesResultSet.setMessage("Failed");
					appRolesResultSet.setMessageDescription("Cannot fine this Application Role in DB to update.");
					return appRolesResultSet;
				}
			}
		} catch (Exception e) {
			appRolesResultSet.setStatus(false);
			appRolesResultSet.setMessage("Exception");
			appRolesResultSet.setMessageDescription(
					"Exception occoured while adding/updating Application Role. Please contact administrator.");
		}
		return appRolesResultSet;
	}

	@Override
	public AppRolesResultSet deleteAppRoleByAppRoleId(Long appRoleId) {
		AppRolesResultSet appRolesResultSet = new AppRolesResultSet();
		CmnAppRolesMstEntity cmnRoleMstEntity = null;
		AppUsersMstEntity appUsersMstEntity = null;
		try {
			if (appRoleId != null) {
				cmnRoleMstEntity = cmnRoleMstRepository.getAppRoleByAppRoleId(appRoleId);
				if (cmnRoleMstEntity != null) {
					appUsersMstEntity = appUserMstRepository.getAppUserByAppRoleId(appRoleId);
					if (appUsersMstEntity != null) {
						appRolesResultSet.setStatus(false);
						appRolesResultSet.setMessage("Failed");
						appRolesResultSet
								.setMessageDescription("Application Roles assigned to a user cannot be deleted.");
					} else {
						cmnAppRoleAccessPrivilegesService.deleteRoleAccessbyRoleId(appRoleId);
						delete(cmnRoleMstEntity);
						appRolesResultSet.setMessageDescription("Application Role deleted successfully.");
					}
				}
			} else {
				appRolesResultSet.setStatus(false);
				appRolesResultSet.setMessage("Failed");
				appRolesResultSet.setMessageDescription("Invalid details submitted to delete.");
			}
		} catch (Exception e) {
			appRolesResultSet.setStatus(false);
			appRolesResultSet.setMessage("Exception");
			appRolesResultSet.setMessageDescription(
					"Excepton occured while deleting the Application Role. Please contact Administrator");
		}
		return appRolesResultSet;
	}

	@Override
	public AppRolesResultSet deleteAppRolesList(List<CmnAppRolesMstDTO> cmnAppRoleMstDTOList) {
		AppRolesResultSet appRolesResultSet = new AppRolesResultSet();
		int successCount = 0;
		int failureCount = 0;
		try {
			if (cmnAppRoleMstDTOList != null && cmnAppRoleMstDTOList.size() > 0) {
				for (CmnAppRolesMstDTO cmnAppRolesMstDTO : cmnAppRoleMstDTOList) {
					CmnAppRolesMstEntity cmnRoleMstEntity = cmnRoleMstRepository
							.getAppRoleByAppRoleId(cmnAppRolesMstDTO.getAppRoleId());
					if (cmnRoleMstEntity != null) {
						AppUsersMstEntity appUsersMstEntity = appUserMstRepository
								.getAppUserByAppRoleId(cmnAppRolesMstDTO.getAppRoleId());
						if (appUsersMstEntity != null) {
							failureCount++;
						} else {
							delete(cmnRoleMstEntity);
							successCount++;
						}
					}
				}
				if (successCount == 0) {
					appRolesResultSet.setMessage("Failed");
					appRolesResultSet.setMessageDescription(
							"Unable to delete rows. \n*Application Roles assigned to a user cannot be deleted.");
				} else if (failureCount > 0) {
					appRolesResultSet.setMessage("Partially success");
					appRolesResultSet.setMessageDescription(successCount + " rows deleted and failed to delete "
							+ failureCount + " rows. \n*Application Roles assigned to a user cannot be deleted.");
				} else {
					appRolesResultSet.setMessage("Success");
					appRolesResultSet.setMessageDescription("Application Roles deleted successfully.");
				}
				appRolesResultSet.setSuccessCount(successCount);
				appRolesResultSet.setFailureCount(failureCount);
			} else {
				appRolesResultSet.setStatus(false);
				appRolesResultSet.setMessage("Failed");
				appRolesResultSet.setMessageDescription("Invalid Inputs");
			}
		} catch (Exception e) {
			appRolesResultSet.setStatus(false);
			appRolesResultSet.setMessage("Exception");
			appRolesResultSet.setMessageDescription(
					"Excepton occured while deleting the Application Roles. Please contact Administrator");
		}
		return appRolesResultSet;
	}

	@Override
	public AppRolesResultSet getAllAppRoles() {
		AppRolesResultSet appRolesResultSet = new AppRolesResultSet();
		try {
			List<CmnAppRolesMstEntity> cmnEntities = (List<CmnAppRolesMstEntity>) cmnRoleMstRepository.getAllAppRoles();
			if (cmnEntities.size() > 0) {
				appRolesResultSet.setCmnAppRolesMstEntityList(cmnEntities);
			} else {
				appRolesResultSet.setStatus(false);
				appRolesResultSet.setMessage("Failed");
				appRolesResultSet.setMessageDescription("AppRole List not available");
			}
		} catch (Exception e) {
			e.printStackTrace();
			appRolesResultSet.setStatus(false);
			appRolesResultSet.setMessage("Exception");
			appRolesResultSet.setMessageDescription(e.getMessage());
		}
		return appRolesResultSet;
	}

	@Override
	public AppRolesResultSet getAppRoleByAppRoleId(Long appRoleId) {
		AppRolesResultSet appRolesResultSet = new AppRolesResultSet();
		CmnAppRolesMstEntity cmnAppRolesMstEntity = null;
		try {
			cmnAppRolesMstEntity = cmnRoleMstRepository.getAppRoleByAppRoleId(appRoleId);
			if (cmnAppRolesMstEntity != null) {
				appRolesResultSet.setCmnAppRolesMstEntity(cmnAppRolesMstEntity);
			} else {
				appRolesResultSet.setStatus(false);
				appRolesResultSet.setMessage("failed");
				appRolesResultSet.setMessageDescription("App role Id not exist");

			}
		} catch (Exception e) {
			appRolesResultSet.setStatus(false);
			appRolesResultSet.setMessage("Exception");
			appRolesResultSet.setMessageDescription(e.getMessage());
		}
		return appRolesResultSet;
	}

	@Override
	public AppRolesResultSet getAppRoleByStatusLookupId(Long statusLookupId) {
		AppRolesResultSet appRolesResultSet = new AppRolesResultSet();
		try {
			List<CmnAppRolesMstEntity> cmnEntities = cmnRoleMstRepository.getAppRoleByStatusLookupId(statusLookupId);
			List<CmnAppRolesMstDTO> cmnAppRoleDtoList = new ArrayList<CmnAppRolesMstDTO>();
			if (cmnEntities != null) {
				cmnEntities.forEach(e -> {
					CmnAppRolesMstDTO cmnAppRolesMstDTO = new CmnAppRolesMstDTO();
					BeanUtils.copyProperties(e, cmnAppRolesMstDTO);
					cmnAppRoleDtoList.add(cmnAppRolesMstDTO);
				});
				appRolesResultSet.setCmnAppRolesMstEntityDtoList(cmnAppRoleDtoList);
			} else {
				appRolesResultSet.setStatus(false);
				appRolesResultSet.setMessage("failed");
				appRolesResultSet.setMessageDescription("No Active App Roles");

			}
		} catch (Exception e) {
			appRolesResultSet.setStatus(false);
			appRolesResultSet.setMessage("Exception");
			appRolesResultSet.setMessageDescription(e.getMessage());
		}
		return appRolesResultSet;
	}

	@Override
	public boolean verifyDuplicateRoleName(String appRoleName, Long appRoleId) {
		if (appRoleName != null) {
			CmnAppRolesMstEntity tempAppRole = cmnRoleMstRepository.getAppRoleByAppRoleName(appRoleName);
			if (tempAppRole != null && appRoleId != null && appRoleId == 0)
				return true;
			else if (tempAppRole != null && appRoleId != null && appRoleId > 0
					&& tempAppRole.getAppRoleId() != appRoleId)
				return true;
			else
				return false;
		} else {
			return true;
		}
	}

}