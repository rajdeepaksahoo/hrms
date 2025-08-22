package org.vrnda.hrms.service.resultset;

import java.util.List;

import org.vrnda.hrms.entity.CmnMenusMstEntity;
import org.vrnda.hrms.repository.dto.CmnMenusMstDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class CmnMenusMstResultSet extends GenericResultSet {

	CmnMenusMstEntity cmnMenusMstEntity;
	
	List<CmnMenusMstEntity> cmnMenusMstEntityList;

	List<CmnMenusMstDTO> cmnMenusMstDtoList;

}
