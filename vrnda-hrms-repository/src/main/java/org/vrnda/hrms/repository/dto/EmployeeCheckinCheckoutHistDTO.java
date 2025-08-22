package org.vrnda.hrms.repository.dto;

import java.util.Date;

import lombok.Data;

@Data
public class EmployeeCheckinCheckoutHistDTO {

	Long empCheckinId;

	Long employeeId;

	Date checkinTime;

	Date checkoutTime;

	Long parentEmpCheckinId;

	Long statusLookupId;

}
