package org.vrnda.hrms.repository.dto;

import lombok.Data;

@Data
public class CmnDesignationsMstDTO {

	Long designationId;

	String designationName;

	String designationDescription;

	String systemConfigFlag;

	Long statusLookupId;

}
