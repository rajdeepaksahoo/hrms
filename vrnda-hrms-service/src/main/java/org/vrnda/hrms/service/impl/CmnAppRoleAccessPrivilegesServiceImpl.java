package org.vrnda.hrms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.CmnAppRoleAccessPrivilegesCompositeKey;
import org.vrnda.hrms.entity.CmnAppRoleAccessPrivilegesEntity;
import org.vrnda.hrms.entity.CmnMenusMstEntity;
import org.vrnda.hrms.repository.CmnAppRoleAccessPrivilegesRepository;
import org.vrnda.hrms.repository.CmnLookupMstRepository;
import org.vrnda.hrms.repository.dto.CmnAppRoleAccessPrivilegesDTO;
import org.vrnda.hrms.service.CmnAppRoleAccessPrivilegesService;
import org.vrnda.hrms.service.CmnMenusMstService;
import org.vrnda.hrms.service.resultset.CmnMenusMstResultSet;
import org.vrnda.hrms.service.resultset.RoleAccessPrivilegesResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class CmnAppRoleAccessPrivilegesServiceImpl
		extends GenericServiceImpl<CmnAppRoleAccessPrivilegesEntity, CmnAppRoleAccessPrivilegesCompositeKey>
		implements CmnAppRoleAccessPrivilegesService {

	public CmnAppRoleAccessPrivilegesServiceImpl(
			PagingAndSortingRepository<CmnAppRoleAccessPrivilegesEntity, CmnAppRoleAccessPrivilegesCompositeKey> typeRepository) {
		super(typeRepository);
	}

	@Autowired
	CmnAppRoleAccessPrivilegesRepository cmnAppRoleAccessPrivilegesRepository;

	@Autowired
	CmnMenusMstService cmnMenusMstService;

	@Autowired
	CmnLookupMstRepository cmnLookupMstRepository;

	@Override
	public RoleAccessPrivilegesResultSet saveOrUpdateRoleAccessAccessprivileges(
			List<CmnAppRoleAccessPrivilegesDTO> cmnAppRoleAccessPrivilegesDTOList, String loggedInUser) {
		RoleAccessPrivilegesResultSet roleAccessResultSet = new RoleAccessPrivilegesResultSet();
		CmnAppRoleAccessPrivilegesEntity cmnAppRoleAccessPrivilegesEntity = null;
		try {
			if (cmnAppRoleAccessPrivilegesDTOList != null && !cmnAppRoleAccessPrivilegesDTOList.isEmpty()) {
				for (CmnAppRoleAccessPrivilegesDTO cmnAppRoleAccessPrivilegesDTO : cmnAppRoleAccessPrivilegesDTOList) {
					cmnAppRoleAccessPrivilegesEntity = cmnAppRoleAccessPrivilegesRepository
							.getAppRoleAccessPrivilegesByMenuIdAndRoleId(cmnAppRoleAccessPrivilegesDTO.getMenuId(),
									cmnAppRoleAccessPrivilegesDTO.getAppRoleId());
					if (cmnAppRoleAccessPrivilegesEntity != null) {
						cmnAppRoleAccessPrivilegesEntity = new CmnAppRoleAccessPrivilegesEntity();
						BeanUtils.copyProperties(cmnAppRoleAccessPrivilegesDTO, cmnAppRoleAccessPrivilegesEntity);
						BaseUtils.setBaseData(cmnAppRoleAccessPrivilegesEntity, loggedInUser);
					} else {
						cmnAppRoleAccessPrivilegesEntity = new CmnAppRoleAccessPrivilegesEntity();
						BeanUtils.copyProperties(cmnAppRoleAccessPrivilegesDTO, cmnAppRoleAccessPrivilegesEntity);
						BaseUtils.modifyBaseData(cmnAppRoleAccessPrivilegesEntity, loggedInUser);
					}
					save(cmnAppRoleAccessPrivilegesEntity);
				}
			} else {
				roleAccessResultSet.setStatus(false);
				roleAccessResultSet.setMessage("Failed");
				roleAccessResultSet.setMessageDescription("Invalid Inputs");
			}
		} catch (Exception e) {
			roleAccessResultSet.setStatus(false);
			roleAccessResultSet.setMessage("Error");
			roleAccessResultSet.setMessageDescription(e.getMessage());
		}
		return roleAccessResultSet;
	}

	@Override
	public RoleAccessPrivilegesResultSet getRoleAccessPrivilegesByRole(Long roleId) {
		RoleAccessPrivilegesResultSet roleAccessResultSet = new RoleAccessPrivilegesResultSet();
		List<CmnAppRoleAccessPrivilegesEntity> cmnAppRoleAccessPrivilegesEntityList = new ArrayList<CmnAppRoleAccessPrivilegesEntity>();
		List<CmnAppRoleAccessPrivilegesDTO> cmnAppRoleAccessPrivilegesDTOList = new ArrayList<CmnAppRoleAccessPrivilegesDTO>();
		Map<Long, Long> menuAccessMap = null;
		try {
			Long screenMenuType = cmnLookupMstRepository.getLookupIdByLookupNameAndParentLookupName(
					ApplicationConstants.SCREEN_ITEM, ApplicationConstants.MENU_TYPE);
			cmnAppRoleAccessPrivilegesEntityList = cmnAppRoleAccessPrivilegesRepository
					.getRoleAccessPrivilegesByRoleId(roleId);
			if (cmnAppRoleAccessPrivilegesEntityList != null && cmnAppRoleAccessPrivilegesEntityList.size() > 0) {
				menuAccessMap = new HashMap<Long, Long>();
				for (CmnAppRoleAccessPrivilegesEntity cmnAppRoleAccessPrivilegesEntity : cmnAppRoleAccessPrivilegesEntityList) {
					menuAccessMap.put(cmnAppRoleAccessPrivilegesEntity.getMenuId(),
							cmnAppRoleAccessPrivilegesEntity.getStatusLookupId());
				}
			}
			if (cmnAppRoleAccessPrivilegesEntityList != null && !cmnAppRoleAccessPrivilegesEntityList.isEmpty()) {
				for (CmnAppRoleAccessPrivilegesEntity cmnAppRoleAccessPrivilegesEntity : cmnAppRoleAccessPrivilegesEntityList) {
					CmnAppRoleAccessPrivilegesDTO cmnAppRoleAccessPrivilegesDto = new CmnAppRoleAccessPrivilegesDTO();
					CmnMenusMstResultSet CmnMenusMstResultSet = cmnMenusMstService
							.getMenuByMenuIdAndMenuTypeId(cmnAppRoleAccessPrivilegesEntity.getMenuId(), screenMenuType);
					if (CmnMenusMstResultSet != null && CmnMenusMstResultSet.getCmnMenusMstEntity() != null) {
						CmnMenusMstEntity cmnMenusMstEntity = CmnMenusMstResultSet.getCmnMenusMstEntity();
						BeanUtils.copyProperties(cmnAppRoleAccessPrivilegesEntity, cmnAppRoleAccessPrivilegesDto);
						cmnAppRoleAccessPrivilegesDto.setMenuName(cmnMenusMstEntity.getMenuName());
						cmnAppRoleAccessPrivilegesDTOList.add(cmnAppRoleAccessPrivilegesDto);
					}
				}
				roleAccessResultSet.setCmnAppRoleAccessPrivilegesDTOList(cmnAppRoleAccessPrivilegesDTOList);
			} else {
				roleAccessResultSet.setStatus(false);
				roleAccessResultSet.setMessage("Failed");
				roleAccessResultSet.setMessageDescription("Query caused no records");
			}
		} catch (Exception e) {
			roleAccessResultSet.setStatus(false);
			roleAccessResultSet.setMessage("Exception");
			roleAccessResultSet.setMessageDescription(e.getMessage());
			return roleAccessResultSet;
		}
		return roleAccessResultSet;
	}

	@Override
	public RoleAccessPrivilegesResultSet deleteRoleAccessbyRoleId(Long roleId) {
		RoleAccessPrivilegesResultSet roleAccessResultSet = new RoleAccessPrivilegesResultSet();
		List<CmnAppRoleAccessPrivilegesEntity> roleAccPriEntityList = new ArrayList<CmnAppRoleAccessPrivilegesEntity>();
		try {
			roleAccPriEntityList = cmnAppRoleAccessPrivilegesRepository.getRoleAccessPrivilegesByRoleId(roleId);
			if (roleAccPriEntityList != null && !roleAccPriEntityList.isEmpty()) {
				for (CmnAppRoleAccessPrivilegesEntity delRoleAccessPriObj : roleAccPriEntityList) {
					delete(delRoleAccessPriObj);
				}
			}
		} catch (Exception e) {
			roleAccessResultSet.setStatus(false);
			roleAccessResultSet.setMessage("Exception");
			roleAccessResultSet.setMessageDescription(e.getMessage());
			return roleAccessResultSet;
		}
		return roleAccessResultSet;
	}

	@Override
	public RoleAccessPrivilegesResultSet getRoleAccessPrivilegesRoels(Long roleId) {
		RoleAccessPrivilegesResultSet roleAccPriResultSet = new RoleAccessPrivilegesResultSet();
		List<CmnAppRoleAccessPrivilegesDTO> cmnAppRoleAccessDto = new ArrayList<CmnAppRoleAccessPrivilegesDTO>();
		try {
			List<Object[]> resultObject = cmnAppRoleAccessPrivilegesRepository.getRoleAccessPrivilegesByRoles(roleId);
			for (Object[] obj : resultObject) {
				CmnMenusMstEntity cmnMenusMstEntity = (CmnMenusMstEntity) obj[0];
				CmnAppRoleAccessPrivilegesDTO cmnAppRoleAccessPrivilegesDTO = new CmnAppRoleAccessPrivilegesDTO();
				BeanUtils.copyProperties(cmnMenusMstEntity, cmnAppRoleAccessPrivilegesDTO);
				cmnAppRoleAccessPrivilegesDTO.setIsAccess((String) obj[1]);
				cmnAppRoleAccessDto.add(cmnAppRoleAccessPrivilegesDTO);
			}
//			for (CmnAppRoleAccessPrivilegesEntity cmnAppRoleEntity : cmnAppRoleAccessEntityList) {
//				CmnAppRoleAccessPrivilegesDTO cmnAppRoleAccessDtotemp = new CmnAppRoleAccessPrivilegesDTO();
//				BeanUtils.copyProperties(cmnAppRoleEntity, cmnAppRoleAccessDtotemp);
//				cmnAppRoleAccessDto.add(cmnAppRoleAccessDtotemp);
//			}
			roleAccPriResultSet.setCmnAppRoleAccessPrivilegesDTOList(cmnAppRoleAccessDto);
		} catch (Exception e) {
			roleAccPriResultSet.setStatus(false);
			roleAccPriResultSet.setMessage("No Records Found to Display");
			roleAccPriResultSet.setMessageDescription(e.getMessage());
		}
		return roleAccPriResultSet;

	}

	@Override
	public RoleAccessPrivilegesResultSet saveOrUpdateRoleAccessAccessprivilegesChild(
			List<CmnAppRoleAccessPrivilegesDTO> cmnAppRoleAccessPrivilegesDTOList, String loggedInUser) {
		RoleAccessPrivilegesResultSet roleAccessPrivilegesResultset = new RoleAccessPrivilegesResultSet();
		try {
			CmnAppRoleAccessPrivilegesEntity cmnAppRoleAccessPrivilegesEntity = null;
			if (cmnAppRoleAccessPrivilegesDTOList != null) {
				List<CmnAppRoleAccessPrivilegesEntity> insertList = new ArrayList<CmnAppRoleAccessPrivilegesEntity>();
				List<CmnAppRoleAccessPrivilegesCompositeKey> deleteListList = new ArrayList<CmnAppRoleAccessPrivilegesCompositeKey>();
				for (CmnAppRoleAccessPrivilegesDTO cmnAppRoleAccessPrivilegesdto : cmnAppRoleAccessPrivilegesDTOList) {
					if (cmnAppRoleAccessPrivilegesdto.getIsAccess().equals(ApplicationConstants.Y)) {
						cmnAppRoleAccessPrivilegesEntity = new CmnAppRoleAccessPrivilegesEntity();
						BeanUtils.copyProperties(cmnAppRoleAccessPrivilegesdto, cmnAppRoleAccessPrivilegesEntity);
						BaseUtils.setBaseData(cmnAppRoleAccessPrivilegesEntity, loggedInUser);
						cmnAppRoleAccessPrivilegesEntity.setSystemConfigFlag("Y");
						insertList.add(cmnAppRoleAccessPrivilegesEntity);
					} else {
						CmnAppRoleAccessPrivilegesCompositeKey pkKey = new CmnAppRoleAccessPrivilegesCompositeKey();
						pkKey.setAppRoleId(cmnAppRoleAccessPrivilegesdto.getAppRoleId());
						pkKey.setMenuId(cmnAppRoleAccessPrivilegesdto.getMenuId());
						deleteListList.add(pkKey);
					}
				}
				if (!insertList.isEmpty()) {
					saveAll(insertList);
				}
				if (!deleteListList.isEmpty()) {
					deleteListList.forEach(e -> {
						deleteById(e);
					});
				}
				roleAccessPrivilegesResultset.setStatus(true);
				roleAccessPrivilegesResultset.setMessage("success");
				roleAccessPrivilegesResultset.setMessageDescription("Records saved successfully");
			} else {
				roleAccessPrivilegesResultset.setStatus(false);
				roleAccessPrivilegesResultset.setMessage("Fail");
				roleAccessPrivilegesResultset.setMessageDescription("Invalid Inputs");
			}
		} catch (Exception e) {
			roleAccessPrivilegesResultset.setMessageDescription(e.getMessage());
		}
		return roleAccessPrivilegesResultset;
	}
}
