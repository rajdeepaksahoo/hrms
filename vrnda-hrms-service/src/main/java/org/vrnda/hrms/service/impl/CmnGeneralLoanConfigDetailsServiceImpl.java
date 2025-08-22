package org.vrnda.hrms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.CmnGeneralLoanConfigDetailsEntity;
import org.vrnda.hrms.entity.CmnLookupMstEntity;
import org.vrnda.hrms.entity.EmployeeAllConfigDetailsEntity;
import org.vrnda.hrms.repository.CmnGeneralLoanConfigDetailsRepository;
import org.vrnda.hrms.repository.CmnLookupMstRepository;
import org.vrnda.hrms.repository.dto.CmnGeneralLoanConfigDetailsDTO;
import org.vrnda.hrms.repository.dto.CmnLookupMstDTO;
import org.vrnda.hrms.service.CmnGeneralLoanConfigDetailsService;
import org.vrnda.hrms.service.CmnLookupMstService;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.resultset.CmnGeneralLoanConfigDetailsResultSet;
import org.vrnda.hrms.service.resultset.CmnLookupMstResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CmnGeneralLoanConfigDetailsServiceImpl extends GenericServiceImpl<CmnGeneralLoanConfigDetailsEntity, Long>
		implements CmnGeneralLoanConfigDetailsService {

	public CmnGeneralLoanConfigDetailsServiceImpl(
			PagingAndSortingRepository<CmnGeneralLoanConfigDetailsEntity, Long> typeRepository) {
		super(typeRepository);
	}

	@Autowired
	CmnTableSeqService cmnTableSeqService;
	@Autowired
	CmnLookupMstService cmnLookupMstService;

	@Autowired
	private CmnLookupMstRepository cmnLookupMstRepository;

	@Autowired
	CmnGeneralLoanConfigDetailsRepository cmnGeneralLoanConfigDetailsRepository;

	@Override
	public CmnGeneralLoanConfigDetailsResultSet saveOrUpdateGeneralLoanConfigDetails(
			CmnGeneralLoanConfigDetailsDTO cmnGeneralLoanConfigDetailsDto, String loggedInUser) {
		CmnGeneralLoanConfigDetailsResultSet cmnGeneralLoanConfigDetailsResultSet = new CmnGeneralLoanConfigDetailsResultSet();
		try {
			if (cmnGeneralLoanConfigDetailsDto != null) {
				if (cmnGeneralLoanConfigDetailsDto.getCmnGenLoanConfigDetlsId() == null
						|| cmnGeneralLoanConfigDetailsDto.getCmnGenLoanConfigDetlsId() == 0) {
					CmnGeneralLoanConfigDetailsEntity cmnGeneralLoanConfigDetailsEntity = new CmnGeneralLoanConfigDetailsEntity();
					BeanUtils.copyProperties(cmnGeneralLoanConfigDetailsDto, cmnGeneralLoanConfigDetailsEntity);
					cmnGeneralLoanConfigDetailsEntity.setCmnGenLoanConfigDetlsId(
							cmnTableSeqService.getNextSequence(ApplicationConstants.CMN_GENERAL_LOAN_CONFIG_DETAILS,
									ApplicationConstants.CMN_GEN_LOAN_CONFIG_DETLS_ID));
					BaseUtils.setBaseData(cmnGeneralLoanConfigDetailsEntity, loggedInUser);
					save(cmnGeneralLoanConfigDetailsEntity);
					cmnGeneralLoanConfigDetailsResultSet.setMessageDescription("Record saved successfully.");
				} else if (cmnGeneralLoanConfigDetailsDto.getCmnGenLoanConfigDetlsId() > 0) {
					CmnGeneralLoanConfigDetailsEntity cmnGeneralLoanConfigDetailsEntity = cmnGeneralLoanConfigDetailsRepository
							.getGeneralLoanConfigDetailsByCmnGenLoanConfigDetlsId(
									cmnGeneralLoanConfigDetailsDto.getCmnGenLoanConfigDetlsId());
					if (cmnGeneralLoanConfigDetailsEntity != null) {
						BeanUtils.copyProperties(cmnGeneralLoanConfigDetailsDto, cmnGeneralLoanConfigDetailsEntity);
						BaseUtils.modifyBaseData(cmnGeneralLoanConfigDetailsEntity, loggedInUser);
						save(cmnGeneralLoanConfigDetailsEntity);
						cmnGeneralLoanConfigDetailsResultSet.setMessageDescription("Record updated successfully.");
					} else {
						cmnGeneralLoanConfigDetailsResultSet.setStatus(false);
						cmnGeneralLoanConfigDetailsResultSet.setMessage("Failed");
						cmnGeneralLoanConfigDetailsResultSet.setMessageDescription("Invalid Inputs.");
					}
				} else {
					cmnGeneralLoanConfigDetailsResultSet.setStatus(false);
					cmnGeneralLoanConfigDetailsResultSet.setMessage("Failed");
					cmnGeneralLoanConfigDetailsResultSet.setMessageDescription("Invalid Inputs.");
				}
			}
		} catch (Exception e) {
			cmnGeneralLoanConfigDetailsResultSet.setStatus(false);
			cmnGeneralLoanConfigDetailsResultSet.setMessage("Error");
			cmnGeneralLoanConfigDetailsResultSet
					.setMessageDescription("Exception occured while save/update of Common Loan Config Details.");
		}
		return cmnGeneralLoanConfigDetailsResultSet;
	}

	@Override
	public CmnGeneralLoanConfigDetailsResultSet deleteGeneralLoanConfigDetailsByCmnGenLoanConfigDetlsId(
			Long cmnGenLoanConfigDetlsId) {
		CmnGeneralLoanConfigDetailsResultSet cmnGeneralLoanConfigDetailsResultSet = new CmnGeneralLoanConfigDetailsResultSet();
		try {
			if (cmnGenLoanConfigDetlsId != null) {
				CmnGeneralLoanConfigDetailsEntity cmnEntity = cmnGeneralLoanConfigDetailsRepository
						.getGeneralLoanConfigDetailsByCmnGenLoanConfigDetlsId(cmnGenLoanConfigDetlsId);
				if (cmnEntity != null) {
					delete(cmnEntity);
					cmnGeneralLoanConfigDetailsResultSet.setMessageDescription("Record deleted successfully");
				} else {
					cmnGeneralLoanConfigDetailsResultSet.setStatus(false);
					cmnGeneralLoanConfigDetailsResultSet.setMessage("Failed");
					cmnGeneralLoanConfigDetailsResultSet
							.setMessageDescription("Loan configuration not available with the given data");
				}
			} else {
				cmnGeneralLoanConfigDetailsResultSet.setStatus(false);
				cmnGeneralLoanConfigDetailsResultSet.setMessage("Failed");
				cmnGeneralLoanConfigDetailsResultSet.setMessageDescription("Invalid Inputs");
			}
		} catch (Exception e) {
			e.printStackTrace();
			cmnGeneralLoanConfigDetailsResultSet.setStatus(false);
			cmnGeneralLoanConfigDetailsResultSet.setMessage("Error");
			cmnGeneralLoanConfigDetailsResultSet.setMessageDescription("Exception occured while deleting the record.");
		}
		return cmnGeneralLoanConfigDetailsResultSet;
	}

	@Override
	public CmnGeneralLoanConfigDetailsResultSet deleteGeneralLoanConfigDetailsList(
			List<CmnGeneralLoanConfigDetailsDTO> cmnGeneralLoanConfigDetailsDtoList) {
		CmnGeneralLoanConfigDetailsResultSet cmnGeneralLoanConfigDetailsResultSet = new CmnGeneralLoanConfigDetailsResultSet();
		int successCount = 0;
		int failureCount = 0;
		try {
			if (cmnGeneralLoanConfigDetailsDtoList != null && cmnGeneralLoanConfigDetailsDtoList.size() > 0) {
				for (CmnGeneralLoanConfigDetailsDTO cmnGeneralLoanConfigDetailsDto : cmnGeneralLoanConfigDetailsDtoList) {
					CmnGeneralLoanConfigDetailsEntity cmnGeneralLoanConfigDetailsEntity = cmnGeneralLoanConfigDetailsRepository
							.getGeneralLoanConfigDetailsByCmnGenLoanConfigDetlsId(
									cmnGeneralLoanConfigDetailsDto.getCmnGenLoanConfigDetlsId());
					if (cmnGeneralLoanConfigDetailsEntity != null) {
						delete(cmnGeneralLoanConfigDetailsEntity);
						successCount++;
					} else {
						failureCount++;
					}
				}
				if (failureCount > 0) {
					cmnGeneralLoanConfigDetailsResultSet.setMessageDescription(
							successCount + " rows deleted and failed to delete " + failureCount + " rows.");
				} else {
					cmnGeneralLoanConfigDetailsResultSet
							.setMessageDescription(successCount + " row deleted successfully.");
				}
			} else {
				cmnGeneralLoanConfigDetailsResultSet.setStatus(false);
				cmnGeneralLoanConfigDetailsResultSet.setMessage("Failed");
				cmnGeneralLoanConfigDetailsResultSet.setMessageDescription("Invalid Inputs");
			}
		} catch (Exception e) {
			e.printStackTrace();
			cmnGeneralLoanConfigDetailsResultSet.setStatus(false);
			cmnGeneralLoanConfigDetailsResultSet.setMessage("Error");
			cmnGeneralLoanConfigDetailsResultSet.setMessageDescription("Exception occured while deleting the record.");
		}
		return cmnGeneralLoanConfigDetailsResultSet;
	}

	@Override
	public CmnGeneralLoanConfigDetailsResultSet getGeneralLoanConfigDetailsByConfigurationId(Long configurationId) {
		CmnGeneralLoanConfigDetailsResultSet cmnGeneralLoanConfigDetailsResultSet = new CmnGeneralLoanConfigDetailsResultSet();
		try {
			if (configurationId != null) {
				List<CmnGeneralLoanConfigDetailsEntity> cmnEntities = cmnGeneralLoanConfigDetailsRepository
						.getGeneralLoanConfigDetailsByConfigurationId(configurationId);
				if (cmnEntities.size() > 0) {
					cmnGeneralLoanConfigDetailsResultSet.setCmnGeneralLoanConfigDetailsEntityList(cmnEntities);
				} else {
					cmnGeneralLoanConfigDetailsResultSet.setStatus(false);
					cmnGeneralLoanConfigDetailsResultSet.setMessage("Failed");
					cmnGeneralLoanConfigDetailsResultSet
							.setMessageDescription("Loan configuration not available with the given data");
				}
			} else {
				cmnGeneralLoanConfigDetailsResultSet.setStatus(false);
				cmnGeneralLoanConfigDetailsResultSet.setMessage("Failed");
				cmnGeneralLoanConfigDetailsResultSet.setMessageDescription("Invalid Inputs");
			}
		} catch (Exception e) {
			e.printStackTrace();
			cmnGeneralLoanConfigDetailsResultSet.setStatus(false);
			cmnGeneralLoanConfigDetailsResultSet.setMessage("Error");
			cmnGeneralLoanConfigDetailsResultSet
					.setMessageDescription("Exception occured while retrieving the record.");
		}
		return cmnGeneralLoanConfigDetailsResultSet;
	}

	@Override
	public CmnGeneralLoanConfigDetailsResultSet getGeneralLoanConfigDetailsByCmnGenLoanConfigDetlsId(
			Long cmnGenLoanConfigDetlsId) {
		CmnGeneralLoanConfigDetailsResultSet cmnGeneralLoanConfigDetailsResultSet = new CmnGeneralLoanConfigDetailsResultSet();
		try {
			if (cmnGenLoanConfigDetlsId != null) {
				CmnGeneralLoanConfigDetailsEntity cmnEntity = cmnGeneralLoanConfigDetailsRepository
						.getGeneralLoanConfigDetailsByCmnGenLoanConfigDetlsId(cmnGenLoanConfigDetlsId);
				if (cmnEntity != null) {
					cmnGeneralLoanConfigDetailsResultSet.setCmnGeneralLoanConfigDetailsEntity(cmnEntity);
					cmnGeneralLoanConfigDetailsResultSet.setMessage("Success");
					cmnGeneralLoanConfigDetailsResultSet.setMessageDescription("Record retrieved successfully");
				} else {
					cmnGeneralLoanConfigDetailsResultSet.setStatus(false);
					cmnGeneralLoanConfigDetailsResultSet.setMessage("Failed");
					cmnGeneralLoanConfigDetailsResultSet
							.setMessageDescription("Loan configuration not available with the given data");
				}
			} else {
				cmnGeneralLoanConfigDetailsResultSet.setStatus(false);
				cmnGeneralLoanConfigDetailsResultSet.setMessage("Failed");
				cmnGeneralLoanConfigDetailsResultSet.setMessageDescription("Invalid Inputs");
			}
		} catch (Exception e) {
			e.printStackTrace();
			cmnGeneralLoanConfigDetailsResultSet.setStatus(false);
			cmnGeneralLoanConfigDetailsResultSet.setMessage("Error");
			cmnGeneralLoanConfigDetailsResultSet.setMessageDescription(e.getMessage());
		}
		return cmnGeneralLoanConfigDetailsResultSet;
	}

	@Override
	public CmnGeneralLoanConfigDetailsResultSet getGeneralLoanConfigDetailsByConfigurationIdAndStatusLookupId(
			Long configurationId, Long statusLookupId) {
		CmnGeneralLoanConfigDetailsResultSet cmnGeneralLoanConfigDetailsResultSet = new CmnGeneralLoanConfigDetailsResultSet();
		try {
			if (configurationId != null) {
				List<CmnGeneralLoanConfigDetailsEntity> cmnEntities = cmnGeneralLoanConfigDetailsRepository
						.getGeneralLoanConfigDetailsByConfigurationIdAndStatusLookupId(configurationId, statusLookupId);
				if (cmnEntities.size() > 0) {
					cmnGeneralLoanConfigDetailsResultSet.setCmnGeneralLoanConfigDetailsEntityList(cmnEntities);
				} else {
					cmnGeneralLoanConfigDetailsResultSet.setStatus(false);
					cmnGeneralLoanConfigDetailsResultSet.setMessage("Failed");
					cmnGeneralLoanConfigDetailsResultSet.setMessageDescription("Loan config List not available");
				}
			} else {
				cmnGeneralLoanConfigDetailsResultSet.setStatus(false);
				cmnGeneralLoanConfigDetailsResultSet.setMessage("Failed");
				cmnGeneralLoanConfigDetailsResultSet.setMessageDescription("Invalid Inputs");
			}
		} catch (Exception e) {
			e.printStackTrace();
			cmnGeneralLoanConfigDetailsResultSet.setStatus(false);
			cmnGeneralLoanConfigDetailsResultSet.setMessage("Exception");
			cmnGeneralLoanConfigDetailsResultSet.setMessageDescription(e.getMessage());
		}
		return cmnGeneralLoanConfigDetailsResultSet;
	}

	@Override
	public CmnGeneralLoanConfigDetailsResultSet getEmployeeLoanConfigDetails(Long employeeId, String year) {
		CmnGeneralLoanConfigDetailsResultSet cmnGeneralLoanConfigDetailsResultSet = new CmnGeneralLoanConfigDetailsResultSet();
		Long loanConfigId = null;
		Long defaultLoanConfigId = null;

		try {
			if (employeeId != null) {
				EmployeeAllConfigDetailsEntity cmnEntities = cmnGeneralLoanConfigDetailsRepository
						.getEmployeeLoanConfigDetails(employeeId, year);
				if (cmnEntities != null && cmnEntities.getAllConfigs() != null) {
					String allConfigsString = new String(
							cmnEntities.getAllConfigs().getBytes(1, (int) cmnEntities.getAllConfigs().length()));
					HashMap<String, Object> workFlowMap = new ObjectMapper().readValue(allConfigsString, HashMap.class);
					if (workFlowMap.containsKey(ApplicationConstants.LOAN_CONFIG)) {
						loanConfigId = Long.valueOf(workFlowMap.get(ApplicationConstants.LOAN_CONFIG).toString());
					} else {
						loanConfigId = defaultLoanConfigId;
					}
				} else {
					loanConfigId = defaultLoanConfigId;
				}

				if (loanConfigId != null) {
					List<CmnGeneralLoanConfigDetailsEntity> cmnLoanConfigDetailsList = cmnGeneralLoanConfigDetailsRepository
							.getLoanConfigDetailsByConfigurationId(loanConfigId);
					if (cmnLoanConfigDetailsList != null && cmnLoanConfigDetailsList.size() > 0) {
						cmnGeneralLoanConfigDetailsResultSet
								.setCmnGeneralLoanConfigDetailsEntityList(cmnLoanConfigDetailsList);

					}

				}

				// cmnGeneralLoanConfigDetailsResultSet.setCmnGeneralLoanConfigDetailsEntityList(cmnEntities);

			} else {
				cmnGeneralLoanConfigDetailsResultSet.setStatus(false);
				cmnGeneralLoanConfigDetailsResultSet.setMessage("Failed");
				cmnGeneralLoanConfigDetailsResultSet.setMessageDescription("Invalid Inputs");
			}
		} catch (Exception e) {
			e.printStackTrace();
			cmnGeneralLoanConfigDetailsResultSet.setStatus(false);
			cmnGeneralLoanConfigDetailsResultSet.setMessage("Exception");
			cmnGeneralLoanConfigDetailsResultSet.setMessageDescription(e.getMessage());
		}
		return cmnGeneralLoanConfigDetailsResultSet;
	}

	@Override
	public CmnGeneralLoanConfigDetailsResultSet getLoanConfigDetailsByLookUpId() {
		CmnGeneralLoanConfigDetailsResultSet cmnGeneralLoanConfigDetailsResultSet = new CmnGeneralLoanConfigDetailsResultSet();
		String[] array = { ApplicationConstants.LOAN_MIN_SAL_MONTHS, ApplicationConstants.LOAN_MAX_SAL_MONTHS,
				ApplicationConstants.LOAN_FIXED_AMT_LIMIT };
		List<CmnLookupMstDTO> cmnLookupMstDTOList = new ArrayList<CmnLookupMstDTO>();
		for (String lookUpName : array) {
			CmnLookupMstEntity cmnLookUpEntitiy = cmnLookupMstRepository.getLookupByLookupName(lookUpName);
			if (cmnLookUpEntitiy != null) {
				CmnLookupMstDTO cmnLookupMstDTO = new CmnLookupMstDTO();
				BeanUtils.copyProperties(cmnLookUpEntitiy, cmnLookupMstDTO);
				cmnLookupMstDTOList.add(cmnLookupMstDTO);
			}
		}
		cmnGeneralLoanConfigDetailsResultSet.setCmnLookupMstDTO(cmnLookupMstDTOList);
		return cmnGeneralLoanConfigDetailsResultSet;

	}

}
