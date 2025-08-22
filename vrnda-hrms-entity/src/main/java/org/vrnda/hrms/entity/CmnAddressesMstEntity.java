package org.vrnda.hrms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "CMN_ADDRESSES_MST")
public class CmnAddressesMstEntity extends EntityBaseModel {

	@Id
	@Column(name = "ADDRESS_ID")
	private Long addressId;

	@Column(name = "ADDRESS_LINE_1")
	private String addressLine1;

	@Column(name = "ADDRESS_LINE_2")
	private String addressLine2;

	@Column(name = "ADDRESS_LINE_3")
	private String addressLine3;

	@Column(name = "ADDRESS_LINE_4")
	private String addressLine4;

	@Column(name = "ADD_TYPE_LOOKUPID")
	private Long addressTypeLookupId;

	@Column(name = "COUNTRY_ID")
	private Long countryId;

	@Column(name = "STATE_ID")
	private Long stateId;

	@Column(name = "DISTRICT_ID")
	private Long districtId;

	@Column(name = "CITY_NAME")
	private String cityName;

	@Column(name = "CONTACT_ID")
	private Long contactId;

	@Column(name = "PINCODE")
	private Long pincode;

}
