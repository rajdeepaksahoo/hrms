package org.vrnda.hrms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;


@Data
@Entity
@Table(name = "EMPLOYEE_LOAN_DETAILS")
public class EmployeeLoanDetailsEntity extends EntityBaseModel{

	@Id
	@Column(name = "LOAN_ID")
	private Long loanId;

	@Column(name = "EMPLOYEE_ID")
	private Long employeeId;

	@Column(name = "PRINCIPLE_AMOUNT")
	private Long principleAmount;

	@Column(name = "LOAN_DETAILS")
	private byte[] loanDetails;

	@Column(name = "REPAYMENT_DETAILS")
	private byte[] repaymentDetails;

	@Column(name = "LOAN_STATUS")
	private Long loanStatus;

	@Column(name = "YEAR_ID")
	private Long yearId;

	@Transient
	private String empLoanDetailsData;

}
