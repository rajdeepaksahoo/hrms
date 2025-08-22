package org.vrnda.hrms.repository.dto;

import lombok.Data;

@Data
public class CmnAddressesMstDTO {

	Long addressId;

	String addressLine1;

	String addressLine2;

	String addressLine3;

	String addressLine4;

	Long addressTypeLookupId;

	Long countryId;

	Long stateId;

	Long districtId;

	String cityName;

	Long contactId;

	Long pincode;

	Long statusLookupId;

	String countryName;

}
