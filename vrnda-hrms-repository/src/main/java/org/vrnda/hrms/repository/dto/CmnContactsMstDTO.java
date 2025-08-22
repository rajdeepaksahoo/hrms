package org.vrnda.hrms.repository.dto;

import lombok.Data;

@Data
public class CmnContactsMstDTO {

	Long contactId;

	String personalEmail;

	String professionalEmail;

	String fax;

	String phoneNo;

	String residencePhone;

	Long statusLookupId;

}
