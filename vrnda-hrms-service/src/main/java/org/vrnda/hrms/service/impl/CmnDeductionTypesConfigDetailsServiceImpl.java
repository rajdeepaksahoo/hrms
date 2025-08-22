package org.vrnda.hrms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.CmnDeductionTypeConfigDetailsEntity;
import org.vrnda.hrms.repository.CmnDeductionTypesConfigDetailsRepository;
import org.vrnda.hrms.repository.dto.CmnDeductionTypesConfigDetailsDTO;
import org.vrnda.hrms.service.CmnDeductionTypesConfigDetailsService;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.resultset.CmnDeductionTypesConfigDetailsResultset;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class CmnDeductionTypesConfigDetailsServiceImpl extends
		GenericServiceImpl<CmnDeductionTypeConfigDetailsEntity, Long> implements CmnDeductionTypesConfigDetailsService {

	@Autowired
	CmnDeductionTypesConfigDetailsRepository cmnDeductionTypesConfigDetailsRepository;

	@Autowired
	CmnTableSeqService cmnTableSeqService;

	public CmnDeductionTypesConfigDetailsServiceImpl(
			PagingAndSortingRepository<CmnDeductionTypeConfigDetailsEntity, Long> typeRepository) {
		super(typeRepository);
	}

	@Override
	public CmnDeductionTypesConfigDetailsResultset getDeductionTypesConfigDetailsByConfigurationId(
			Long configurationId) {
		CmnDeductionTypesConfigDetailsResultset cmnDeductionTypesConfigDetailsResultSet = new CmnDeductionTypesConfigDetailsResultset();
		try {
			if (configurationId != null) {
				List<CmnDeductionTypeConfigDetailsEntity> deductiontypesconfigEntitylist = cmnDeductionTypesConfigDetailsRepository
						.getDeductionTypesConfigDetailsByConfigurationId(configurationId);
				List<CmnDeductionTypesConfigDetailsDTO> deductiontypesConfigdtoList = new ArrayList<CmnDeductionTypesConfigDetailsDTO>();
				if (deductiontypesconfigEntitylist.size() > 0) {
					deductiontypesconfigEntitylist.forEach(obj -> {
						CmnDeductionTypesConfigDetailsDTO deductiontypesconfigdto = new CmnDeductionTypesConfigDetailsDTO();
						BeanUtils.copyProperties(obj, deductiontypesconfigdto);
						deductiontypesConfigdtoList.add(deductiontypesconfigdto);
					});
					cmnDeductionTypesConfigDetailsResultSet
							.setDeductiontypeconfigdetailsdtolist(deductiontypesConfigdtoList);
				} else {
					cmnDeductionTypesConfigDetailsResultSet.setStatus(false);
					cmnDeductionTypesConfigDetailsResultSet.setMessage("Error");
					cmnDeductionTypesConfigDetailsResultSet
							.setMessageDescription("No common Deduction configuration available in db");
				}

			} else {
				cmnDeductionTypesConfigDetailsResultSet.setStatus(false);
				cmnDeductionTypesConfigDetailsResultSet.setMessage("Failed");
				cmnDeductionTypesConfigDetailsResultSet.setMessageDescription("Invalid Inputs");
			}
		} catch (Exception e) {
			cmnDeductionTypesConfigDetailsResultSet.setStatus(false);
			cmnDeductionTypesConfigDetailsResultSet.setMessage("Error");
			cmnDeductionTypesConfigDetailsResultSet.setMessageDescription(e.getMessage());
		}
		return cmnDeductionTypesConfigDetailsResultSet;

	}

	@Override
	public CmnDeductionTypesConfigDetailsResultset saveAndUpdateDeductionTypesConfigDetails(List<CmnDeductionTypesConfigDetailsDTO> deductiontypesconfigdetailsList, String loginUser) {
		CmnDeductionTypesConfigDetailsResultset deductiontypesconfigdetailsResultset = new CmnDeductionTypesConfigDetailsResultset();
		try {
			if (deductiontypesconfigdetailsList != null && deductiontypesconfigdetailsList.size() > 0) {
				for (CmnDeductionTypesConfigDetailsDTO deductiontypesconfigdto : deductiontypesconfigdetailsList) {
					if (deductiontypesconfigdto.getCmnDtConfigDetlsId() == null) {
						CmnDeductionTypeConfigDetailsEntity deductiontypesconfigEntity = new CmnDeductionTypeConfigDetailsEntity();
						BeanUtils.copyProperties(deductiontypesconfigdto, deductiontypesconfigEntity);
						deductiontypesconfigEntity.setCmnDtConfigDetlsId(cmnTableSeqService.getNextSequence(
								ApplicationConstants.CMN_SALARY_DEDUCTIONS_CONFIG_DETAILS,
								ApplicationConstants.CMN_SALARY_DED_CONFIG_DETLS_ID));
						BaseUtils.setBaseData(deductiontypesconfigEntity, loginUser);
						save(deductiontypesconfigEntity);
						deductiontypesconfigdetailsResultset.setMessageDescription("Record saved successfully.");
					} else if (deductiontypesconfigdto != null && deductiontypesconfigdto.getCmnDtConfigDetlsId() > 0) {
						CmnDeductionTypeConfigDetailsEntity deductiontypesconfigEntity = cmnDeductionTypesConfigDetailsRepository
								.getDeductionTypesConfigDetailsBycmnDtConfigDetlsId(
										deductiontypesconfigdto.getCmnDtConfigDetlsId());
						if (deductiontypesconfigEntity != null) {
							BeanUtils.copyProperties(deductiontypesconfigdto, deductiontypesconfigEntity);
							BaseUtils.modifyBaseData(deductiontypesconfigEntity, loginUser);
							save(deductiontypesconfigEntity);
							deductiontypesconfigdetailsResultset.setMessageDescription("Record saved successfully.");
						}
					} else {
						deductiontypesconfigdetailsResultset.setStatus(false);
						deductiontypesconfigdetailsResultset.setMessage("Error");
						deductiontypesconfigdetailsResultset
								.setMessageDescription("Unable to save Deduction type Configurations");
					}
				}

			}
		} catch (Exception e) {
			deductiontypesconfigdetailsResultset.setStatus(false);
			deductiontypesconfigdetailsResultset.setMessage("Exception");
			deductiontypesconfigdetailsResultset.setMessageDescription(e.getMessage());
		}
		return deductiontypesconfigdetailsResultset;

	}

	@Override
	public CmnDeductionTypesConfigDetailsResultset deleteDeductionTypesConfigDetailsbyConfigurationId(
			Long configurationId) {
		CmnDeductionTypesConfigDetailsResultset cmnDeductionTypesConfigDetailsResultSet = new CmnDeductionTypesConfigDetailsResultset();
		try {
			if(configurationId != null) {
				List<CmnDeductionTypeConfigDetailsEntity> cmnDeductionTypesConfigDetailsEntity = cmnDeductionTypesConfigDetailsRepository.getDeductionTypesConfigDetailsByConfigurationId(configurationId);
				deleteAll(cmnDeductionTypesConfigDetailsEntity);
				cmnDeductionTypesConfigDetailsResultSet.setMessageDescription("Deduction Type Config deleted successfully.");
				cmnDeductionTypesConfigDetailsResultSet.setMessage("Success");
			} else {
				cmnDeductionTypesConfigDetailsResultSet.setStatus(false);
				cmnDeductionTypesConfigDetailsResultSet.setMessage("Failed");
				cmnDeductionTypesConfigDetailsResultSet.setMessageDescription("Invalid Inputs.");
			}
		} catch (Exception e) {
			cmnDeductionTypesConfigDetailsResultSet.setStatus(false);
			cmnDeductionTypesConfigDetailsResultSet.setMessage("Exception");
			cmnDeductionTypesConfigDetailsResultSet.setMessageDescription(e.getMessage());
		}
		return cmnDeductionTypesConfigDetailsResultSet;
	}

}
