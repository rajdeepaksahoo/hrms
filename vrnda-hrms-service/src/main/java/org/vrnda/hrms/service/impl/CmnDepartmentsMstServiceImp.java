package org.vrnda.hrms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.CmnCompanyRolesMstEntity;
import org.vrnda.hrms.entity.CmnDepartmentsMstEntity;
import org.vrnda.hrms.repository.CmnCompanyRolesMstRepository;
import org.vrnda.hrms.repository.CmnDepartmentsMstRepository;
import org.vrnda.hrms.repository.dto.CmnDepartmentsMstDTO;
import org.vrnda.hrms.service.CmnDepartmentsMstService;
import org.vrnda.hrms.service.CmnLookupMstService;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.resultset.DepartmentsResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class CmnDepartmentsMstServiceImp extends GenericServiceImpl<CmnDepartmentsMstEntity, Long>
		implements CmnDepartmentsMstService {

	public CmnDepartmentsMstServiceImp(PagingAndSortingRepository<CmnDepartmentsMstEntity, Long> typeRepository) {
		super(typeRepository);
	}

	@Autowired
	CmnDepartmentsMstRepository cmnDepartmentMstRepository;

	@Autowired
	CmnTableSeqService cmnTableSeqService;

	@Autowired
	CmnLookupMstService cmnLookupMstService;

	@Autowired
	CmnCompanyRolesMstRepository cmnCompanyRolesMstRepository;

	public DepartmentsResultSet saveOrUpdateDepartments(CmnDepartmentsMstDTO cmnDepartmentsMstDto,
			String loggedInUser) {
		DepartmentsResultSet departmentResultSet = new DepartmentsResultSet();
		try {
			if (cmnDepartmentsMstDto != null) {
				if (verifyDuplicateDepartmentName(cmnDepartmentsMstDto)) {
					departmentResultSet.setStatus(false);
					departmentResultSet.setMessage("Failed");
					departmentResultSet.setMessageDescription("Records already exists with same department name.");
					return departmentResultSet;
				}
				if (cmnDepartmentsMstDto.getDepartmentId() == 0) {
					CmnDepartmentsMstEntity cmnDepartmentsMstEntity = new CmnDepartmentsMstEntity();
					BeanUtils.copyProperties(cmnDepartmentsMstDto, cmnDepartmentsMstEntity);
					cmnDepartmentsMstEntity.setDepartmentId(cmnTableSeqService.getNextSequence(
							ApplicationConstants.CMN_DEPARTMENTS_MST, ApplicationConstants.DEPARTMENT_ID));
					BaseUtils.setBaseData(cmnDepartmentsMstEntity, loggedInUser);
					save(cmnDepartmentsMstEntity);
					departmentResultSet.setMessageDescription("Department saved successfully.");
				} else if (cmnDepartmentsMstDto != null && cmnDepartmentsMstDto.getDepartmentId() > 0) {
					CmnDepartmentsMstEntity cmnDepartmentsMstEntity = cmnDepartmentMstRepository
							.getDepartmentByDepartmentId(cmnDepartmentsMstDto.getDepartmentId());
					if (cmnDepartmentsMstEntity != null) {
						BeanUtils.copyProperties(cmnDepartmentsMstDto, cmnDepartmentsMstEntity);
						BaseUtils.modifyBaseData(cmnDepartmentsMstEntity, loggedInUser);
						save(cmnDepartmentsMstEntity);
						departmentResultSet.setMessageDescription("Department updated successfully.");
					}
				} else {
					departmentResultSet.setStatus(false);
					departmentResultSet.setMessage("Error");
					departmentResultSet.setMessageDescription("Invalid Inputs.");
				}
			}
		} catch (Exception e) {
			departmentResultSet.setStatus(false);
			departmentResultSet.setMessage("Exception");
			departmentResultSet.setMessageDescription("Exception occured while save/update of Department.");
		}
		return departmentResultSet;
	}

	@Override
	public DepartmentsResultSet deleteDepartmentByDepartmentId(Long departmentId) {
		DepartmentsResultSet departmentsResultSet = new DepartmentsResultSet();
		CmnDepartmentsMstEntity departmentEntity = null;
		List<CmnCompanyRolesMstEntity> cmnCompanyRolesMstEntity = null;
		try {
			if (departmentId != null) {
				departmentEntity = cmnDepartmentMstRepository.getDepartmentByDepartmentId(departmentId);
				if (departmentEntity != null) {
					cmnCompanyRolesMstEntity = cmnCompanyRolesMstRepository.getCompanyRoleByDepartmentId(departmentId);
					if (cmnCompanyRolesMstEntity.size() > 0) {
						departmentsResultSet.setStatus(false);
						departmentsResultSet.setMessage("Failed");
						departmentsResultSet.setMessageDescription("Department assigned to a user cannot be deleted.");
					} else {
						delete(departmentEntity);
						departmentsResultSet.setMessageDescription("Department deleted successfully.");
					}
				}
			} else {
				departmentsResultSet.setStatus(false);
				departmentsResultSet.setMessage("Failed");
				departmentsResultSet.setMessageDescription("Invalid details submitted to delete.");
			}
		} catch (Exception e) {
			departmentsResultSet.setStatus(false);
			departmentsResultSet.setMessage("Exception");
			departmentsResultSet.setMessageDescription(e.getMessage());
		}
		return departmentsResultSet;
	}

	@Override
	public DepartmentsResultSet deleteDepartmentsList(List<CmnDepartmentsMstDTO> cmnDepartmentsMstDtoList) {
		DepartmentsResultSet departmentsResultSet = new DepartmentsResultSet();
		int successCount = 0;
		int failureCount = 0;
		List<CmnCompanyRolesMstEntity> cmnCompanyRolesMstEntity = null;
		try {
			if (cmnDepartmentsMstDtoList != null && cmnDepartmentsMstDtoList.size() > 0) {
				for (CmnDepartmentsMstDTO cmnDepartmentsMstDto : cmnDepartmentsMstDtoList) {
					CmnDepartmentsMstEntity cmnDepartmentsMstEntity = cmnDepartmentMstRepository
							.getDepartmentByDepartmentId(cmnDepartmentsMstDto.getDepartmentId());
					if (cmnDepartmentsMstEntity != null) {
						cmnCompanyRolesMstEntity = cmnCompanyRolesMstRepository
								.getCompanyRoleByDepartmentId(cmnDepartmentsMstDto.getDepartmentId());
						if (cmnCompanyRolesMstEntity.size() > 0) {
							departmentsResultSet.setStatus(false);
							departmentsResultSet.setMessage("Failed");
							failureCount++;
							if(failureCount>0) {
								departmentsResultSet
								.setMessageDescription("Department assigned to a user cannot be deleted.");
						}
					} else {
							delete(cmnDepartmentsMstEntity);
							successCount++;
							departmentsResultSet.setMessageDescription(successCount + " row deleted successfully.");
						}

					}
				}
				departmentsResultSet.setSuccessCount(successCount);
				departmentsResultSet.setFailureCount(failureCount);
			} else {
				departmentsResultSet.setStatus(false);
				departmentsResultSet.setMessage("Failed");
				departmentsResultSet.setMessageDescription("Invalid Inputs");
			}
		} catch (Exception e) {
			departmentsResultSet.setStatus(false);
			departmentsResultSet.setMessage("Exception");
			departmentsResultSet.setMessageDescription(
					"Excepton occured while deleting the department. Please contact Administrator");
		}
		return departmentsResultSet;
	}

	public DepartmentsResultSet getAllDepartments() {
		DepartmentsResultSet departmentResultSet = new DepartmentsResultSet();
		try {
			List<CmnDepartmentsMstEntity> deparmentEntity = cmnDepartmentMstRepository.getAllDepartments();
			departmentResultSet.setCmnDepartmentsMstEntityList(deparmentEntity);
		} catch (Exception e) {
			e.printStackTrace();
			departmentResultSet.setStatus(false);
			departmentResultSet.setMessage("Exception");
			departmentResultSet.setMessageDescription(e.getMessage());
		}
		return departmentResultSet;
	}

	public DepartmentsResultSet getDepartmentByDepartmentId(Long departmentId) {
		CmnDepartmentsMstEntity departmentEntity = null;
		DepartmentsResultSet departmentResultSet = new DepartmentsResultSet();
		try {
			departmentEntity = cmnDepartmentMstRepository.getDepartmentByDepartmentId(departmentId);
			if (departmentEntity != null) {
				List<CmnDepartmentsMstEntity> list = new ArrayList<CmnDepartmentsMstEntity>();
				list.add(departmentEntity);
				departmentResultSet.setCmnDepartmentsMstEntityList(list);
			} else {
				departmentResultSet.setStatus(false);
				departmentResultSet.setMessage("Failed");
				departmentResultSet.setMessageDescription("Department id does not exist");
			}
		} catch (Exception e) {
			departmentResultSet.setStatus(false);
			departmentResultSet.setMessage("Exception");
			departmentResultSet.setMessageDescription(e.getMessage());
		}
		return departmentResultSet;
	}

	public DepartmentsResultSet getDepartmentsByStatusLookupId(Long statusLookupId) {
		List<CmnDepartmentsMstEntity> departmentEntity = null;
		DepartmentsResultSet departmentResultSet = new DepartmentsResultSet();
		try {
			departmentEntity = cmnDepartmentMstRepository.getDepartmentByStatusLookupId(statusLookupId);
			if (departmentEntity != null) {
				departmentResultSet.setCmnDepartmentsMstEntityList(departmentEntity);
			} else {

				departmentResultSet.setStatus(false);
				departmentResultSet.setMessage("Failed");
				departmentResultSet.setMessageDescription("Department id does not exist");
			}
		} catch (Exception e) {
			departmentResultSet.setStatus(false);
			departmentResultSet.setMessage("Exception");
			departmentResultSet.setMessageDescription(e.getMessage());
		}
		return departmentResultSet;
	}

	@Override
	public boolean verifyDuplicateDepartmentName(CmnDepartmentsMstDTO cmnDepartmentsMstDto) {
		if (cmnDepartmentsMstDto != null) {
			CmnDepartmentsMstEntity tempdep = cmnDepartmentMstRepository
					.getDepartmentByDepartmentName(cmnDepartmentsMstDto.getDepartmentName());
			if (tempdep != null && cmnDepartmentsMstDto.getDepartmentId() != null
					&& cmnDepartmentsMstDto.getDepartmentId() == 0)
				return true;
			else if (tempdep != null && cmnDepartmentsMstDto.getDepartmentId() != null
					&& cmnDepartmentsMstDto.getDepartmentId() > 0
					&& tempdep.getDepartmentId() != cmnDepartmentsMstDto.getDepartmentId())
				return true;
			else
				return false;
		} else {
			return true;
		}
	}

}
