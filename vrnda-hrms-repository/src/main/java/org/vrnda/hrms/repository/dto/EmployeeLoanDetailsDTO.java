package org.vrnda.hrms.repository.dto;

import lombok.Data;

@Data
public class EmployeeLoanDetailsDTO {

	Long loanId;

	Long employeeId;

	Long principleAmount;

	String loanDetailsString;

	String repaymentDetailsString;

	Long loanStatus;

	Long yearId;

	String statusLookupId;

	byte[] loanDetails;

	byte[] repaymentDetails;
	
	String createdDate;
	
	String employeeFullName;

}
