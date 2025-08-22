package org.vrnda.hrms.repository.dto;

import java.util.List;

import lombok.Data;

@Data
public class CmnGeneralLoanConfigDetailsDTO {

	Long cmnGenLoanConfigDetlsId;

	Long configurationId;

	Long loanBaseAmountType;

	Long baseAmountMultiplier;

	Long fixedLoanAmount;

	Long maxEmi;

	Long statusLookupId;

	List<CmnLookupMstDTO> cmnLookupMstDTOList;

}
