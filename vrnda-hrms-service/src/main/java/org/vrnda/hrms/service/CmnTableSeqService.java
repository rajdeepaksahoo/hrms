package org.vrnda.hrms.service;

import org.vrnda.hrms.entity.CmnTableSeqMstEntity;

public interface CmnTableSeqService  extends GenericService<CmnTableSeqMstEntity, Long>{
	
	public Long getNextSequence(String tableName, String columnName);

}
