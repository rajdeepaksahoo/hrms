package org.vrnda.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CMN_COUNTRYS_MST")
public class CmnCountrysMstEntity extends EntityBaseModel{

	@Id
	@Column(name = "COUNTRY_ID")
	private Long countryId;

	@Column(name = "COUNTRY_NAME")
	private String countryName;

}
