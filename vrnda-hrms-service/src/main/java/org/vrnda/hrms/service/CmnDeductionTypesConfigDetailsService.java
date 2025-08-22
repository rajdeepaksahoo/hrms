package org.vrnda.hrms.service;

import java.util.List;

import org.vrnda.hrms.entity.CmnDeductionTypeConfigDetailsEntity;
import org.vrnda.hrms.repository.dto.CmnDeductionTypesConfigDetailsDTO;
import org.vrnda.hrms.service.resultset.CmnDeductionTypesConfigDetailsResultset;


public interface CmnDeductionTypesConfigDetailsService extends GenericService<CmnDeductionTypeConfigDetailsEntity, Long>{

	public CmnDeductionTypesConfigDetailsResultset getDeductionTypesConfigDetailsByConfigurationId(Long configurationId);
	
	public CmnDeductionTypesConfigDetailsResultset saveAndUpdateDeductionTypesConfigDetails(List<CmnDeductionTypesConfigDetailsDTO> deductiontypesconfigdetailsList,String loginUser);
    
	public CmnDeductionTypesConfigDetailsResultset deleteDeductionTypesConfigDetailsbyConfigurationId(Long configurationId);
}
