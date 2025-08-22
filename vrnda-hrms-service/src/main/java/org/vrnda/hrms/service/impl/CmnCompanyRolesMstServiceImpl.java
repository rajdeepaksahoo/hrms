package org.vrnda.hrms.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.CmnCompanyRolesMstEntity;
import org.vrnda.hrms.entity.EmployeeDetailsEntity;
import org.vrnda.hrms.repository.CmnCompanyRolesMstRepository;
import org.vrnda.hrms.repository.CmnLookupMstRepository;
import org.vrnda.hrms.repository.EmployeeDetailsRepository;
import org.vrnda.hrms.repository.dto.CmnCompanyRolesMstDTO;
import org.vrnda.hrms.service.CmnCompanyRolesMstService;
import org.vrnda.hrms.service.CmnLookupMstService;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.resultset.CmnCompanyRolesMstResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class CmnCompanyRolesMstServiceImpl extends GenericServiceImpl<CmnCompanyRolesMstEntity, Long>
		implements CmnCompanyRolesMstService {

	@Autowired
	private CmnCompanyRolesMstRepository cmnCompanyRoleMstRepository;

	@Autowired
	CmnTableSeqService cmnTableSeqService;

	@Autowired
	CmnLookupMstRepository cmnLookupMstRepository;

	@Autowired
	CmnLookupMstService cmnLookupMstService;

	@Autowired
	EmployeeDetailsRepository employeeDetailsRepository;

	private Logger logger = LogManager.getLogger(CmnCompanyRolesMstRepository.class.getName());

	public CmnCompanyRolesMstServiceImpl(PagingAndSortingRepository<CmnCompanyRolesMstEntity, Long> typeRepository) {
		super(typeRepository);
	}

	@Override
	public CmnCompanyRolesMstResultSet saveOrUpdateCompanyRole(CmnCompanyRolesMstDTO cmnCompanyRoleMstDto,
			String loggedInUser) {
		CmnCompanyRolesMstResultSet companyRoleResultSet = new CmnCompanyRolesMstResultSet();
		try {
			Long companyRoleTypeLookupId = cmnLookupMstService.getLookupIdByLookupNameAndParentLookupName(
					ApplicationConstants.ADMIN, ApplicationConstants.COMPANY_ROLE_TYPE);
			if (cmnCompanyRoleMstDto != null) {
				if (verifyDuplicateRoleName(cmnCompanyRoleMstDto.getCompanyRoleName(),
						cmnCompanyRoleMstDto.getCompanyRoleId())) {
					companyRoleResultSet.setStatus(false);
					companyRoleResultSet.setMessage("Failed");
					companyRoleResultSet
							.setMessageDescription("Records already exists with same company role.");
					return companyRoleResultSet;
				}
				if (cmnCompanyRoleMstDto.getCompanyRoleId() == 0) {
					CmnCompanyRolesMstEntity cmnCompanyRolesMstEntity = new CmnCompanyRolesMstEntity();
					BeanUtils.copyProperties(cmnCompanyRoleMstDto, cmnCompanyRolesMstEntity);
					cmnCompanyRolesMstEntity.setCompanyRoleId(cmnTableSeqService.getNextSequence(
							ApplicationConstants.CMN_COMPANY_ROLES_MST, ApplicationConstants.COMPANY_ROLE_ID));
					cmnCompanyRolesMstEntity.setCompanyRoleTypeLookupId(companyRoleTypeLookupId);
					BaseUtils.setBaseData(cmnCompanyRolesMstEntity, loggedInUser);
					save(cmnCompanyRolesMstEntity);
					companyRoleResultSet.setMessageDescription("Company role saved successfully.");
				} else if (cmnCompanyRoleMstDto != null && cmnCompanyRoleMstDto.getCompanyRoleId() > 0) {
					CmnCompanyRolesMstEntity cmnCompanyRolesMstEntity = cmnCompanyRoleMstRepository
							.getCompanyRoleByCompanyRoleId(cmnCompanyRoleMstDto.getCompanyRoleId());
					if (cmnCompanyRolesMstEntity != null) {
						BeanUtils.copyProperties(cmnCompanyRoleMstDto, cmnCompanyRolesMstEntity);
						BaseUtils.modifyBaseData(cmnCompanyRolesMstEntity, loggedInUser);
						save(cmnCompanyRolesMstEntity);
						companyRoleResultSet.setMessageDescription("Company role updated successfully.");
					}
				} else {
					companyRoleResultSet.setStatus(false);
					companyRoleResultSet.setMessage("Error");
					companyRoleResultSet.setMessageDescription("Invalid Inputs.");
				}
			}
		} catch (Exception e) {
			companyRoleResultSet.setStatus(false);
			companyRoleResultSet.setMessage("Exception");
			companyRoleResultSet.setMessageDescription("Exception occured while save/update of company role.");
		}
		return companyRoleResultSet;
	}

	@Override
	public CmnCompanyRolesMstResultSet deleteCompanyRoleByRoleId(Long companyRoleId) {
		CmnCompanyRolesMstResultSet cmnCompanyRolesMstResultSet = new CmnCompanyRolesMstResultSet();
		CmnCompanyRolesMstEntity cmnCompanyRolesMstEntity = null;
		List<EmployeeDetailsEntity> employeeDetailsEntity = null;
		try {
			if (companyRoleId != null) {
				cmnCompanyRolesMstEntity = cmnCompanyRoleMstRepository.getCompanyRoleByCompanyRoleId(companyRoleId);
				if (cmnCompanyRolesMstEntity != null) {
					employeeDetailsEntity = employeeDetailsRepository.getEmployeeDetailsBycompanyRoleId(companyRoleId);
					if (employeeDetailsEntity.size() > 0) {
						cmnCompanyRolesMstResultSet.setStatus(false);
						cmnCompanyRolesMstResultSet.setMessage("Failed");
						cmnCompanyRolesMstResultSet
								.setMessageDescription("Company role assigned to a user cannot be deleted.");
					} else {
						delete(cmnCompanyRolesMstEntity);
						cmnCompanyRolesMstResultSet.setMessageDescription("Company role deleted successfully.");
					}
				}
			} else {
				cmnCompanyRolesMstResultSet.setStatus(false);
				cmnCompanyRolesMstResultSet.setMessage("Failed");
				cmnCompanyRolesMstResultSet.setMessageDescription("Invalid Inputs.");
			}
		} catch (Exception e) {
			cmnCompanyRolesMstResultSet.setStatus(false);
			cmnCompanyRolesMstResultSet.setMessage("Exception");
			cmnCompanyRolesMstResultSet.setMessageDescription(e.getMessage());
		}
		return cmnCompanyRolesMstResultSet;
	}

	@Override
	public CmnCompanyRolesMstResultSet deleteCompanyRolesList(List<CmnCompanyRolesMstDTO> cmnCompanyRoleMstDtoList) {
		CmnCompanyRolesMstResultSet cmnCompanyRolesMstResultSet = new CmnCompanyRolesMstResultSet();
		int successCount = 0;
		int failureCount = 0;
		try {
			if (cmnCompanyRoleMstDtoList != null && cmnCompanyRoleMstDtoList.size() > 0) {
				for (CmnCompanyRolesMstDTO cmnCompanyRoleMstDto : cmnCompanyRoleMstDtoList) {
					CmnCompanyRolesMstEntity cmnCompanyRolesMstEntity = cmnCompanyRoleMstRepository
							.getCompanyRoleByCompanyRoleId(cmnCompanyRoleMstDto.getCompanyRoleId());
					if (cmnCompanyRolesMstEntity != null) {
						delete(cmnCompanyRolesMstEntity);
						successCount++;
					} else {
						failureCount++;
					}
				}
				if (failureCount > 0) {
					cmnCompanyRolesMstResultSet.setMessageDescription(
							successCount + " rows deleted and failed to delete " + failureCount + " rows.");
				} else {
					cmnCompanyRolesMstResultSet.setMessageDescription(successCount + " row deleted successfully.");
				}
				cmnCompanyRolesMstResultSet.setSuccessCount(successCount);
				cmnCompanyRolesMstResultSet.setFailureCount(failureCount);
			} else {
				cmnCompanyRolesMstResultSet.setStatus(false);
				cmnCompanyRolesMstResultSet.setMessage("Failed");
				cmnCompanyRolesMstResultSet.setMessageDescription("Invalid Inputs");
			}
		} catch (Exception e) {
			cmnCompanyRolesMstResultSet.setStatus(false);
			cmnCompanyRolesMstResultSet.setMessage("Exception");
			cmnCompanyRolesMstResultSet.setMessageDescription(
					"Excepton occured while deleting the Application Roles. Please contact Administrator");
		}
		return cmnCompanyRolesMstResultSet;
	}

	@Override
	public CmnCompanyRolesMstResultSet getAllCompanyRoles() {
		CmnCompanyRolesMstResultSet companyRoleResultSet = new CmnCompanyRolesMstResultSet();
		List<CmnCompanyRolesMstEntity> cmnEntities = null;
		try {
			cmnEntities = cmnCompanyRoleMstRepository.getAllCompanyRoles();
			if (cmnEntities.size() > 0) {
				companyRoleResultSet.setCmnCompanyRolesMstEntityList(cmnEntities);

			} else {
				companyRoleResultSet.setStatus(false);
				companyRoleResultSet.setMessage("Failed");
				companyRoleResultSet.setMessageDescription("Company role List not available");
			}
		} catch (Exception e) {
			e.printStackTrace();
			companyRoleResultSet.setStatus(false);
			companyRoleResultSet.setMessage("Exception");
			companyRoleResultSet.setMessageDescription(e.getMessage());
		}
		return companyRoleResultSet;
	}
	@Override
	public CmnCompanyRolesMstResultSet getAllCompanyRolesByStatusLookupId(Long statusLookupId) {
		CmnCompanyRolesMstResultSet companyRoleResultSet = new CmnCompanyRolesMstResultSet();
		List<CmnCompanyRolesMstEntity> cmnEntities = null;
		try {
			cmnEntities = cmnCompanyRoleMstRepository.getAllCompanyRolesByStatusLookupId(statusLookupId);
			if (cmnEntities.size() > 0) {
				companyRoleResultSet.setCmnCompanyRolesMstEntityList(cmnEntities);

			} else {
				companyRoleResultSet.setStatus(false);
				companyRoleResultSet.setMessage("Failed");
				companyRoleResultSet.setMessageDescription("Company role List not available");
			}
		} catch (Exception e) {
			e.printStackTrace();
			companyRoleResultSet.setStatus(false);
			companyRoleResultSet.setMessage("Exception");
			companyRoleResultSet.setMessageDescription(e.getMessage());
		}
		return companyRoleResultSet;
	}

	@Override
	public CmnCompanyRolesMstResultSet getCompanyRoleByCompanyRoleId(Long companyRoleId) {
		CmnCompanyRolesMstResultSet cmnCompanyRolesMstResultSet = new CmnCompanyRolesMstResultSet();
		try {
			if (companyRoleId != null) {
				CmnCompanyRolesMstEntity cmnCompanyRolesMstEntity = cmnCompanyRoleMstRepository
						.getCompanyRoleByCompanyRoleId(companyRoleId);
				cmnCompanyRolesMstResultSet.setCmnCompanyRolesMstEntity(cmnCompanyRolesMstEntity);
				cmnCompanyRolesMstResultSet.setMessageDescription("Company role retrieved successfully.");
			} else {
				cmnCompanyRolesMstResultSet.setStatus(false);
				cmnCompanyRolesMstResultSet.setMessage("Failed");
				cmnCompanyRolesMstResultSet.setMessageDescription("Invalid Inputs.");
			}
		} catch (Exception e) {
			cmnCompanyRolesMstResultSet.setStatus(false);
			cmnCompanyRolesMstResultSet.setMessage("Exception");
			cmnCompanyRolesMstResultSet.setMessageDescription(e.getMessage());
		}
		return cmnCompanyRolesMstResultSet;
	}

	@Override
	public CmnCompanyRolesMstResultSet getCompanyRolesByStatusLookupId(Long statusLookupId) {
		CmnCompanyRolesMstResultSet cmnCompanyRolesMstResultSet = new CmnCompanyRolesMstResultSet();
		try {
			if (statusLookupId != null) {
				List<CmnCompanyRolesMstEntity> cmnCompanyRoleMstList = cmnCompanyRoleMstRepository
						.getCompanyRolesByStatusLookupId(statusLookupId);
				cmnCompanyRolesMstResultSet.setCmnCompanyRolesMstEntityList(cmnCompanyRoleMstList);
				cmnCompanyRolesMstResultSet.setMessageDescription("Company role retrieved successfully.");
			} else {
				cmnCompanyRolesMstResultSet.setStatus(false);
				cmnCompanyRolesMstResultSet.setMessage("Failed");
				cmnCompanyRolesMstResultSet.setMessageDescription("Invalid Inputs.");
			}
		} catch (Exception e) {
			cmnCompanyRolesMstResultSet.setStatus(false);
			cmnCompanyRolesMstResultSet.setMessage("Exception");
			cmnCompanyRolesMstResultSet.setMessageDescription(e.getMessage());
		}
		return cmnCompanyRolesMstResultSet;
	}

	@Override
	public boolean verifyDuplicateRoleName(String roleName, Long roleId) {
		if (roleName != null) {
			CmnCompanyRolesMstEntity tempCompanyRole = cmnCompanyRoleMstRepository
					.getCompanyRoleByCompanyRoleName(roleName);
			if (tempCompanyRole != null && roleId != null && roleId == 0)
				return true;
			else if (tempCompanyRole != null && roleId != null && roleId > 0
					&& tempCompanyRole.getCompanyRoleId() != roleId)
				return true;
			else
				return false;
		} else {
			return true;
		}
	}

	@Override
	public CmnCompanyRolesMstResultSet getCompanyRolesByLeadOrManager() {
		CmnCompanyRolesMstResultSet cmnCompanyRolesMstResultSet = new CmnCompanyRolesMstResultSet();
		try {
			List<CmnCompanyRolesMstEntity> cmnCompanyRoleMstList = cmnCompanyRoleMstRepository
					.getCompanyRolesByLeadOrManager();
			cmnCompanyRolesMstResultSet.setCmnCompanyRolesMstEntityList(cmnCompanyRoleMstList);
			cmnCompanyRolesMstResultSet.setMessageDescription("Company role retrieved successfully.");
		} catch (Exception e) {
			cmnCompanyRolesMstResultSet.setStatus(false);
			cmnCompanyRolesMstResultSet.setMessage("Exception");
			cmnCompanyRolesMstResultSet.setMessageDescription(e.getMessage());
		}
		return cmnCompanyRolesMstResultSet;
	}

	@Override
	public CmnCompanyRolesMstResultSet getCompanyRolesByHr() {
		CmnCompanyRolesMstResultSet cmnCompanyRolesMstResultSet = new CmnCompanyRolesMstResultSet();
		try {
			List<CmnCompanyRolesMstEntity> cmnCompanyRoleMstList = cmnCompanyRoleMstRepository.getCompanyRolesByHr();
			cmnCompanyRolesMstResultSet.setCmnCompanyRolesMstEntityList(cmnCompanyRoleMstList);
			cmnCompanyRolesMstResultSet.setMessageDescription("Company role retrieved successfully.");

		} catch (Exception e) {
			cmnCompanyRolesMstResultSet.setStatus(false);
			cmnCompanyRolesMstResultSet.setMessage("Exception");
			cmnCompanyRolesMstResultSet.setMessageDescription(e.getMessage());
		}
		return cmnCompanyRolesMstResultSet;
	}

	@Override
	public CmnCompanyRolesMstResultSet getCompanyRolesByStatusLookupIdAndManager(Long statusLookupId) {
		CmnCompanyRolesMstResultSet cmnCompanyRolesMstResultSet = new CmnCompanyRolesMstResultSet();
		try {
			List<CmnCompanyRolesMstEntity> cmnCompanyRoleMstList = cmnCompanyRoleMstRepository
					.getCompanyRolesByStatusLookupIdAndManager(statusLookupId);
			cmnCompanyRolesMstResultSet.setCmnCompanyRolesMstEntityList(cmnCompanyRoleMstList);
			cmnCompanyRolesMstResultSet.setMessageDescription("Company role retrieved successfully.");

		} catch (Exception e) {
			cmnCompanyRolesMstResultSet.setStatus(false);
			cmnCompanyRolesMstResultSet.setMessage("Exception");
			cmnCompanyRolesMstResultSet.setMessageDescription(e.getMessage());
		}
		return cmnCompanyRolesMstResultSet;
	}

	@Override
	public CmnCompanyRolesMstResultSet getCompanyRolesByStatusLookupIdAndHr(Long statusLookupId) {
		CmnCompanyRolesMstResultSet cmnCompanyRolesMstResultSet = new CmnCompanyRolesMstResultSet();
		try {
			List<CmnCompanyRolesMstEntity> cmnCompanyRoleMstList = cmnCompanyRoleMstRepository
					.getCompanyRolesByStatusLookupIdAndHr(statusLookupId);
			cmnCompanyRolesMstResultSet.setCmnCompanyRolesMstEntityList(cmnCompanyRoleMstList);
			cmnCompanyRolesMstResultSet.setMessageDescription("Company role retrieved successfully.");

		} catch (Exception e) {
			cmnCompanyRolesMstResultSet.setStatus(false);
			cmnCompanyRolesMstResultSet.setMessage("Exception");
			cmnCompanyRolesMstResultSet.setMessageDescription(e.getMessage());
		}
		return cmnCompanyRolesMstResultSet;
	}

	@Override
	public CmnCompanyRolesMstResultSet getCompanyRoleIdByRoleName(String companyRoleName) {
		CmnCompanyRolesMstResultSet cmnCompanyRolesMstResultSet = new CmnCompanyRolesMstResultSet();
		try {
			CmnCompanyRolesMstEntity cmnCompanyRolesMstEntity = cmnCompanyRoleMstRepository
					.getCompanyRoleIdByRoleName(companyRoleName);
			if (cmnCompanyRolesMstEntity != null) {
				CmnCompanyRolesMstDTO cmnCompanyRolesMstDTO = new CmnCompanyRolesMstDTO();
				BeanUtils.copyProperties(cmnCompanyRolesMstEntity, cmnCompanyRolesMstDTO);
				cmnCompanyRolesMstResultSet.setCmnCompanyRolesMsDTO(cmnCompanyRolesMstDTO);
			} else {
				cmnCompanyRolesMstResultSet.setStatus(false);
				cmnCompanyRolesMstResultSet.setMessage(ApplicationConstants.FAILED);
				cmnCompanyRolesMstResultSet.setMessageDescription("Invalid Inputs.");
			}
		} catch (Exception e) {
			cmnCompanyRolesMstResultSet.setStatus(false);
			cmnCompanyRolesMstResultSet.setMessage("Exception");
			cmnCompanyRolesMstResultSet.setMessageDescription(e.getMessage());
			logger.error("Exception occured in CmnCompanyRolesMstRepository.getCompanyRoleIdByRoleName()", e);
		}
		return cmnCompanyRolesMstResultSet;
	}

	@Override
	public CmnCompanyRolesMstResultSet getCompanyRolesByStatusLookupIdAndDepartmentId(Long statusLookupId,
			Long departMentID) {
		CmnCompanyRolesMstResultSet cmnCompanyRolesMstResultSet = new CmnCompanyRolesMstResultSet();
		try {
			if (statusLookupId != null) {
				List<CmnCompanyRolesMstEntity> cmnCompanyRoleMstList = cmnCompanyRoleMstRepository
						.getCompanyRolesByStatusLookupIdAndDepartmentId(statusLookupId,departMentID);
//				List<CmnCompanyRolesMstEntity> cmnCompanyRoleMstList = cmnCompanyRoleMstRepository
//						.getAllCompanyRoles();
				cmnCompanyRolesMstResultSet.setCmnCompanyRolesMstEntityList(cmnCompanyRoleMstList);
				cmnCompanyRolesMstResultSet.setMessageDescription("Company role retrieved successfully.");
			} else {
				cmnCompanyRolesMstResultSet.setStatus(false);
				cmnCompanyRolesMstResultSet.setMessage("Failed");
				cmnCompanyRolesMstResultSet.setMessageDescription("Invalid Inputs.");
			}
		} catch (Exception e) {
			cmnCompanyRolesMstResultSet.setStatus(false);
			cmnCompanyRolesMstResultSet.setMessage("Exception");
			cmnCompanyRolesMstResultSet.setMessageDescription(e.getMessage());
		}
		return cmnCompanyRolesMstResultSet;
	}
}
