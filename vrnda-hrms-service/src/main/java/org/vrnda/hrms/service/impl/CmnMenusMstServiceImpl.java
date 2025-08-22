package org.vrnda.hrms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.CmnAppRoleAccessPrivilegesEntity;
import org.vrnda.hrms.entity.CmnAppRolesMstEntity;
import org.vrnda.hrms.entity.CmnMenusMstEntity;
import org.vrnda.hrms.repository.CmnMenusMstRepository;
import org.vrnda.hrms.repository.dto.CmnMenusMstDTO;
import org.vrnda.hrms.service.CmnAppRoleAccessPrivilegesService;
import org.vrnda.hrms.service.CmnAppRolesMstService;
import org.vrnda.hrms.service.CmnLookupMstService;
import org.vrnda.hrms.service.CmnMenusMstService;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.resultset.AppRolesResultSet;
import org.vrnda.hrms.service.resultset.CmnMenusMstResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class CmnMenusMstServiceImpl extends GenericServiceImpl<CmnMenusMstEntity, Long> implements CmnMenusMstService {

	public CmnMenusMstServiceImpl(PagingAndSortingRepository<CmnMenusMstEntity, Long> typeRepository) {
		super(typeRepository);
	}

	@Autowired
	CmnMenusMstRepository cmnMenusMstRepository;

	@Autowired
	CmnLookupMstService cmnLookupMstService;

	@Autowired
	CmnAppRolesMstService cmnAppRoleMstService;

	@Autowired
	CmnAppRoleAccessPrivilegesService cmnAppRoleAccessPrivilegesService;

	@Autowired
	CmnTableSeqService cmnTableSeqService;

	public CmnMenusMstResultSet saveOrUpdateMenu(CmnMenusMstDTO cmnMenusMstDto, String loggedInUser) {
		CmnMenusMstResultSet cmnMenusMstResultSet = new CmnMenusMstResultSet();
		try {
			if(verifyDuplicateMenuCode(cmnMenusMstDto.getMenuCode(), cmnMenusMstDto.getMenuId()) ) {
				cmnMenusMstResultSet.setStatus(false);
				cmnMenusMstResultSet.setMessage("Failed");
				cmnMenusMstResultSet.setMessageDescription("Record already exists with this Menu Code.");
				return cmnMenusMstResultSet;
			}
			if(verifyDuplicateMenuName(cmnMenusMstDto.getMenuName(), cmnMenusMstDto.getMenuId()) ) {
				cmnMenusMstResultSet.setStatus(false);
				cmnMenusMstResultSet.setMessage("Failed");
				cmnMenusMstResultSet.setMessageDescription("Record already exists with this Menu Name.");
				return cmnMenusMstResultSet;
			}
			if(verifyDuplicateMenuPath(cmnMenusMstDto.getMenuPath(), cmnMenusMstDto.getMenuId()) ) {
				cmnMenusMstResultSet.setStatus(false);
				cmnMenusMstResultSet.setMessage("Failed");
				cmnMenusMstResultSet.setMessageDescription("Record already exists with this Menu Path.");
				return cmnMenusMstResultSet;
			}
			Long inActiveFlagId = cmnLookupMstService.getLookupIdByLookupNameAndParentLookupName(ApplicationConstants.ACTIVE, ApplicationConstants.STATUS);
			Long screenItemId = cmnLookupMstService.getLookupIdByLookupNameAndParentLookupName(ApplicationConstants.SCREEN_ITEM, ApplicationConstants.MENU_TYPE);
			if (cmnMenusMstDto != null && cmnMenusMstDto.getMenuId() == 0) {
				CmnMenusMstEntity menusEntity = new CmnMenusMstEntity();
				BeanUtils.copyProperties(cmnMenusMstDto, menusEntity);
				menusEntity.setMenuId(cmnTableSeqService.getNextSequence(ApplicationConstants.CMN_MENUS_MST, ApplicationConstants.MENU_ID));
				BaseUtils.setBaseData(menusEntity, loggedInUser);
				save(menusEntity);
				if (menusEntity.getMenuType() == screenItemId) {
					AppRolesResultSet appRolesResultSet = cmnAppRoleMstService.getAllAppRoles();
					List<CmnAppRolesMstEntity> cmnAppRolesMstEntityList = appRolesResultSet.getCmnAppRolesMstEntityList();
					if (cmnAppRolesMstEntityList != null && cmnAppRolesMstEntityList.size() != 0) {
						for (CmnAppRolesMstEntity cmnAppRolesMstEntity : cmnAppRolesMstEntityList) {
							CmnAppRoleAccessPrivilegesEntity cmnAppRoleAccessPrivilegesEntity = new CmnAppRoleAccessPrivilegesEntity();
							cmnAppRoleAccessPrivilegesEntity.setAppRoleId(cmnAppRolesMstEntity.getAppRoleId());
							cmnAppRoleAccessPrivilegesEntity.setMenuId(menusEntity.getMenuId());
							cmnAppRoleAccessPrivilegesEntity.setStatusLookupId(inActiveFlagId);
							cmnAppRoleAccessPrivilegesService.save(cmnAppRoleAccessPrivilegesEntity);
						}
					}
				}
			} else if(cmnMenusMstDto != null && cmnMenusMstDto.getMenuId() > 0) {
				CmnMenusMstEntity menusEntity = cmnMenusMstRepository.getCmnMenuMstEntityByMenuId(cmnMenusMstDto.getMenuId());
				BeanUtils.copyProperties(cmnMenusMstDto, menusEntity);
				BaseUtils.modifyBaseData(menusEntity, loggedInUser);
				save(menusEntity);
			} else {
				cmnMenusMstResultSet.setStatus(false);
				cmnMenusMstResultSet.setMessage("Failed");
				cmnMenusMstResultSet.setMessageDescription("Invalid Inputs.");
			}
		} catch (Exception e) {
			cmnMenusMstResultSet.setStatus(false);
			cmnMenusMstResultSet.setMessage(ApplicationConstants.EXCEPTION);
			cmnMenusMstResultSet.setMessageDescription("Exception occured while saving Menu.");
		}
		return cmnMenusMstResultSet;
	}

	@Override
	public CmnMenusMstResultSet getAllMenus() {
		CmnMenusMstResultSet cmnMenusMstResultSet = new CmnMenusMstResultSet();
		List<CmnMenusMstDTO> cmnMenuMstDtoList = new ArrayList<CmnMenusMstDTO>();
		Map<Long,String> menuItemsMap = new HashMap<Long,String>();
		try {
			Map<Long,String> menuTypeMap = cmnLookupMstService.getLookupIdAndLookupNameMapingsByParentLookupName(ApplicationConstants.MENU_TYPE);
			List<CmnMenusMstEntity> menusList = cmnMenusMstRepository.getAllMenus();
			if(menusList != null) {
				for(CmnMenusMstEntity menu : menusList) {
					menuItemsMap.put(menu.getMenuId(), menu.getMenuName());
				}
				for(CmnMenusMstEntity menu : menusList) {
					CmnMenusMstDTO cmnMenusMstDto = new CmnMenusMstDTO();
					BeanUtils.copyProperties(menu, cmnMenusMstDto);
					cmnMenusMstDto.setMenuTypeName(menuTypeMap.get(menu.getMenuType()));
					cmnMenusMstDto.setParentMenuName(menuItemsMap.get(menu.getParentMenuId()));
					cmnMenuMstDtoList.add(cmnMenusMstDto);
				}
				cmnMenusMstResultSet.setCmnMenusMstDtoList(cmnMenuMstDtoList);
			} else {
				cmnMenusMstResultSet.setStatus(false);
				cmnMenusMstResultSet.setMessage("Failed");
				cmnMenusMstResultSet.setMessageDescription("Processing error. Contact Administrator.");
			}
		} catch (Exception e) {
			cmnMenusMstResultSet.setStatus(false);
			cmnMenusMstResultSet.setMessage("No Records Found to Display");
			cmnMenusMstResultSet.setMessageDescription(e.getMessage());
		}
		return cmnMenusMstResultSet;
	}

	@Override
	public CmnMenusMstResultSet getMenusByMenuTypeId(Long menuTypeId) {
		CmnMenusMstResultSet cmnMenusMstResultSet = new CmnMenusMstResultSet();
		List<CmnMenusMstDTO> cmnMenuMstDtoList = new ArrayList<CmnMenusMstDTO>();
		try {
			List<CmnMenusMstEntity> cmnMenuMstEntityList = cmnMenusMstRepository.getMenusByMenuTypeId(menuTypeId);
			for(CmnMenusMstEntity cmnMenusMstEntity : cmnMenuMstEntityList) {
				CmnMenusMstDTO cmnMenusMstDto = new CmnMenusMstDTO();
				BeanUtils.copyProperties(cmnMenusMstEntity, cmnMenusMstDto);
				cmnMenuMstDtoList.add(cmnMenusMstDto);
			}
			cmnMenusMstResultSet.setCmnMenusMstDtoList(cmnMenuMstDtoList);
		} catch (Exception e) {
			cmnMenusMstResultSet.setStatus(false);
			cmnMenusMstResultSet.setMessage("No Records Found to Display");
			cmnMenusMstResultSet.setMessageDescription(e.getMessage());
		}
		return cmnMenusMstResultSet;
	}

	@Override
	public CmnMenusMstResultSet getMenusByParentMenuId(Long parentMenuId) {
		CmnMenusMstResultSet cmnMenusMstResultSet = new CmnMenusMstResultSet();
		List<CmnMenusMstDTO> cmnMenuMstDtoList = new ArrayList<CmnMenusMstDTO>();
		try {
			List<CmnMenusMstEntity> cmnMenuMstEntityList = cmnMenusMstRepository.getMenusByParentMenuId(parentMenuId);
			for(CmnMenusMstEntity cmnMenusMstEntity : cmnMenuMstEntityList) {
				CmnMenusMstDTO cmnMenusMstDto = new CmnMenusMstDTO();
				BeanUtils.copyProperties(cmnMenusMstEntity, cmnMenusMstDto);
				cmnMenuMstDtoList.add(cmnMenusMstDto);
			}
			cmnMenusMstResultSet.setCmnMenusMstDtoList(cmnMenuMstDtoList);
		} catch (Exception e) {
			cmnMenusMstResultSet.setStatus(false);
			cmnMenusMstResultSet.setMessage("No Records Found to Display");
			cmnMenusMstResultSet.setMessageDescription(e.getMessage());
		}
		return cmnMenusMstResultSet;
	}

	@Override
	public CmnMenusMstResultSet getMenuByMenuIdAndMenuTypeId(Long menuId, Long menuTypeId) {
		CmnMenusMstResultSet cmnMenusMstResultSet = new CmnMenusMstResultSet();
		CmnMenusMstEntity cmnMenusMstEntity = cmnMenusMstRepository.getMenuByMenuIdAndMenuTypeId(menuId, menuTypeId);
		if(cmnMenusMstEntity != null) {
			cmnMenusMstResultSet.setCmnMenusMstEntity(cmnMenusMstEntity);
		}
		return cmnMenusMstResultSet;
	}

	@Override
	public CmnMenusMstResultSet getMenuByMenuCode(String menuCode) {
		CmnMenusMstResultSet cmnMenusMstResultSet = new CmnMenusMstResultSet();
		CmnMenusMstEntity cmnMenusMstEntity = cmnMenusMstRepository.getMenuByMenuCode(menuCode);
		if(cmnMenusMstEntity != null) {
			cmnMenusMstResultSet.setCmnMenusMstEntity(cmnMenusMstEntity);
		}
		return cmnMenusMstResultSet;
	}

	@Override
	public boolean verifyDuplicateMenuCode(String menuCode, Long menuId) {
		if(menuCode != null && menuId != null) {
			CmnMenusMstEntity tempCmnMenu = cmnMenusMstRepository.getMenuByMenuCode(menuCode);
			if(tempCmnMenu != null && menuId == 0)
				return true;
			else if(tempCmnMenu != null && menuId > 0 && tempCmnMenu.getMenuId() != menuId.longValue())
				return true;
			else 
				return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean verifyDuplicateMenuName(String menuName, Long menuId) {
		if(menuName != null && menuId != null) {
			CmnMenusMstEntity tempCmnMenu = cmnMenusMstRepository.getMenuByMenuName(menuName);
			if(tempCmnMenu != null && menuId == 0)
				return true;
			else if(tempCmnMenu != null && menuId > 0 && tempCmnMenu.getMenuId() != menuId.longValue())
				return true;
			else 
				return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean verifyDuplicateMenuPath(String menuPath, Long menuId) {
		if(menuPath != null && menuId != null) {
			CmnMenusMstEntity tempCmnMenu = cmnMenusMstRepository.getMenuByMenuPath(menuPath);
			if(tempCmnMenu != null && menuId == 0)
				return true;
			else if(tempCmnMenu != null && menuId > 0 && tempCmnMenu.getMenuId() != menuId.longValue())
				return true;
			else 
				return false;
		} else {
			return true;
		}
	}

}
