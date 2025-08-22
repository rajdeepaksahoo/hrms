package org.vrnda.hrms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.CmnSalaryEarningsConfigDetailsEntity;
import org.vrnda.hrms.repository.CmnSalaryEarningsConfigDetailsRepository;
import org.vrnda.hrms.repository.dto.CmnSalaryEarningsConfigDetailsDTO;
import org.vrnda.hrms.service.CmnSalaryEarningsConfigDetailsService;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.resultset.CmnSalaryEarningsConfigDetailsResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class CmnSalaryEarningsConfigDetailsServiceImpl
		extends GenericServiceImpl<CmnSalaryEarningsConfigDetailsEntity, Long>
		implements CmnSalaryEarningsConfigDetailsService {

	@Autowired
	private CmnSalaryEarningsConfigDetailsRepository cmnSalaryEarningsConfigDetailsRepository;

	@Autowired
	CmnTableSeqService cmnTableSeqService;

	public CmnSalaryEarningsConfigDetailsServiceImpl(
			PagingAndSortingRepository<CmnSalaryEarningsConfigDetailsEntity, Long> typeRepository) {
		super(typeRepository);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CmnSalaryEarningsConfigDetailsResultSet getSalaryEarningsConfigDetailsByConfigId(Long configurationId) {
		CmnSalaryEarningsConfigDetailsResultSet cmnSalaryEarningsConfigDetailsResultSet = new CmnSalaryEarningsConfigDetailsResultSet();
		try {
			if (configurationId != null) {
				List<CmnSalaryEarningsConfigDetailsEntity> CmnSalaryEarningsConfigDetailsEntityList = cmnSalaryEarningsConfigDetailsRepository
						.getSalaryEarningsConfigDetailsByConfigId(configurationId);
				List<CmnSalaryEarningsConfigDetailsDTO> cmnSalaryEarningsConfigDetailsDTOList = new ArrayList<CmnSalaryEarningsConfigDetailsDTO>();
				if (CmnSalaryEarningsConfigDetailsEntityList.size() > 0) {
					CmnSalaryEarningsConfigDetailsEntityList.forEach(objects -> {
						CmnSalaryEarningsConfigDetailsDTO cmnSalaryEarningsConfigDetailsDTO = new CmnSalaryEarningsConfigDetailsDTO();
						BeanUtils.copyProperties(objects, cmnSalaryEarningsConfigDetailsDTO);
						cmnSalaryEarningsConfigDetailsDTOList.add(cmnSalaryEarningsConfigDetailsDTO);
					});
					cmnSalaryEarningsConfigDetailsResultSet
							.setCmnSalaryEarningsConfigDetailsDtoList(cmnSalaryEarningsConfigDetailsDTOList);
				} else {
					cmnSalaryEarningsConfigDetailsResultSet.setStatus(false);
					cmnSalaryEarningsConfigDetailsResultSet.setMessage(ApplicationConstants.ERROR);
					cmnSalaryEarningsConfigDetailsResultSet
							.setMessageDescription("No common Salary Earnings Configurations Details available...");
				}
			} else {
				cmnSalaryEarningsConfigDetailsResultSet.setStatus(false);
				cmnSalaryEarningsConfigDetailsResultSet.setMessage(ApplicationConstants.FAILED);
				cmnSalaryEarningsConfigDetailsResultSet.setMessageDescription("Invalid Inputs");
			}
		} catch (Exception e) {
			cmnSalaryEarningsConfigDetailsResultSet.setStatus(false);
			cmnSalaryEarningsConfigDetailsResultSet.setMessage(ApplicationConstants.ERROR);
			cmnSalaryEarningsConfigDetailsResultSet.setMessageDescription(e.getMessage());
		}
		return cmnSalaryEarningsConfigDetailsResultSet;
	}

	@Override
	public CmnSalaryEarningsConfigDetailsResultSet saveOrUpdateSalaryEarningsConfigDetails(
			List<CmnSalaryEarningsConfigDetailsDTO> cmnSalaryEarningsConfigDetailsDTOList, String loginUser) {
		CmnSalaryEarningsConfigDetailsResultSet cmnSalaryEarningsConfigDetailsResultSet = new CmnSalaryEarningsConfigDetailsResultSet();
		try {
			if (cmnSalaryEarningsConfigDetailsDTOList != null && cmnSalaryEarningsConfigDetailsDTOList.size() > 0) {
				for (CmnSalaryEarningsConfigDetailsDTO cmnSalaryEarningsConfigDetailsDTO : cmnSalaryEarningsConfigDetailsDTOList) {
					if (cmnSalaryEarningsConfigDetailsDTO.getCmnSalaryEarningsConfigDetailsId() == null) {
						CmnSalaryEarningsConfigDetailsEntity cmnSalaryEarningsConfigDetailsEntity = new CmnSalaryEarningsConfigDetailsEntity();
						cmnSalaryEarningsConfigDetailsDTO.setSystemConfigFlag("Y");
						BeanUtils.copyProperties(cmnSalaryEarningsConfigDetailsDTO,
								cmnSalaryEarningsConfigDetailsEntity);
						cmnSalaryEarningsConfigDetailsEntity.setCmnSalaryEarningsConfigDetailsId(cmnTableSeqService
								.getNextSequence(ApplicationConstants.CMN_SALARY_EARNINGS_CONFIG_DETAILS,
										ApplicationConstants.CMN_SALARY_EARNINGS_CONFIG_DETLS_ID));
						BaseUtils.setBaseData(cmnSalaryEarningsConfigDetailsEntity, loginUser);
						save(cmnSalaryEarningsConfigDetailsEntity);
						cmnSalaryEarningsConfigDetailsResultSet.setMessageDescription("Record saved successfully.");
					} else if (cmnSalaryEarningsConfigDetailsDTO != null
							&& cmnSalaryEarningsConfigDetailsDTO.getCmnSalaryEarningsConfigDetailsId() > 0) {
						CmnSalaryEarningsConfigDetailsEntity cmnSalaryEarningsConfigDetailsEntity = cmnSalaryEarningsConfigDetailsRepository
								.getSalaryEarningsConfigDetailsByCmnSalaryEarningsConfigDetailsId(
										cmnSalaryEarningsConfigDetailsDTO.getCmnSalaryEarningsConfigDetailsId());
						if (cmnSalaryEarningsConfigDetailsEntity != null) {
							cmnSalaryEarningsConfigDetailsDTO.setSystemConfigFlag("Y");
							BeanUtils.copyProperties(cmnSalaryEarningsConfigDetailsDTO,
									cmnSalaryEarningsConfigDetailsEntity);
							BaseUtils.modifyBaseData(cmnSalaryEarningsConfigDetailsEntity, loginUser);
							save(cmnSalaryEarningsConfigDetailsEntity);
							cmnSalaryEarningsConfigDetailsResultSet.setMessageDescription("Record saved successfully.");
						}
					} else {
						cmnSalaryEarningsConfigDetailsResultSet.setStatus(false);
						cmnSalaryEarningsConfigDetailsResultSet.setMessage(ApplicationConstants.ERROR);
						cmnSalaryEarningsConfigDetailsResultSet
								.setMessageDescription("Unable to save Earnings Type Configurations");
					}
				}
			}
		} catch (Exception e) {
			cmnSalaryEarningsConfigDetailsResultSet.setStatus(false);
			cmnSalaryEarningsConfigDetailsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			cmnSalaryEarningsConfigDetailsResultSet.setMessageDescription(e.getMessage());
		}
		return cmnSalaryEarningsConfigDetailsResultSet;
	}

	@Override
	public CmnSalaryEarningsConfigDetailsResultSet deleteCmnSalaryEarningsConfigDetailsByConfigId(
			Long configurationId) {
		CmnSalaryEarningsConfigDetailsResultSet cmnSalaryEarningsConfigDetailsResultSet = new CmnSalaryEarningsConfigDetailsResultSet();
		try {
			if (configurationId != null) {
				List<CmnSalaryEarningsConfigDetailsEntity> cmnSalaryEarningsConfigDetailsEntity = cmnSalaryEarningsConfigDetailsRepository
						.getSalaryEarningsConfigDetailsByConfigId(configurationId);
				deleteAll(cmnSalaryEarningsConfigDetailsEntity);
				cmnSalaryEarningsConfigDetailsResultSet
						.setMessageDescription("Commom Salary Configuration Details deleted successfully.");
				cmnSalaryEarningsConfigDetailsResultSet.setMessage("Success");
			} else {
				cmnSalaryEarningsConfigDetailsResultSet.setStatus(false);
				cmnSalaryEarningsConfigDetailsResultSet.setMessage("Failed");
				cmnSalaryEarningsConfigDetailsResultSet.setMessageDescription("Invalid Inputs.");
			}
		} catch (Exception e) {
			cmnSalaryEarningsConfigDetailsResultSet.setStatus(false);
			cmnSalaryEarningsConfigDetailsResultSet.setMessage("Exception");
			cmnSalaryEarningsConfigDetailsResultSet.setMessageDescription(e.getMessage());
		}
		return cmnSalaryEarningsConfigDetailsResultSet;
	}
}
