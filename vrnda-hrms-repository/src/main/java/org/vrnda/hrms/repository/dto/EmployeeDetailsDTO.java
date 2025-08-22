package org.vrnda.hrms.repository.dto;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class EmployeeDetailsDTO {

	Long employeeId;

	String employeeFullName;

	String employeeFirstName;

	String employeeMiddleName;

	String employeeLastName;

	String fatherFullname;

	String motherFullname;

	Date dateOfBirth;

	Date dateOfJoining;

	Long bloodLookupId;

	Long genderId;

	Long companyDesigId;

	Long employmentType;

	String isInProbation;

	Integer probationPeriodMonths;

	Long presentAddressId;

	String isPermanentAddress;

	Long permAddressId;

	Long employeePrsnId;

	Long contactId;

	Long managerId;

	Long hrId;

	Long finAcctDetId;

	Long locationId;

	Long departmentId;

	Long statusLookupId;

	Long employeeCnctPrsnId;

	Date fromDateOfJoining;

	Date toDateOfJoining;

	String createdBy;

	String updatedBy;

	Timestamp createdDate;

	Timestamp updatedDate;

	CmnAddressesMstDTO employeeAddress;

	CmnContactsMstDTO employeeContacts;

	EmployeeContactPersonsDTO employeeEmergencyContact;

	CmnAddressesMstDTO employeePermanentAddress;

	EmployeeFinancialAccountDetailsDTO employeeFinancialDetails;

	String managerFullName;

	List<LeaveDetailsDTO> leaveDetailsList;

	Long companyRoleId;

	String designationDescription;

	String locationName;

	String companyRoleDescription;

	String departmentDescription;

	String hrFullName;
	
	String employmentTypeName;
	
	String professionalEmail;

}
