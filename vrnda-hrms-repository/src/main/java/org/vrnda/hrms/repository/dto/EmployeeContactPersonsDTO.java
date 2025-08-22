package org.vrnda.hrms.repository.dto;

import lombok.Data;

@Data
public class EmployeeContactPersonsDTO {

	Long employeeCnctPrsnId;

	Long employeePrsnId;

	String cnctPrsnName;

	Long relationLookupId;

	Long contactId;

	Long statusLookupId;

	String emergencyPhoneNumber;

}
