package org.vrnda.hrms.repository.dto;

import lombok.Data;

@Data
public class CmnDepartmentsMstDTO {

	Long departmentId;

	String departmentName;

	String departmentDescription;

	String systemConfigFlag;

	Long statusLookupId;

}
