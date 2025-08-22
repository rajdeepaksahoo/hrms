package org.vrnda.hrms.repository.dto;

import lombok.Data;

@Data
public class CmnTableSeqMstDTO {

	Long Id;

	Long tableName;

	String columnName;

	String generatedId;

	String systemConfigFlag;

	Long statusLookupId;

}
