package org.vrnda.hrms.repository.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class EmployeeCompOffLeaveMstDTO {

	Long empCoLvMstId;

	Long employeeId;

	Long leaveTypeLookupId;

	String isHalfDay;

	BigDecimal noOfLeaves;

	Timestamp fromDate;

	Timestamp toDate;

	Timestamp leaveExpiryDate;

	Long statusLookupId;
	
    String compoffUsed;
}
