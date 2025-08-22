package org.vrnda.hrms.service.resultset;


import java.util.List;

import org.vrnda.hrms.entity.CmnSalaryAllowanceConfigDetailsEntity;
import org.vrnda.hrms.entity.CmnSalaryAnnualBenefitsConfigDetailsEntity;
import org.vrnda.hrms.repository.dto.CmnSalaryAllowanceConfigDetailsDTO;
import org.vrnda.hrms.repository.dto.CmnSalaryAnnualBenefitsConfigDetailsDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class CmnSalaryAnnualBenefitsConfigDetailResultSet extends GenericResultSet {

	Long configurationId;
	
	List<CmnSalaryAnnualBenefitsConfigDetailsEntity> cmnSalaryAnnualBenefitsConfigDetailsEntity;

	List<CmnSalaryAnnualBenefitsConfigDetailsDTO> cmnSalaryAnnualBenefitsConfigDetailsDTO;

	
}
