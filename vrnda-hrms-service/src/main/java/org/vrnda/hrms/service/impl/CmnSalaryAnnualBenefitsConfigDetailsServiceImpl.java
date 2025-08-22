package org.vrnda.hrms.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.CmnLeaveConfigDetailsEntity;
import org.vrnda.hrms.entity.CmnSalaryAnnualBenefitsConfigDetailsEntity;
import org.vrnda.hrms.repository.CmnSalaryAnnualBenefitsLevelRepository;
import org.vrnda.hrms.repository.dto.CmnSalaryAnnualBenefitsConfigDetailsDTO;
import org.vrnda.hrms.service.CmnSalaryAnnualBenefitsConfigDetailsService;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.resultset.CmnLeaveConfigDetailsResultSet;
import org.vrnda.hrms.service.resultset.CmnSalaryAnnualBenefitsConfigDetailResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class CmnSalaryAnnualBenefitsConfigDetailsServiceImpl extends GenericServiceImpl<CmnSalaryAnnualBenefitsConfigDetailsEntity, Long>
implements CmnSalaryAnnualBenefitsConfigDetailsService {
	
	@Autowired
	public CmnSalaryAnnualBenefitsLevelRepository cmnSalaryAnnualBenefitsLevelRepository;
	
	@Autowired
	CmnTableSeqService cmnTableSeqService;
	
	public CmnSalaryAnnualBenefitsConfigDetailsServiceImpl(
			PagingAndSortingRepository<CmnSalaryAnnualBenefitsConfigDetailsEntity, Long> typeRepository) {
		super(typeRepository);
	}

	@Override
	public CmnSalaryAnnualBenefitsConfigDetailResultSet getSalaryAnnualTypeconfigDetails(Long configurationId) {
		CmnSalaryAnnualBenefitsConfigDetailResultSet cmnSalaryAnnualBenefitsConfigDetailResultSet = new CmnSalaryAnnualBenefitsConfigDetailResultSet();
		try {
			if (configurationId != null) {
				List<CmnSalaryAnnualBenefitsConfigDetailsEntity> cmnSalaryAnnualBenefitsList = cmnSalaryAnnualBenefitsLevelRepository
						.getSalaryAnnualTypeconfigDetails(configurationId);
				if (cmnSalaryAnnualBenefitsList.size() > 0) {
					cmnSalaryAnnualBenefitsConfigDetailResultSet.setCmnSalaryAnnualBenefitsConfigDetailsEntity(cmnSalaryAnnualBenefitsList);
				} else {
					cmnSalaryAnnualBenefitsConfigDetailResultSet.setStatus(false);
					cmnSalaryAnnualBenefitsConfigDetailResultSet.setMessage("Failed");
					cmnSalaryAnnualBenefitsConfigDetailResultSet.setMessageDescription("AnnualTypes is Empty");
				}
			} 
		} catch (Exception e) {
			cmnSalaryAnnualBenefitsConfigDetailResultSet.setStatus(false);
			cmnSalaryAnnualBenefitsConfigDetailResultSet.setMessage("Error");
			cmnSalaryAnnualBenefitsConfigDetailResultSet.setMessageDescription(e.getMessage());
		}
		return cmnSalaryAnnualBenefitsConfigDetailResultSet;
	}

	@Override
	public CmnSalaryAnnualBenefitsConfigDetailResultSet saveOrUpdateAnnualTypes(
			List<CmnSalaryAnnualBenefitsConfigDetailsDTO> cmnSalaryAnnualTypesDto, String loggedInUser) {
		CmnSalaryAnnualBenefitsConfigDetailResultSet cmnSalaryAnnualBenefitsConfigDetailResultSet = new CmnSalaryAnnualBenefitsConfigDetailResultSet();
			try {
				if (cmnSalaryAnnualTypesDto != null && cmnSalaryAnnualTypesDto.size() > 0) {
					for (CmnSalaryAnnualBenefitsConfigDetailsDTO cmnAnnualTypesDto : cmnSalaryAnnualTypesDto) {
						if (cmnAnnualTypesDto.getCmnSalaryAnnBenConfigDtlsId() == null) {
							CmnSalaryAnnualBenefitsConfigDetailsEntity salaryannualTypesEntity = new CmnSalaryAnnualBenefitsConfigDetailsEntity();
							BeanUtils.copyProperties(cmnAnnualTypesDto, salaryannualTypesEntity);
							salaryannualTypesEntity.setCmnSalaryAnnBenConfigDtlsId(cmnTableSeqService.getNextSequence(
								ApplicationConstants.CMN_SALARY_ANNUAL_BENEFITS_CONFIG_DETAILS,
								ApplicationConstants.CMN_SALARY_ANN_BEN_CONFIG_DTLS_ID));
							BaseUtils.setBaseData(salaryannualTypesEntity, loggedInUser);
							save(salaryannualTypesEntity);
							cmnSalaryAnnualBenefitsConfigDetailResultSet.setMessageDescription("Record saved successfully.");
						} else if (cmnAnnualTypesDto != null && cmnAnnualTypesDto.getCmnSalaryAnnBenConfigDtlsId() > 0) {
							CmnSalaryAnnualBenefitsConfigDetailsEntity annualEntity = cmnSalaryAnnualBenefitsLevelRepository
									.getCmnSalaryAnnualRetriementBenfitConfigDetlsId(
											cmnAnnualTypesDto.getCmnSalaryAnnBenConfigDtlsId());
							if (cmnAnnualTypesDto != null) {
								BeanUtils.copyProperties(cmnAnnualTypesDto, annualEntity);
								BaseUtils.modifyBaseData(annualEntity, loggedInUser);
								save(annualEntity);
								cmnSalaryAnnualBenefitsConfigDetailResultSet.setMessageDescription("Record saved successfully.");
							}
						}
					}

				}
			} catch (Exception e) {
				cmnSalaryAnnualBenefitsConfigDetailResultSet.setStatus(false);
				cmnSalaryAnnualBenefitsConfigDetailResultSet.setMessage("Exception");
				cmnSalaryAnnualBenefitsConfigDetailResultSet.setMessageDescription(e.getMessage());
			}
			return cmnSalaryAnnualBenefitsConfigDetailResultSet;
		}

	@Override
	public CmnSalaryAnnualBenefitsConfigDetailResultSet deleteAnnualConfigDetailsbyConfigurationId(
			Long configurationId) {
		CmnSalaryAnnualBenefitsConfigDetailResultSet cmnSalaryAnnualBenefitsConfigDetailResultSet = new CmnSalaryAnnualBenefitsConfigDetailResultSet();
		try {
			if(configurationId != null) {
				List<CmnSalaryAnnualBenefitsConfigDetailsEntity> cmnSalaryAnnualBenefitsConfigDetailsEntity = cmnSalaryAnnualBenefitsLevelRepository.getSalaryAnnualTypeconfigDetails(configurationId);
				deleteAll(cmnSalaryAnnualBenefitsConfigDetailsEntity);
				cmnSalaryAnnualBenefitsConfigDetailResultSet.setMessageDescription("Annual Benefits Config deleted successfully.");
				cmnSalaryAnnualBenefitsConfigDetailResultSet.setMessage("Success");
			} else {
				cmnSalaryAnnualBenefitsConfigDetailResultSet.setStatus(false);
				cmnSalaryAnnualBenefitsConfigDetailResultSet.setMessage("Failed");
				cmnSalaryAnnualBenefitsConfigDetailResultSet.setMessageDescription("Invalid Inputs.");
			}
		} catch (Exception e) {
			cmnSalaryAnnualBenefitsConfigDetailResultSet.setStatus(false);
			cmnSalaryAnnualBenefitsConfigDetailResultSet.setMessage("Exception");
			cmnSalaryAnnualBenefitsConfigDetailResultSet.setMessageDescription(e.getMessage());
		}
		return cmnSalaryAnnualBenefitsConfigDetailResultSet;
	}

}
	
