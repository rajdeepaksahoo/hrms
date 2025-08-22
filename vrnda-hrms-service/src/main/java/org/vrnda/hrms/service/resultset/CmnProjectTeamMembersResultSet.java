package org.vrnda.hrms.service.resultset;

import java.util.List;

import org.vrnda.hrms.entity.CmnProjectTeamMembersEntity;
import org.vrnda.hrms.repository.dto.CmnProjectTeamMembersDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class CmnProjectTeamMembersResultSet extends GenericResultSet {

	List<CmnProjectTeamMembersEntity> cmnProjectTeamMembersEntityList;

	List<CmnProjectTeamMembersDTO> cmnProjectTeamMembersDtoList;

}
