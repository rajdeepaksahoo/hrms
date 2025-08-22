package org.vrnda.hrms.service;

import java.util.List;

import org.vrnda.hrms.entity.CmnSalaryAnnualBenefitsConfigDetailsEntity;
import org.vrnda.hrms.repository.dto.CmnSalaryAnnualBenefitsConfigDetailsDTO;
import org.vrnda.hrms.service.resultset.CmnSalaryAnnualBenefitsConfigDetailResultSet;


public interface CmnSalaryAnnualBenefitsConfigDetailsService extends GenericService<CmnSalaryAnnualBenefitsConfigDetailsEntity, Long> {
	
	public CmnSalaryAnnualBenefitsConfigDetailResultSet getSalaryAnnualTypeconfigDetails(Long configurationId);
	
	public CmnSalaryAnnualBenefitsConfigDetailResultSet saveOrUpdateAnnualTypes(List<CmnSalaryAnnualBenefitsConfigDetailsDTO> cmnSalaryAnnualTypesDto, String loggedInUser);
	
	public CmnSalaryAnnualBenefitsConfigDetailResultSet deleteAnnualConfigDetailsbyConfigurationId(Long configurationId);


}
