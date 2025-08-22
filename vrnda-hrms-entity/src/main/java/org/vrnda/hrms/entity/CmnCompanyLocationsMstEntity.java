package org.vrnda.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CMN_COMPANY_LOCATIONS_MST")
public class CmnCompanyLocationsMstEntity extends EntityBaseModel{

	@Id
	@Column(name = "LOCATION_ID")
	private Long locationId;

	@Column(name= "LOCATION_NAME" )
	private String locationName;

	@Column(name= "LOCATION_DESCRIPTION" )
	private String locationDescription;

	@Column(name= "ADDRESS_ID" )
	private Long addressId;

	@Column(name= "LATITUDE" )
	private Double latitude;

	@Column(name= "LONGITUDE" )
	private Double longitude;

	@Column(name = "SYSTEM_CONFIG_FLAG")
	private String  systemConfigFlag;

}
