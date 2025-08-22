package org.vrnda.hrms.service.resultset;

import java.util.List;
import java.util.Map;

import org.vrnda.hrms.entity.CmnLookupMstEntity;
import org.vrnda.hrms.repository.dto.CmnLookupMstDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class CmnLookupMstResultSet  extends GenericResultSet{

	CmnLookupMstEntity cmnLookupMstEntity;

	List<CmnLookupMstEntity> cmnLookupMstEntityList;

	List<CmnLookupMstDTO> cmnLookupMstDtoList;

	Map<Long, String> lookupIdAndLookupNameMapings;

}
