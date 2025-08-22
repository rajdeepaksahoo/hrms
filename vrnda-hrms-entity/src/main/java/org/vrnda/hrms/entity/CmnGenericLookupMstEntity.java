package org.vrnda.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CMN_GENERIC_LOOKUP_MST")
public class CmnGenericLookupMstEntity extends EntityBaseModel {

	@Id
	@Column(name = "ID")
	private int id;

	@Column(name = "DROP_DOWN_NAME")
	private String dropDownName;

	@Column(name = "PARENT_LOOKUP_ID")
	private long parentLookupId;

}
