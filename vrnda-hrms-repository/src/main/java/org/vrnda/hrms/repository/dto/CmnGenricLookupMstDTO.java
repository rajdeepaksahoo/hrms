package org.vrnda.hrms.repository.dto;

import lombok.Data;

@Data
public class CmnGenricLookupMstDTO {

	int id;

	String dropDownName;

	long parentLookupId;
	
}
