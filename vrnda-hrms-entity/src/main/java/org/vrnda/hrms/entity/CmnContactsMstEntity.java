package org.vrnda.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CMN_CONTACTS_MST")
public class CmnContactsMstEntity extends EntityBaseModel {

	@Id
	@Column(name = "CONTACT_ID")
	private Long contactId;

	@Column(name = "PERSONAL_EMAIL")
	private String personalEmail;

	@Column(name = "PROFESSIONAL_MAIL")
	private String professionalEmail;

	@Column(name = "FAX")
	private String fax;

	@Column(name = "PHONE_NO")
	private String phoneNo;

	@Column(name = "RESIDENCE_PHONE")
	private String residencePhone;

}
