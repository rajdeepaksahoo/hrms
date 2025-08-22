package org.vrnda.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CMN_LOOKUP_MST")
public class CmnLookupMstEntity extends EntityBaseModel {

	@Id
	@Column(name= "LOOKUP_ID")
	private Long lookupId;

	@Column(name= "PARENT_LOOKUP_ID")
	private Long parentLookupId;

	@Column(name= "LOOKUP_NAME")
	private String lookupName;

	@Column(name= "LOOKUP_DESC")
	private String lookupDesc;

	@Column(name= "ORDER_NO")
	private Integer orderNo;

	@Column(name = "SYSTEM_CONFIG_FLAG")
	private String  systemConfigFlag;
	
	@Column(name = "LOOKUP_VALUE")
	private String  lookupValue;

}
