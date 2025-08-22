package org.vrnda.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "EMPLOYEE_CONTACT_PERSONS")
public class EmployeeContactPersonsEntity  extends EntityBaseModel {

	@Id
	@Column(name = "EMPLOYEE_CNCT_PRSN_ID")
	private Long employeeCnctPrsnId;

	@Column(name = "CNCT_PRSN_NAME")
	private String cnctPrsnName;

	@Column(name = "RELATION_LOOKUP_ID")
	private Long relationLookupId;

	@Column(name = "CONTACT_ID")
	private Long contactId;

}
