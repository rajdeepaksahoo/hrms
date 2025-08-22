package org.vrnda.hrms.repository;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnTableSeqMstEntity;

@Repository
public interface CmnTableSequenceRepository extends PagingAndSortingRepository<CmnTableSeqMstEntity, Long>{
	
	@Procedure("PROC_GET_COL_NEXT_SEQ_ID")
	public Long getNextSequence( String tableName, String columnName, int count);
	
	

}
