package org.vrnda.hrms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.AppUsersMstEntity;
import org.vrnda.hrms.entity.CmnMenusMstEntity;
import org.vrnda.hrms.entity.EmployeeDetailsEntity;
import org.vrnda.hrms.repository.AppUserMstRepository;
import org.vrnda.hrms.repository.dto.AppUsersMstDTO;
import org.vrnda.hrms.repository.dto.CmnAppRoleAccessPrivilegesDTO;
import org.vrnda.hrms.repository.dto.CmnLookupMstDTO;
import org.vrnda.hrms.repository.dto.CmnMenusMstDTO;
import org.vrnda.hrms.repository.dto.LoggedInUserInformationDTO;
import org.vrnda.hrms.repository.dto.StatusLookupIdDTO;
import org.vrnda.hrms.repository.dto.UIMenusDTO;
import org.vrnda.hrms.service.AppUsersMstService;
import org.vrnda.hrms.service.CmnAppRoleAccessPrivilegesService;
import org.vrnda.hrms.service.CmnAppRolesMstService;
import org.vrnda.hrms.service.CmnLookupMstService;
import org.vrnda.hrms.service.CmnMenusMstService;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.EmployeeDetailsService;
import org.vrnda.hrms.service.resultset.AllUserDetailsResultSet;
import org.vrnda.hrms.service.resultset.AppRolesResultSet;
import org.vrnda.hrms.service.resultset.AppUsersMstResultSet;
import org.vrnda.hrms.service.resultset.EmployeeDtlsResultSet;
import org.vrnda.hrms.service.resultset.RoleAccessPrivilegesResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;
import org.vrnda.hrms.utils.PasswordGenerator;
import org.vrnda.hrms.utils.mailutils.MailDto;
import org.vrnda.hrms.utils.mailutils.MailUtilityService;

@Slf4j
@Service
public class AppUsersMstServiceImpl extends GenericServiceImpl<AppUsersMstEntity, String>
		implements AppUsersMstService {

	@Autowired
	AppUserMstRepository appUserMstRepository;

	@Autowired
	EmployeeDetailsService employeeDetailsService;

	@Autowired
	CmnAppRoleAccessPrivilegesService cmnAppRoleAccessPrivilegesService;

	@Autowired
	CmnAppRolesMstService cmnAppRoleMstService;

	@Autowired
	CmnLookupMstService cmnLookupMstService;

	@Autowired
	CmnMenusMstService cmnMenusMstService;

	@Autowired
	CmnTableSeqService cmnTableSeqService;

	@Autowired
	MailUtilityService mailUtilityService;

	public AppUsersMstServiceImpl(PagingAndSortingRepository<AppUsersMstEntity, String> typeRepository) {
		super(typeRepository);
	}

	@Override
	public AppUsersMstResultSet doLogin(String userName, String password) {
		AppUsersMstResultSet aumResultSet = new AppUsersMstResultSet();
		try {
			AppUsersMstEntity aumEntity = appUserMstRepository.getAppUserByUserNameAndPassword(userName, password);
    			List<AppUsersMstEntity> all = appUserMstRepository.findAll();
			if (aumEntity != null) {
				aumResultSet.setAppUsersMstEntity(aumEntity);
			} else {
				aumResultSet.setStatus(false);
				aumResultSet.setMessage(ApplicationConstants.FAILED);
				aumResultSet.setMessageDescription("Invalid credentials");
			}
		} catch (Exception e) {
			aumResultSet.setStatus(false);
			aumResultSet.setMessage(ApplicationConstants.EXCEPTION);
			aumResultSet.setMessageDescription(e.getMessage());
		}
		return aumResultSet;
	}

	Boolean sentEmailForGeneratedPassword(String recipient, String password) {
		MailDto mailDto = new MailDto();
		Boolean isMailSent = false;
		try {
			List<String> recipients = new ArrayList<>();
			recipients.add(recipient);
			mailDto.setSubject("HRMS Account Password");
			String content = "<p>Hello " + recipient + ", </p>"
					+ "<p>We are delighted to welcome you to our HRMS (Human Resource Management System) platform. Your account has been successfully created, and here are your login details:</p>"
					+ "<p>Username: <b>" + recipient + "</b></p>" + "<p>Temporary Password: <b>" + password + "</b></p>"
					+ "<p>To ensure the security of your account, please follow these steps:</p>"
					+ "<p>Login: Visit HRMS login page</p>" + "<p>Username: Enter your email address</p>"
					+ "<p>Password: Use the temporary password provided above.</p>"
					+ "<p>Upon your first login, you will be prompted to change your password to something more secure and memorable. Please make sure to create a strong password that includes a combination of uppercase letters, lowercase letters, numbers, and special characters.</p>"
					+ "<p>If you have any questions or need assistance with your account setup, please don't hesitate to reach out to HR department.</p>"
					+ "<p>Thank you for being a part of Vrnda team!</p>" + "<p>Best regards,</p>"
					+ "<p> <b> Vrnda Software Technologies Pvt Ltd  </b> </p>";

			mailDto.setBody(content);
			mailDto.setRecipients(recipients);
			isMailSent = mailUtilityService.sendGenericEmial(mailDto);
		} catch (Exception e) {
			log.error("sentEmailForGeneratedPassword error: {}  " + e.getMessage());
		}
		return isMailSent;
	}

	@Override
	public AppUsersMstResultSet saveOrUpdateAppUser(AppUsersMstDTO appUsersMstDTO, String loggedInUser) {
		AppUsersMstResultSet aumResultSet = new AppUsersMstResultSet();
		AppUsersMstEntity aumEntity = null;
		try {
			if (appUsersMstDTO.getUserId() != null) {
				aumEntity = appUserMstRepository.getAppUserByUserId(appUsersMstDTO.getUserId());
				BaseUtils.modifyBaseData(aumEntity, loggedInUser);
			} else {
				if (appUsersMstDTO.getEmployeeId() != null) {
					AppUsersMstEntity checkDuplicateUser = appUserMstRepository
							.getAppUserByUserName(appUsersMstDTO.getUsername());
					if (checkDuplicateUser != null) {
						aumResultSet.setStatus(false);
						aumResultSet.setMessage(ApplicationConstants.FAILED);
						aumResultSet.setMessageDescription("User Exits with same Email Id");
						return aumResultSet;
					} else {
						aumEntity = new AppUsersMstEntity();
						EmployeeDtlsResultSet employeeDtlsResultSet = employeeDetailsService
								.getEmployeeDtlsById(appUsersMstDTO.getEmployeeId());
						EmployeeDetailsEntity edEntity = employeeDtlsResultSet.getEmployeeDetailsEntity();
						if (edEntity != null) {
							aumEntity.setUserId(cmnTableSeqService.getNextSequence(ApplicationConstants.APP_USERS_MST,
									ApplicationConstants.USER_ID)+1);
							aumEntity.setUsername(appUsersMstDTO.getUsername());
							aumEntity.setEmployeeId(appUsersMstDTO.getEmployeeId());
							aumEntity.setFirstLogin('Y');
							aumEntity.setSystemConfigFlag("N");
							aumEntity.setAlwaysLogin('N');
							PasswordGenerator passwordGenerator = PasswordGenerator.getInstance();
							String randomPassword = passwordGenerator.getGeneratePasswordWithSpecailChar();
							sentEmailForGeneratedPassword(appUsersMstDTO.getUsername(), randomPassword);
							aumEntity.setPassword(passwordGenerator.getSHA512(randomPassword));
//							aumEntity.setPasswordChangedDate(new Date());
							BaseUtils.setBaseData(aumEntity, loggedInUser);
						}
					}

				} else {
					aumResultSet.setStatus(false);
					aumResultSet.setMessage(ApplicationConstants.FAILED);
					aumResultSet.setMessageDescription("Invalid Employee Id");
				}
			}
			if (aumEntity != null) {
				aumEntity.setActivateFlag(appUsersMstDTO.getActivateFlag());
				aumEntity.setStatusLookupId(aumEntity.getActivateFlag());
//				aumEntity.setAlwaysLogin(appUsersMstDTO.getAlwaysLogin());
				aumEntity.setAppRoleId(appUsersMstDTO.getAppRoleId());
				aumEntity.setInvalidLoginCount(appUsersMstDTO.getInvalidLoginCount());
				save(aumEntity);
			} else {
				aumResultSet.setStatus(false);
				aumResultSet.setMessage(ApplicationConstants.FAILED);
				aumResultSet.setMessageDescription("Unable to create user.");
			}
		} catch (Exception e) {
			aumResultSet.setStatus(false);
			aumResultSet.setMessage(ApplicationConstants.EXCEPTION);
			aumResultSet.setMessageDescription(e.getMessage());
		}
		return aumResultSet;
	}

	@Override
	public AppUsersMstResultSet deleteAppUserByAppUserId(Long appUserId) {
		AppUsersMstResultSet aumResultSet = new AppUsersMstResultSet();
		try {
			AppUsersMstEntity aumEntity = appUserMstRepository.getAppUserByUserId(appUserId);
			if (aumEntity != null) {
				delete(aumEntity);
			} else {
				aumResultSet.setStatus(false);
				aumResultSet.setMessage(ApplicationConstants.FAILED);
				aumResultSet.setMessageDescription("User does not exist");
			}
		} catch (Exception e) {
			aumResultSet.setStatus(false);
			aumResultSet.setMessage(ApplicationConstants.EXCEPTION);
			aumResultSet.setMessageDescription(e.getMessage());
		}
		return aumResultSet;
	}

	@Override
	public AppUsersMstResultSet getAllAppUsers() {
		AppUsersMstResultSet aumResultSet = new AppUsersMstResultSet();
		List<AppUsersMstDTO> appUserMstDtoList = null;
		try {
			appUserMstDtoList = appUserMstRepository.getAllAppUsers();
			if (appUserMstDtoList != null) {
				for (AppUsersMstDTO appUsersMstDto : appUserMstDtoList) {
					if (appUsersMstDto.getEmployeeId() != null) {
						EmployeeDtlsResultSet employeeDtlsResultSet = employeeDetailsService
								.getEmployeeDtlsById(appUsersMstDto.getEmployeeId());
						EmployeeDetailsEntity employeeDetails = employeeDtlsResultSet.getEmployeeDetailsEntity();
						appUsersMstDto.setEmployeeFullName(
								employeeDetails.getEmployeeFirstName() + " " + employeeDetails.getEmployeeLastName());
					}
					if (appUsersMstDto.getAppRoleId() != null) {
						appUsersMstDto.setAppRoleName(
								cmnAppRoleMstService.getAppRoleByAppRoleId(appUsersMstDto.getAppRoleId())
										.getCmnAppRolesMstEntity().getAppRoleName());
					}
				}
				aumResultSet.setAppUsersMstDtoList(appUserMstDtoList);
			} else {
				aumResultSet.setStatus(false);
				aumResultSet.setMessage(ApplicationConstants.FAILED);
				aumResultSet.setMessageDescription("User does not exist");
			}
		} catch (Exception e) {
			aumResultSet.setStatus(false);
			aumResultSet.setMessage(ApplicationConstants.EXCEPTION);
			aumResultSet.setMessageDescription(e.getMessage());
		}
		return aumResultSet;
	}

	@Override
	public AppUsersMstResultSet getAppUsersByStatusLookupId(Long statusLookupId) {
		AppUsersMstResultSet aumResultSet = new AppUsersMstResultSet();
		List<AppUsersMstDTO> appUserMstDtoList = null;
		try {
			appUserMstDtoList = appUserMstRepository.getAppUsersByStatusLookupId(statusLookupId);
			if (appUserMstDtoList != null) {
				for (AppUsersMstDTO appUsersMstDto : appUserMstDtoList) {
					if (appUsersMstDto.getEmployeeId() != null) {
						EmployeeDtlsResultSet employeeDtlsResultSet = employeeDetailsService
								.getEmployeeDtlsById(appUsersMstDto.getEmployeeId());
						EmployeeDetailsEntity employeeDetails = employeeDtlsResultSet.getEmployeeDetailsEntity();
						appUsersMstDto.setEmployeeFullName(
								employeeDetails.getEmployeeFirstName() + " " + employeeDetails.getEmployeeLastName());
					}
					if (appUsersMstDto.getAppRoleId() != null) {
						appUsersMstDto.setAppRoleName(
								cmnAppRoleMstService.getAppRoleByAppRoleId(appUsersMstDto.getAppRoleId())
										.getCmnAppRolesMstEntity().getAppRoleName());
					}
				}
				aumResultSet.setAppUsersMstDtoList(appUserMstDtoList);
			} else {
				aumResultSet.setStatus(false);
				aumResultSet.setMessage(ApplicationConstants.FAILED);
				aumResultSet.setMessageDescription("User does not exist");
			}
		} catch (Exception e) {
			aumResultSet.setStatus(false);
			aumResultSet.setMessage(ApplicationConstants.EXCEPTION);
			aumResultSet.setMessageDescription(e.getMessage());
		}
		return aumResultSet;
	}

	@Override
	public AppUsersMstResultSet getAppUserByUserId(Long appUserId) {
		AppUsersMstResultSet aumResultSet = new AppUsersMstResultSet();
		try {
			AppUsersMstEntity aumEntity = appUserMstRepository.getAppUserByUserId(appUserId);
			if (aumEntity != null) {
				aumResultSet.setAppUsersMstEntity(aumEntity);
			} else {
				aumResultSet.setStatus(false);
				aumResultSet.setMessage(ApplicationConstants.FAILED);
				aumResultSet.setMessageDescription("User does not exist");
			}
		} catch (Exception e) {
			aumResultSet.setStatus(false);
			aumResultSet.setMessage(ApplicationConstants.EXCEPTION);
			aumResultSet.setMessageDescription(e.getMessage());
		}
		return aumResultSet;
	}

	@Override
	public AllUserDetailsResultSet getAppUserAllDetails(String username) {
		AllUserDetailsResultSet allUserDetailsRS = new AllUserDetailsResultSet();
		boolean reqStatus = true;
		Map<String, Long> menuTypeMap = new HashMap<String, Long>();
		Map<String, String> rolesMap = new HashMap<String, String>();
		List<Long> roleAccessMenus = new ArrayList<Long>();
		List<String> role = new ArrayList<String>();
		try {
			UIMenusDTO uiMenusDto = new UIMenusDTO();
			AppUsersMstEntity appUserMst = appUserMstRepository.getAppUserByUserName(username);
			role.add("'" + cmnAppRoleMstService.getAppRoleByAppRoleId(appUserMst.getAppRoleId())
					.getCmnAppRolesMstEntity().getAppRoleName() + "'");
			Long activeFlagId = cmnLookupMstService.getLookupIdByLookupNameAndParentLookupName(
					ApplicationConstants.ACTIVE, ApplicationConstants.STATUS);
			menuTypeMap.put(ApplicationConstants.MENU_ITEM,
					cmnLookupMstService.getLookupIdByLookupNameAndParentLookupName(ApplicationConstants.MENU_ITEM,
							ApplicationConstants.MENU_TYPE));
			menuTypeMap.put(ApplicationConstants.SCREEN_ITEM,
					cmnLookupMstService.getLookupIdByLookupNameAndParentLookupName(ApplicationConstants.SCREEN_ITEM,
							ApplicationConstants.MENU_TYPE));
			RoleAccessPrivilegesResultSet roleAccessPrivilegesResultSet = cmnAppRoleAccessPrivilegesService
					.getRoleAccessPrivilegesByRole(appUserMst.getAppRoleId());
			if (roleAccessPrivilegesResultSet != null) {
				List<CmnAppRoleAccessPrivilegesDTO> cmnAppRoleAccessPrivilegesDtoList = roleAccessPrivilegesResultSet
						.getCmnAppRoleAccessPrivilegesDTOList();
				for (CmnAppRoleAccessPrivilegesDTO cmnAppRoleAccessPrivilegesDto : cmnAppRoleAccessPrivilegesDtoList) {
					if (cmnAppRoleAccessPrivilegesDto.getStatusLookupId().longValue() == activeFlagId.longValue())
						roleAccessMenus.add(cmnAppRoleAccessPrivilegesDto.getMenuId());
				}
			}
			if (appUserMst != null && appUserMst.getAppRoleId() != null && appUserMst.getAppRoleId() > 0
					&& appUserMst.getEmployeeId() != null && appUserMst.getEmployeeId() > 0) {
				CmnMenusMstEntity cmnMenusEntity = cmnMenusMstService.getMenuByMenuCode(ApplicationConstants.MAIN)
						.getCmnMenusMstEntity();
				if (cmnMenusEntity != null) {
					BeanUtils.copyProperties(cmnMenusEntity, uiMenusDto);
					uiMenusDto.setRole(role);
					uiMenusDto = getChildren(uiMenusDto, menuTypeMap, roleAccessMenus, role, activeFlagId);
					if (uiMenusDto != null && uiMenusDto.getSubMenusDto()!=null) {
						allUserDetailsRS.setUiMenusDtoList(getFinalMenus(uiMenusDto));
					}
				}
				Long screenTypeId = cmnLookupMstService.getLookupIdByLookupNameAndParentLookupName(
						ApplicationConstants.SCREEN_ITEM, ApplicationConstants.MENU_TYPE);
				List<CmnMenusMstDTO> cmnMenuMstDtoList = cmnMenusMstService.getMenusByMenuTypeId(screenTypeId)
						.getCmnMenusMstDtoList();
				if (cmnMenuMstDtoList != null) {
					for (CmnMenusMstDTO cmnMenusMstDto : cmnMenuMstDtoList) {
						if (roleAccessMenus.contains(cmnMenusMstDto.getMenuId()) && cmnMenusMstDto.getStatusLookupId().equals(activeFlagId)) {
							rolesMap.put(cmnMenusMstDto.getMenuPath(), "full");
						}
					}
					if (rolesMap != null) {
						allUserDetailsRS.setRolesMap(rolesMap);
					}
				}
				EmployeeDetailsEntity employeeDtlsEntity = employeeDetailsService
						.getEmployeeDtlsById(appUserMst.getEmployeeId()).getEmployeeDetailsEntity();
				List<CmnLookupMstDTO> cmnLookupMstDtoList = cmnLookupMstService
						.getLookupIdAndNameListByParentLookupName(ApplicationConstants.STATUS).getCmnLookupMstDtoList();
				if (employeeDtlsEntity != null && cmnLookupMstDtoList != null) {
					StatusLookupIdDTO statusLookupIdDTO = new StatusLookupIdDTO();
					LoggedInUserInformationDTO loggedInUserInformationDTO = new LoggedInUserInformationDTO();
					for (CmnLookupMstDTO cmnLookupMstDto : cmnLookupMstDtoList) {
						if (cmnLookupMstDto.getLookupName().equalsIgnoreCase(ApplicationConstants.ACTIVE)) {
							statusLookupIdDTO.setActiveFlagId(cmnLookupMstDto.getLookupId());
						}
						if (cmnLookupMstDto.getLookupName().equalsIgnoreCase(ApplicationConstants.INACTIVE)) {
							statusLookupIdDTO.setInActiveFlagId(cmnLookupMstDto.getLookupId());
						}
					}
					if (!(statusLookupIdDTO.getActiveFlagId() != null && statusLookupIdDTO.getActiveFlagId() > 0
							&& statusLookupIdDTO.getInActiveFlagId() != null
							&& statusLookupIdDTO.getInActiveFlagId() > 0))
						reqStatus = false;
					AppRolesResultSet appRolesResultSet = cmnAppRoleMstService
							.getAppRoleByAppRoleId(appUserMst.getAppRoleId());
					if (appRolesResultSet != null && appRolesResultSet.getCmnAppRolesMstEntity() != null) {
						String roleName = appRolesResultSet.getCmnAppRolesMstEntity().getAppRoleName();
						if (roleName != null) {
							loggedInUserInformationDTO.setUsername(username);
							loggedInUserInformationDTO.setFirstName(employeeDtlsEntity.getEmployeeFirstName());
							loggedInUserInformationDTO.setLastName(employeeDtlsEntity.getEmployeeLastName());
							loggedInUserInformationDTO.setEmployeeId(employeeDtlsEntity.getEmployeeId());
							loggedInUserInformationDTO.setRoleId(appUserMst.getAppRoleId());
							loggedInUserInformationDTO.setRoleName(roleName);
							loggedInUserInformationDTO.setFullName(employeeDtlsEntity.getEmployeeFirstName() + " "
									+ employeeDtlsEntity.getEmployeeLastName());
							loggedInUserInformationDTO.setUserId(appUserMst.getUserId());
						} else {
							reqStatus = false;
						}
					} else {
						reqStatus = false;
					}
					allUserDetailsRS.setStatusLookupIdDto(statusLookupIdDTO);
					allUserDetailsRS.setLoggedInUserInformationDto(loggedInUserInformationDTO);
				} else {
					reqStatus = false;
				}
			} else {
				reqStatus = false;
			}
			if (!reqStatus) {
				allUserDetailsRS.setStatus(false);
				allUserDetailsRS.setMessage(ApplicationConstants.FAILED);
				allUserDetailsRS.setMessageDescription("Unable to retrieve details");
				return allUserDetailsRS;
			}

		} catch (Exception e) {
			allUserDetailsRS.setStatus(false);
			allUserDetailsRS.setMessage(ApplicationConstants.FAILED);
			allUserDetailsRS.setMessageDescription("Unable to retrieve details");
			log.error("Exception in AppUserMstServiceImpl.getAppUserAllDetails");
		}
		return allUserDetailsRS;
	}

	public UIMenusDTO getChildren(UIMenusDTO menus, Map<String, Long> menuType, List<Long> roleAccessMenus,
			List<String> role, Long activeFlagId) {
		List<UIMenusDTO> menuList = new ArrayList<UIMenusDTO>();
		List<UIMenusDTO> dummy = new ArrayList<UIMenusDTO>();
		List<CmnMenusMstDTO> childMenusList = cmnMenusMstService.getMenusByParentMenuId(menus.getMenuId()).getCmnMenusMstDtoList();
		if (childMenusList != null) {
			for (CmnMenusMstDTO childMenu : childMenusList) {
				if(childMenu.getStatusLookupId().equals(activeFlagId)) {
				UIMenusDTO childUIMenusDTO = new UIMenusDTO();
				BeanUtils.copyProperties(childMenu, childUIMenusDTO);
				childUIMenusDTO.setRole(role);
				childUIMenusDTO.setSubMenusDto(dummy);
				if (childUIMenusDTO.getMenuType().equals(menuType.get(ApplicationConstants.MENU_ITEM))) {
					childUIMenusDTO.setMenuClass("menu-toggle");
					UIMenusDTO returnChild = getChildren(childUIMenusDTO, menuType, roleAccessMenus, role, activeFlagId);
					if (returnChild.getSubMenusDto() != null && returnChild.getSubMenusDto().size() > 0)
						menuList.add(returnChild);
				} else {
					if (roleAccessMenus.contains(childUIMenusDTO.getMenuId())) {
						if (childUIMenusDTO.getMenuPath() != null) {
							childUIMenusDTO.setMenuPath("/" + childUIMenusDTO.getMenuPath());
						}
						childUIMenusDTO.setMenuClass("ml-menu");
						menuList.add(childUIMenusDTO);
					}
				}
			}
			}
		}
		if (menuList != null && menuList.size() > 0) {
			menus.setSubMenusDto(menuList);
		}
		return menus;
	}

	public List<UIMenusDTO> getFinalMenus(UIMenusDTO menus) {
		List<UIMenusDTO> finalMenus = new ArrayList<UIMenusDTO>();
		for (UIMenusDTO subMenu : menus.getSubMenusDto()) {
			finalMenus.add(subMenu);
		}
		return finalMenus;
	}

	public Long getAppUserIdByUserName(String username) {
		return appUserMstRepository.getAppUserIdByUserName(username);
	}

	public void sendUserCreationMail() {
		// TO_DO Email trigger for successful user creation with password
	}

	@Override
	public AppUsersMstResultSet changeUserPassword(AppUsersMstDTO appUsersMstDTO, String loggedInUser) {
		AppUsersMstResultSet appUsersMstResultSet = new AppUsersMstResultSet();
		try {
			AppUsersMstEntity aumEntity = appUserMstRepository.getAppUserByUserId(appUsersMstDTO.getUserId());
			if (aumEntity != null) {
				if (!aumEntity.getPassword().equals(appUsersMstDTO.getCurrentPassword())) {
					appUsersMstResultSet.setStatus(false);
					appUsersMstResultSet.setMessage(ApplicationConstants.FAILED);
					appUsersMstResultSet.setMessageDescription("Invalid current Password");
				} else {
					aumEntity.setPassword(appUsersMstDTO.getNewPassword());
					aumEntity.setPasswordChangedDate(BaseUtils.getCurrentTime());
					BaseUtils.modifyBaseData(aumEntity, loggedInUser);
					save(aumEntity);
					appUsersMstResultSet.setMessageDescription("Password Changed Successfully.");
				}
			}
		} catch (Exception e) {
			appUsersMstResultSet.setStatus(false);
			appUsersMstResultSet.setMessage(ApplicationConstants.FAILED);
			appUsersMstResultSet.setMessageDescription("Unable to Change User Password");
			log.error("Exception in AppUserMstServiceImpl.changeUserPassword");
		}

		return appUsersMstResultSet;
	}

	@Override
	public AppUsersMstResultSet firstTimeLoginChangePass(AppUsersMstDTO appUsersMstDTO) {
		AppUsersMstResultSet appUsersMstResultSet = new AppUsersMstResultSet();
		try {
			AppUsersMstEntity aumEntity = appUserMstRepository.getAppUserByUserName(appUsersMstDTO.getUsername());
			if (aumEntity != null) {
				if (!aumEntity.getPassword().equals(appUsersMstDTO.getCurrentPassword())) {
					appUsersMstResultSet.setStatus(false);
					appUsersMstResultSet.setMessage(ApplicationConstants.FAILED);
					appUsersMstResultSet.setMessageDescription("Invalid current Password");
				} else {
					aumEntity.setPassword(appUsersMstDTO.getNewPassword());
					aumEntity.setPasswordChangedDate(BaseUtils.getCurrentTime());
					aumEntity.setFirstLogin('N');
					BaseUtils.modifyBaseData(aumEntity, appUsersMstDTO.getUsername());
					save(aumEntity);
					appUsersMstResultSet.setMessageDescription("Password Changed Successfully.");
				}
			}
		} catch (Exception e) {
			appUsersMstResultSet.setStatus(false);
			appUsersMstResultSet.setMessage(ApplicationConstants.FAILED);
			appUsersMstResultSet.setMessageDescription("Unable to Change User Password");
			log.error("Exception in AppUserMstServiceImpl.changeUserPassword");
		}

		return appUsersMstResultSet;
	}
}
