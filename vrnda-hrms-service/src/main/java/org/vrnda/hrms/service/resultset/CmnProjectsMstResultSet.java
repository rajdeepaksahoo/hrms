package org.vrnda.hrms.service.resultset;

import java.util.List;

import org.vrnda.hrms.entity.CmnProjectsMstEntity;
import org.vrnda.hrms.repository.dto.CmnProjectsMstDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class CmnProjectsMstResultSet extends GenericResultSet {
	
	Long projectId;

	CmnProjectsMstEntity cmnProjectMstEntity;
	
	CmnProjectsMstDTO cmnProjectsMstDTO;

	List<CmnProjectsMstDTO> cmnProjectMstDtoList;

	List<CmnProjectsMstEntity> cmnProjectMstEntityList;
}
