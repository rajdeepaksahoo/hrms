package org.vrnda.hrms.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@Data
@MappedSuperclass
public class EntityBaseModel {

	@Column(name = "STATUS_LOOKUP_ID")
	private Long statusLookupId;
	
	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;
	
	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;
	
}
