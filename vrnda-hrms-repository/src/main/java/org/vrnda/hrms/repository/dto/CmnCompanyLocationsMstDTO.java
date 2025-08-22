package org.vrnda.hrms.repository.dto;


import lombok.Data;

@Data
public class CmnCompanyLocationsMstDTO{

	Long locationId;

	String locationName;

	String locationDescription;

	Long addressId;
	
	String address;

	Double latitude;

	Double longitude;
	
	Long statusLookupId;

	String  systemConfigFlag;
	
	Long countryId;
	
	Long stateId;
	
	Long districtId;
	
	String cityName;
	
	Long pincode;
	
	public CmnCompanyLocationsMstDTO() {
		super();
	}
	public CmnCompanyLocationsMstDTO(Long locationId,String locationName,String locationDescription, Long addressId,String address,Double latitude,Double longitude,
			Long statusLookupId,String  systemConfigFlag,Long countryId,Long stateId,Long districtId,String cityName,Long pincode) {
		this.locationId = locationId;
		this.locationName = locationName;
		this.locationDescription = locationDescription;
		this.addressId = addressId;
		this.address=address;
		this.latitude=latitude;
		this.longitude=longitude;
		this.statusLookupId=statusLookupId;
		this.systemConfigFlag=systemConfigFlag;
		this.countryId=countryId;
		this.stateId=stateId;
		this.districtId=districtId;
		this.cityName=cityName;
		this.pincode=pincode;
	}

	@Override
	public String toString() {
		return "CmnCompanyLocationsMstDTO [locationId=" + locationId + ", locationName=" + locationName + ", locationDescription=" + locationDescription + ", addressId="+ addressId +  ", address="+ address + 
				", latitude="+ latitude +  ", longitude="+ longitude +  ", statusLookupId="+ statusLookupId +  ", systemConfigFlag="+ systemConfigFlag + 
				", countryId="+ countryId +  ", stateId="+ stateId +  ", districtId="+ districtId +  ", cityName="+ cityName +  ", pincode="+ pincode + "]";
	}

}
