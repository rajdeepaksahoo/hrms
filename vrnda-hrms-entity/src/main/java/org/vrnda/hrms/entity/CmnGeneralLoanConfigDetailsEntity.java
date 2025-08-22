package org.vrnda.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CMN_GENERAL_LOAN_CONFIG_DETAILS")
public class CmnGeneralLoanConfigDetailsEntity extends EntityBaseModel{

	@Id
	@Column(name = "CMN_GEN_LOAN_CONFIG_DETLS_ID")
	private Long cmnGenLoanConfigDetlsId;

	@Column(name = "CONFIGURATION_ID")
	private Long configurationId;

	@Column(name = "LOAN_BASE_AMOUNT_TYPE")
	private Long loanBaseAmountType;

	@Column(name = "BASE_AMOUNT_MULTIPLIER")
	private Long baseAmountMultiplier;

	@Column(name = "FIXED_LOAN_AMOUNT")
	private Long fixedLoanAmount;

	@Column(name = "MAX_EMI")
	private Long maxEmi; 

}
