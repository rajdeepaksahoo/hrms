package org.vrnda.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CMN_TABLE_SEQ_MST")
public class CmnTableSeqMstEntity extends EntityBaseModel {

	@Id
	@Column(name= "ID")
	private Long Id;

	@Column(name= "TABLE_NAME")
	private Long tableName;

	@Column(name= "COLUMN_NAME")
	private String columnName;

	@Column(name= "GENERATED_ID")
	private String generatedId;

	@Column(name = "SYSTEM_CONFIG_FLAG")
	private String systemConfigFlag;

}
