package org.vrnda.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table(name = "CMN_STATE_MST")
public class CmnStatesMstEntity extends EntityBaseModel{

	@Id
	@Column(name = "STATE_ID")
	private Long stateId;

	@Column(name = "STATE_NAME")
	private String stateName;

}
