package org.vrnda.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CMN_DISTRICT_MST")
public class CmnDistrictMstEntity  extends EntityBaseModel{

	@Id
	@Column(name = "DISTRICT_ID")
	private Long districtId;

	@Column(name = "DISTRICT_NAME")
	private String districtName;

}
