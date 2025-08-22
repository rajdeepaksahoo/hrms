package org.vrnda.hrms.service.resultset;

import java.util.List;

import org.vrnda.hrms.entity.CmnProjectTeamsEntity;
import org.vrnda.hrms.repository.dto.CmnProjectTeamsDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class CmnProjectTeamsResultSet extends GenericResultSet {

	CmnProjectTeamsEntity cmnProjectTeamsEntity;

	CmnProjectTeamsDTO cmnProjectTeamsDTO;

	List<CmnProjectTeamsEntity> cmnProjectTeamsEntityList;

	List<CmnProjectTeamsDTO> cmnProjectTeamsDTOList;
}
