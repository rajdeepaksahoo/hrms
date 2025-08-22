package org.vrnda.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "EMPLOYEE_FINANCIAL_ACCOUNT_DETAILS")
public class EmployeeFinancialAccountDetailsEntity extends EntityBaseModel  {

	@Id
	@Column(name = "FIN_ACCT_DET_ID")
	private Long finAcctDetId;

	@Column(name = "AADHAR_NUMBER")
	private Long aadharNumber;

	@Column(name = "PAN_NUMBER")
	private String panNumber;

	@Column(name = "UAN_NUMBER")
	private String uanNumber ;

	@Column(name = "PF_NUMBER")
	private String pfNumber ;

	@Column(name="ESI_NUMBER")
	private String esiNumber;	

	@Column(name = "BANK_ACCOUNT_NUMBER")
	private Long bankAccountNumber;

	@Column(name = "NAME_AS_IN_BANK")
	private String nameAsInBank;

	@Column(name = "BANK_NAME")
	private String bankName;

	@Column(name = "BRANCH")
	private String branch ;

	@Column(name = "IFSC")
	private String ifsc ;

}
