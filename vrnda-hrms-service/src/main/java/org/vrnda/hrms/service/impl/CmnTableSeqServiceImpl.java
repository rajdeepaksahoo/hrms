package org.vrnda.hrms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.CmnTableSeqMstEntity;
import org.vrnda.hrms.repository.CmnTableSequenceRepository;
import org.vrnda.hrms.service.CmnTableSeqService;

@Service
public class CmnTableSeqServiceImpl extends GenericServiceImpl<CmnTableSeqMstEntity, Long> implements CmnTableSeqService {

	public CmnTableSeqServiceImpl(PagingAndSortingRepository<CmnTableSeqMstEntity, Long> typeRepository) {
		super(typeRepository);
	}

	@Autowired
	CmnTableSequenceRepository cmnTableSequenceRepository;

	@Override
	public Long getNextSequence(String tableName, String columnName) {
		Long sequence = cmnTableSequenceRepository.getNextSequence(tableName, columnName, 1);
		return sequence;
	}

}
