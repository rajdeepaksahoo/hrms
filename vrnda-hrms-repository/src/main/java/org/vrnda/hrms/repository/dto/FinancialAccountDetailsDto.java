package org.vrnda.hrms.repository.dto;

import lombok.Data;

@Data
public class FinancialAccountDetailsDto {

	Long finAcctDetId;
	Long aadharNumber;
	String panNumber;
	String uanNumber;
	String pfNumber;
	String esiNumber;
	Long bankAccountNumber;
	String nameAsInBank;
	String bankName;
	String branch;
	String ifsc;
	Long statusFlagId;
	Long statusLookupId;

}
