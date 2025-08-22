package org.vrnda.hrms.service.resultset;

import java.util.List;

import org.vrnda.hrms.entity.CmnGeneralLoanConfigDetailsEntity;
import org.vrnda.hrms.repository.dto.CmnGeneralLoanConfigDetailsDTO;
import org.vrnda.hrms.repository.dto.CmnLookupMstDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class CmnGeneralLoanConfigDetailsResultSet extends GenericResultSet {

	List<CmnGeneralLoanConfigDetailsEntity> cmnGeneralLoanConfigDetailsEntityList;

	List<CmnGeneralLoanConfigDetailsDTO> cmnGeneralConfigDetailsDtoList;

	CmnGeneralLoanConfigDetailsEntity cmnGeneralLoanConfigDetailsEntity;

	List<CmnLookupMstDTO> cmnLookupMstDTO;
		
	
	
	

}
