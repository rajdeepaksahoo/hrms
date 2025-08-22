package org.vrnda.hrms.repository.dto;

import lombok.Data;

@Data
public class LeaveDetailsDTO {

	Long lvTypeId;

	String lvTypeName;

	Double leaveBalance;

}
