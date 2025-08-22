package org.vrnda.hrms.repository.dto;

import lombok.Data;

@Data
public class CmnBandsMstDTO {

	Long bandId;

	String  bandName;

	String bandDescription;

	String  systemConfigFlag;

	Long statusLookupId;

}
