package org.vrnda.hrms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "EMPLOYEE_DETAILS")
public class EmployeeDetailsEntity extends EntityBaseModel {

	@Id
	@Column(name = "EMPLOYEE_ID")
	private Long employeeId;

	@Column(name = "EMPLOYEE_FIRST_NAME")
	private String employeeFirstName;

	@Column(name = "EMPLOYEE_MIDDLE_NAME")
	private String employeeMiddleName;

	@Column(name = "EMPLOYEE_LAST_NAME")
	private String employeeLastName;

	@Column(name = "FATHER_FULLNAME")
	private String fatherFullname;

	@Column(name = "MOTHER_FULLNAME")
	private String motherFullname;

	@Column(name = "DATE_OF_BIRTH")
	private Date dateOfBirth;

	@Column(name = "DATE_OF_JOINING")
	private Date dateOfJoining;

	@Column(name = "BLOOD_LOOKUP_ID")
	private Long bloodLookupId;

	@Column(name = "GENDER_ID")
	private Long genderId;

	// @Column(name = "IMAGE")
	// private Blob profileImage;

	@Column(name = "COMPANY_DESIG_ID")
	private Long companyDesigId;

	@Column(name = "EMPLOYEMENT_TYPE")
	private Long employmentType;

	@Column(name = "IS_IN_PROBATION")
	private String isInProbation;

	@Column(name = "PROBATION_PERIOD_MONTHS")
	private Integer probationPeriodMonths;

	@Column(name = "PRESENT_ADDRESS_ID")
	private Long presentAddressId;

	@Column(name = "PERM_ADDRESS_ID")
	private Long permAddressId;

	@Column(name = "EMPLOYEE_CNCT_PRSN_ID")
	private Long employeeCnctPrsnId;

	@Column(name = "CONTACT_ID")
	private Long contactId;

	@Column(name = "MANAGER_ID")
	private Long managerId;

	@Column(name = "HR_ID")
	private Long hrId;

	@Column(name = "FIN_ACCT_DET_ID")
	private Long finAcctDetId;

	@Column(name = "LOCATION_ID")
	private Long locationId;

	@Column(name = "DEPARTMENT_ID")
	private Long departmentId;

	@Column(name = "COMPANY_ROLE_ID")
	private Long companyRoleId;

}
