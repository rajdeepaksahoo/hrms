package org.vrnda.hrms.repository.dto;

import lombok.Data;

@Data
public class CmnLookupMstDTO {

	Long lookupId;

	Long parentLookupId;

	String lookupName;

	String lookupDesc;

	Integer orderNo;
	
	String parentLookupType;

	String systemConfigFlag;

	Long statusLookupId;
	
	String lookupValue;

}
