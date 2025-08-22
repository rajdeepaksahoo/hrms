package org.vrnda.hrms.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CMN_BANDS_MST")
public class CmnBandsMstEntity  extends EntityBaseModel {

	@Id
	@Column(name = "BAND_ID")
	private Long bandId;

	@Column(name = "BAND_NAME")
	private String  bandName;

	@Column(name = "BAND_DESCRIPTION")
	private String bandDescription;

	@Column(name = "SYSTEM_CONFIG_FLAG")
	private String  systemConfigFlag;

}
