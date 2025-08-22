package org.vrnda.hrms.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.CmnSalaryAllowanceConfigDetailsEntity;
import org.vrnda.hrms.entity.CmnSalarySlabLevelConfigDetailsEntity;
import org.vrnda.hrms.repository.CmnLoanConfigurationsRepository;
import org.vrnda.hrms.repository.CmnSalaryAllowanceConfigDetailsRepository;
import org.vrnda.hrms.repository.CmnSalarySlabLevelConfigDetailsRepository;
import org.vrnda.hrms.repository.dto.CmnSalarySlabLevelConfigDetailsDTO;
import org.vrnda.hrms.service.CmnLookupMstService;
import org.vrnda.hrms.service.CmnSalarySlabLevelConfigDetailsService;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.resultset.CmnSalarySlabLevelConfigDetailsResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class CmnSalarySlabLevelConfigDetailsServiceImpl
		extends GenericServiceImpl<CmnSalarySlabLevelConfigDetailsEntity, Long>
		implements CmnSalarySlabLevelConfigDetailsService {

	@Autowired
	private CmnSalarySlabLevelConfigDetailsRepository cmnSalarySlabLevelConfigDetailsRepository;

	@Autowired
	CmnTableSeqService cmnTableSeqService;

	@Autowired
	CmnLookupMstService cmnLookupMstService;

	@Autowired
	CmnLoanConfigurationsRepository cmnLoanConfigurationsRepository;

	@Autowired
	CmnSalaryAllowanceConfigDetailsRepository cmnSalaryAllowanceConfigDetailsRepository;

	public CmnSalarySlabLevelConfigDetailsServiceImpl(
			PagingAndSortingRepository<CmnSalarySlabLevelConfigDetailsEntity, Long> typeRepository) {
		super(typeRepository);
	}

	@Override
	public CmnSalarySlabLevelConfigDetailsResultSet getAllsalarySlabDetails(Long configurationId) {
		CmnSalarySlabLevelConfigDetailsResultSet cmnSalarySlabLevelConfigDetailsResultSet = new CmnSalarySlabLevelConfigDetailsResultSet();
		try {
			if (configurationId != null) {
				List<CmnSalarySlabLevelConfigDetailsEntity> cmnSalarySlabLevelList = cmnSalarySlabLevelConfigDetailsRepository
						.getSlabLevelDetailsByConfigurationId(configurationId);
				if (cmnSalarySlabLevelList.size() > 0) {
					cmnSalarySlabLevelConfigDetailsResultSet
							.setCmnSalarySlabLevelConfigDetailsEntityList(cmnSalarySlabLevelList);
				} else {
					cmnSalarySlabLevelConfigDetailsResultSet.setStatus(false);
					cmnSalarySlabLevelConfigDetailsResultSet.setMessage("Failed");
					cmnSalarySlabLevelConfigDetailsResultSet.setMessageDescription("Salary Slab is Empty");
				}
			}
		} catch (Exception e) {
			cmnSalarySlabLevelConfigDetailsResultSet.setStatus(false);
			cmnSalarySlabLevelConfigDetailsResultSet.setMessage("Error");
			cmnSalarySlabLevelConfigDetailsResultSet.setMessageDescription(e.getMessage());
		}
		return cmnSalarySlabLevelConfigDetailsResultSet;

	}

	@Override
	public CmnSalarySlabLevelConfigDetailsResultSet saveOrUpdateSalarySlab(
			CmnSalarySlabLevelConfigDetailsDTO cmnSalarySlabLevelDto, String loggedInUser) {
		CmnSalarySlabLevelConfigDetailsResultSet cmnSalarySlabLevelConfigDetailsResultSet = new CmnSalarySlabLevelConfigDetailsResultSet();
		try {
			if (cmnSalarySlabLevelDto != null) {
				if (verifyDuplicateInSalarySlab(cmnSalarySlabLevelDto)) {
					cmnSalarySlabLevelConfigDetailsResultSet.setStatus(false);
					cmnSalarySlabLevelConfigDetailsResultSet.setMessage("Failed");
					cmnSalarySlabLevelConfigDetailsResultSet
							.setMessageDescription("Salary Slab Level already exists with same configuration.");
					return cmnSalarySlabLevelConfigDetailsResultSet;
				}
				if (cmnSalarySlabLevelDto.getCmnSalarySlabLevelConfigDetlsId() == null || cmnSalarySlabLevelDto.getCmnSalarySlabLevelConfigDetlsId() == 0 ) {
					CmnSalarySlabLevelConfigDetailsEntity salaryslablevelEntity = new CmnSalarySlabLevelConfigDetailsEntity();
					BeanUtils.copyProperties(cmnSalarySlabLevelDto, salaryslablevelEntity);
					salaryslablevelEntity.setCmnSalarySlabLevelConfigDetlsId(cmnTableSeqService.getNextSequence(
							ApplicationConstants.CMN_SALARY_SLAB_LEVEL_CONFIG_DETAILS,
							ApplicationConstants.CMN_SALARY_SLAB_LEVEL_CONFIG_DETLS_ID));
					BaseUtils.setBaseData(salaryslablevelEntity, loggedInUser);
					save(salaryslablevelEntity);
					cmnSalarySlabLevelConfigDetailsResultSet.setMessageDescription("Record saved successfully.");
				} else if (cmnSalarySlabLevelDto != null
						&& cmnSalarySlabLevelDto.getCmnSalarySlabLevelConfigDetlsId() > 0) {
					CmnSalarySlabLevelConfigDetailsEntity salaryslablevelEntity = cmnSalarySlabLevelConfigDetailsRepository
							.getSalarySlabLevelByCmnSalarySlabLevelConfigDetlsId(
									cmnSalarySlabLevelDto.getCmnSalarySlabLevelConfigDetlsId());
					if (salaryslablevelEntity != null) {
						BeanUtils.copyProperties(cmnSalarySlabLevelDto, salaryslablevelEntity);
						BaseUtils.modifyBaseData(salaryslablevelEntity, loggedInUser);
						save(salaryslablevelEntity);
						cmnSalarySlabLevelConfigDetailsResultSet.setMessageDescription("Record updated successfully.");
					}
				} else {
					cmnSalarySlabLevelConfigDetailsResultSet.setStatus(false);
					cmnSalarySlabLevelConfigDetailsResultSet.setMessage("Error");
					cmnSalarySlabLevelConfigDetailsResultSet.setMessageDescription("Invalid Inputs.");
				}
			}

		} catch (Exception e) {
			cmnSalarySlabLevelConfigDetailsResultSet.setStatus(false);
			cmnSalarySlabLevelConfigDetailsResultSet.setMessage("Exception");
			cmnSalarySlabLevelConfigDetailsResultSet.setMessageDescription(e.getMessage());
		}
		return cmnSalarySlabLevelConfigDetailsResultSet;
	}

	public boolean verifyDuplicateInSalarySlab(CmnSalarySlabLevelConfigDetailsDTO cmnSalarySlabLevelDto) {
		if (cmnSalarySlabLevelDto != null) {
			CmnSalarySlabLevelConfigDetailsEntity tempSalaryslab = cmnSalarySlabLevelConfigDetailsRepository
					.getCmnsalarySlabsBycmnsalaryslabdetails(cmnSalarySlabLevelDto.getSlabLevel(),
							cmnSalarySlabLevelDto.getConfigurationId());
			if (tempSalaryslab != null && cmnSalarySlabLevelDto.getCmnSalarySlabLevelConfigDetlsId() != null
					&& cmnSalarySlabLevelDto.getCmnSalarySlabLevelConfigDetlsId() == 0)
				return true;
			else if (tempSalaryslab != null && cmnSalarySlabLevelDto.getCmnSalarySlabLevelConfigDetlsId() != null
					&& cmnSalarySlabLevelDto.getCmnSalarySlabLevelConfigDetlsId() > 0
					&& tempSalaryslab.getCmnSalarySlabLevelConfigDetlsId().longValue() != cmnSalarySlabLevelDto
							.getCmnSalarySlabLevelConfigDetlsId().longValue())
				return true;
			else
				return false;
		} else {
			return true;
		}
	}

	@Override
	public CmnSalarySlabLevelConfigDetailsResultSet deleteSalarySlabBycmnSalarySlabLevelConfigDetlsId(
			Long cmnSalarySlabLevelConfigDetlsId) {
		CmnSalarySlabLevelConfigDetailsResultSet cmnSalarySlabResultSet = new CmnSalarySlabLevelConfigDetailsResultSet();
		try {
			if (cmnSalarySlabLevelConfigDetlsId != null) {
				List<CmnSalaryAllowanceConfigDetailsEntity> cmnSalaryAllowanceConfigDetailsEntity = cmnSalaryAllowanceConfigDetailsRepository
						.getAllowanceLevelDetailsBySalaryLevelId(cmnSalarySlabLevelConfigDetlsId);
				if (cmnSalaryAllowanceConfigDetailsEntity != null) {
					cmnSalaryAllowanceConfigDetailsRepository.deleteAll(cmnSalaryAllowanceConfigDetailsEntity);
				}
				CmnSalarySlabLevelConfigDetailsEntity cmnSalarySlabMstEntity = cmnSalarySlabLevelConfigDetailsRepository
						.getSalarySlabLevelByCmnSalarySlabLevelConfigDetlsId(cmnSalarySlabLevelConfigDetlsId);
				delete(cmnSalarySlabMstEntity);
				cmnSalarySlabResultSet.setMessageDescription("Salary Slab deleted successfully.");
			} else {
				cmnSalarySlabResultSet.setStatus(false);
				cmnSalarySlabResultSet.setMessage("Failed");
				cmnSalarySlabResultSet.setMessageDescription("Invalid Inputs.");
			}
		} catch (Exception e) {
			cmnSalarySlabResultSet.setStatus(false);
			cmnSalarySlabResultSet.setMessage("Exception");
			cmnSalarySlabResultSet.setMessageDescription(e.getMessage());
		}
		return cmnSalarySlabResultSet;
	}

	@Override
	public CmnSalarySlabLevelConfigDetailsResultSet deleteSalarySlabs(
			List<CmnSalarySlabLevelConfigDetailsDTO> cmnSalarySlabLevelDto) {
		CmnSalarySlabLevelConfigDetailsResultSet cmnSalarySlabResultSet = new CmnSalarySlabLevelConfigDetailsResultSet();
		int successCount = 0;
		int failureCount = 0;
		try {
			if (cmnSalarySlabLevelDto != null && cmnSalarySlabLevelDto.size() > 0) {
				for (CmnSalarySlabLevelConfigDetailsDTO salarySlab : cmnSalarySlabLevelDto) {
					List<CmnSalaryAllowanceConfigDetailsEntity> cmnSalaryAllowanceConfigDetailsEntity = cmnSalaryAllowanceConfigDetailsRepository
							.getAllowanceLevelDetailsBySalaryLevelId(salarySlab.getCmnSalarySlabLevelConfigDetlsId());
					if (cmnSalaryAllowanceConfigDetailsEntity != null) {
						cmnSalaryAllowanceConfigDetailsRepository.deleteAll(cmnSalaryAllowanceConfigDetailsEntity);
					}

					CmnSalarySlabLevelConfigDetailsEntity cmnSalarySlabMstEntity = cmnSalarySlabLevelConfigDetailsRepository
							.getSalarySlabLevelByCmnSalarySlabLevelConfigDetlsId(
									salarySlab.getCmnSalarySlabLevelConfigDetlsId());
					if (cmnSalarySlabMstEntity != null) {
						delete(cmnSalarySlabMstEntity);
						successCount++;
					} else {
						failureCount++;
					}
				}
				if (failureCount > 0) {
					cmnSalarySlabResultSet.setMessageDescription(
							successCount + " rows deleted and failed to delete " + failureCount + " rows.");
				} else {
					cmnSalarySlabResultSet.setMessageDescription(successCount + " row deleted successfully.");
				}
				cmnSalarySlabResultSet.setSuccessCount(successCount);
				cmnSalarySlabResultSet.setFailureCount(failureCount);
			} else {
				cmnSalarySlabResultSet.setStatus(false);
				cmnSalarySlabResultSet.setMessage("Failed");
				cmnSalarySlabResultSet.setMessageDescription("Invalid Inputs");
			}
		} catch (Exception e) {
			cmnSalarySlabResultSet.setStatus(false);
			cmnSalarySlabResultSet.setMessage("Exception");
			cmnSalarySlabResultSet.setMessageDescription(
					"Excepton occured while deleting the Salary Slab. Please contact Administrator");
		}
		return cmnSalarySlabResultSet;
	}
}
