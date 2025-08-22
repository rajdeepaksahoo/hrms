package org.vrnda.hrms.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.CmnDesignationsMstEntity;
import org.vrnda.hrms.entity.EmployeeDetailsEntity;
import org.vrnda.hrms.repository.CmnDesignationsMstRepository;
import org.vrnda.hrms.repository.EmployeeDetailsRepository;
import org.vrnda.hrms.repository.dto.CmnDesignationsMstDTO;
import org.vrnda.hrms.service.CmnDesignationsMstService;
import org.vrnda.hrms.service.CmnLookupMstService;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.resultset.CmnDesignationsMstResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class CmnDesignationsMstServiceImpl extends GenericServiceImpl<CmnDesignationsMstEntity, Long>
		implements CmnDesignationsMstService {

	@Autowired
	private CmnDesignationsMstRepository cmnDesignationsMstRepository;

	@Autowired
	CmnTableSeqService cmnTableSeqService;

	@Autowired
	CmnLookupMstService cmnLookupMstService;

	@Autowired
	EmployeeDetailsRepository employeeDetailsRepository;

	public CmnDesignationsMstServiceImpl(PagingAndSortingRepository<CmnDesignationsMstEntity, Long> typeRepository) {
		super(typeRepository);
	}

	@Override
	public CmnDesignationsMstResultSet saveOrUpdateDesignation(CmnDesignationsMstDTO cmnDesignationsMstDto,
			String loggedInUser) {
		CmnDesignationsMstResultSet cmnDesignationMstResultSet = new CmnDesignationsMstResultSet();
		try {
			if (cmnDesignationsMstDto != null) {
				if (verifyDuplicateDesignationName(cmnDesignationsMstDto)) {
					cmnDesignationMstResultSet.setStatus(false);
					cmnDesignationMstResultSet.setMessage("Failed");
					cmnDesignationMstResultSet
							.setMessageDescription("Records already exists with same designation name.");
					return cmnDesignationMstResultSet;
				}
			}
			if (cmnDesignationsMstDto.getDesignationId() == 0) {
				CmnDesignationsMstEntity cmnDesignationsMstEntity = new CmnDesignationsMstEntity();
				BeanUtils.copyProperties(cmnDesignationsMstDto, cmnDesignationsMstEntity);
				cmnDesignationsMstEntity.setDesignationId(cmnTableSeqService.getNextSequence(
						ApplicationConstants.CMN_DESIGNATIONS_MST, ApplicationConstants.DESIGNATION_ID));
				BaseUtils.setBaseData(cmnDesignationsMstEntity, loggedInUser);
				save(cmnDesignationsMstEntity);
				cmnDesignationMstResultSet.setMessageDescription("Designation saved successfully.");
			} else if (cmnDesignationsMstDto != null && cmnDesignationsMstDto.getDesignationId() > 0) {
				CmnDesignationsMstEntity cmnDesignationsMstEntity = cmnDesignationsMstRepository
						.getDesignationByDesignationId(cmnDesignationsMstDto.getDesignationId());
				if (cmnDesignationsMstEntity != null) {
					BeanUtils.copyProperties(cmnDesignationsMstDto, cmnDesignationsMstEntity);
					BaseUtils.modifyBaseData(cmnDesignationsMstEntity, loggedInUser);
					save(cmnDesignationsMstEntity);
					cmnDesignationMstResultSet.setMessageDescription("Designation updated successfully.");
				}
			} else {
				cmnDesignationMstResultSet.setStatus(false);
				cmnDesignationMstResultSet.setMessage("Error");
				cmnDesignationMstResultSet.setMessageDescription("Invalid Inputs.");
			}
		} catch (Exception e) {
			cmnDesignationMstResultSet.setStatus(false);
			cmnDesignationMstResultSet.setMessage("Exception");
			cmnDesignationMstResultSet.setMessageDescription("Exception occured while save/update of designation.");
		}
		return cmnDesignationMstResultSet;
	}

	@Override
	public CmnDesignationsMstResultSet deleteDesignationByDesignationId(Long designationId) {
		CmnDesignationsMstResultSet cmnDesignationMstResultSet = new CmnDesignationsMstResultSet();
		CmnDesignationsMstEntity cmnDesignationsMstEntity = null;
		List<EmployeeDetailsEntity> employeeDetailsEntity = null;
		try {
			if (designationId != null) {
				cmnDesignationsMstEntity = cmnDesignationsMstRepository.getDesignationByDesignationId(designationId);
				if (cmnDesignationsMstEntity != null) {
					employeeDetailsEntity = employeeDetailsRepository.getEmployeeDetailsByDesignationId(designationId);
					if (employeeDetailsEntity.size() > 0) {
						cmnDesignationMstResultSet.setStatus(false);
						cmnDesignationMstResultSet.setMessage("Failed");
						cmnDesignationMstResultSet
								.setMessageDescription("Designation assigned to a user cannot be deleted.");
					} else {
						delete(cmnDesignationsMstEntity);
						cmnDesignationMstResultSet.setMessageDescription("Designation deleted successfully.");
					}
				}
			} else {
				cmnDesignationMstResultSet.setStatus(false);
				cmnDesignationMstResultSet.setMessage("Failed");
				cmnDesignationMstResultSet.setMessageDescription("Invalid details submitted to delete.");
			}
		} catch (Exception e) {
			cmnDesignationMstResultSet.setStatus(false);
			cmnDesignationMstResultSet.setMessage("Exception");
			cmnDesignationMstResultSet.setMessageDescription(e.getMessage());
		}
		return cmnDesignationMstResultSet;
	}

	@Override
	public CmnDesignationsMstResultSet deleteDesignationsList(List<CmnDesignationsMstDTO> cmnDesignationMstDtoList) {
		CmnDesignationsMstResultSet cmnDesignationMstResultSet = new CmnDesignationsMstResultSet();
		int successCount = 0;
		int failureCount = 0;
		try {
			if (cmnDesignationMstDtoList != null && cmnDesignationMstDtoList.size() > 0) {
				for (CmnDesignationsMstDTO cmnDesignationsMstDto : cmnDesignationMstDtoList) {
					CmnDesignationsMstEntity cmnDesignationsMstEntity = cmnDesignationsMstRepository
							.getDesignationByDesignationId(cmnDesignationsMstDto.getDesignationId());
					if (cmnDesignationsMstEntity != null) {
						delete(cmnDesignationsMstEntity);
						successCount++;
					} else {
						failureCount++;
					}
				}
				if (failureCount > 0) {
					cmnDesignationMstResultSet.setMessageDescription(
							successCount + " rows deleted and failed to delete " + failureCount + " rows.");
				} else {
					cmnDesignationMstResultSet.setMessageDescription(successCount + " row deleted successfully.");
				}
				cmnDesignationMstResultSet.setSuccessCount(successCount);
				cmnDesignationMstResultSet.setFailureCount(failureCount);
			} else {
				cmnDesignationMstResultSet.setStatus(false);
				cmnDesignationMstResultSet.setMessage("Failed");
				cmnDesignationMstResultSet.setMessageDescription("Invalid Inputs");
			}
		} catch (Exception e) {
			cmnDesignationMstResultSet.setStatus(false);
			cmnDesignationMstResultSet.setMessage("Exception");
			cmnDesignationMstResultSet.setMessageDescription(
					"Excepton occured while deleting the designation. Please contact Administrator");
		}
		return cmnDesignationMstResultSet;
	}

	@Override
	public CmnDesignationsMstResultSet getAllDesignations() {
		CmnDesignationsMstResultSet obj = new CmnDesignationsMstResultSet();
		try {
			obj.setCmnDesignationsMstEntityList(cmnDesignationsMstRepository.getAllDesignations());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public CmnDesignationsMstResultSet getDesignationByDesignationId(Long designationId) {
		CmnDesignationsMstResultSet cmnDesignationMstResultSet = new CmnDesignationsMstResultSet();
		try {
			CmnDesignationsMstEntity cmnEntity = cmnDesignationsMstRepository
					.getDesignationByDesignationId(designationId);
			if (cmnEntity != null) {
				cmnDesignationMstResultSet.setCmnDesignationsMstEntity(cmnEntity);
			} else {
				cmnDesignationMstResultSet.setStatus(false);
				cmnDesignationMstResultSet.setMessage("Failed");
				cmnDesignationMstResultSet.setMessageDescription("Designation does not exist");
			}
		} catch (Exception e) {
			e.printStackTrace();
			cmnDesignationMstResultSet.setStatus(false);
			cmnDesignationMstResultSet.setMessage("Exception");
			cmnDesignationMstResultSet.setMessageDescription(e.getMessage());
		}
		return cmnDesignationMstResultSet;
	}

	@Override
	public CmnDesignationsMstResultSet getDesignationsByStatusLookupId(Long statusLookupId) {
		CmnDesignationsMstResultSet cmnDesignationMstResultSet = new CmnDesignationsMstResultSet();
		try {
			List<CmnDesignationsMstEntity> cmnEntityList = cmnDesignationsMstRepository
					.getDesignationsByStatusLookupId(statusLookupId);
			if (cmnEntityList.size() > 0 && cmnEntityList != null) {
				cmnDesignationMstResultSet.setCmnDesignationsMstEntityList(cmnEntityList);
			} else {
				cmnDesignationMstResultSet.setStatus(false);
				cmnDesignationMstResultSet.setMessage("Failed");
				cmnDesignationMstResultSet.setMessageDescription("Designation does not exist");
			}
		} catch (Exception e) {
			e.printStackTrace();
			cmnDesignationMstResultSet.setStatus(false);
			cmnDesignationMstResultSet.setMessage("Exception");
			cmnDesignationMstResultSet.setMessageDescription(e.getMessage());
		}
		return cmnDesignationMstResultSet;
	}

	@Override
	public CmnDesignationsMstResultSet getAllDesignationIdsAndDesignationNames() {
		CmnDesignationsMstResultSet cmnDesignationMstResultSet = new CmnDesignationsMstResultSet();
		try {
			cmnDesignationMstResultSet.setCmnDesignationsMstEntityList(
					cmnDesignationsMstRepository.getAllDesignationIdsAndDesignationNames());
		} catch (Exception e) {
			cmnDesignationMstResultSet.setStatus(false);
			cmnDesignationMstResultSet.setMessage("Exception");
			cmnDesignationMstResultSet.setMessageDescription(e.getMessage());
		}
		return cmnDesignationMstResultSet;
	}

	@Override
	public boolean verifyDuplicateDesignationName(CmnDesignationsMstDTO cmnDesignationsMstDto) {
		if (cmnDesignationsMstDto != null) {
			CmnDesignationsMstEntity tempdesig = cmnDesignationsMstRepository
					.getDesignationByDesignationName(cmnDesignationsMstDto.getDesignationName());
			if (tempdesig != null && cmnDesignationsMstDto.getDesignationId() != null
					&& cmnDesignationsMstDto.getDesignationId() == 0)
				return true;
			else if (tempdesig != null && cmnDesignationsMstDto.getDesignationId() != null
					&& cmnDesignationsMstDto.getDesignationId() > 0
					&& tempdesig.getDesignationId() != cmnDesignationsMstDto.getDesignationId())
				return true;
			else
				return false;
		} else {
			return true;
		}
	}

}
