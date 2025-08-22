package org.vrnda.hrms.service.resultset;

import java.util.List;

import org.vrnda.hrms.entity.CmnAppRoleAccessPrivilegesEntity;
import org.vrnda.hrms.repository.dto.CmnAppRoleAccessPrivilegesDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class RoleAccessPrivilegesResultSet extends GenericResultSet {

	private CmnAppRoleAccessPrivilegesDTO cmnAppRoleAccessPrivilegesDTO;

	private List<CmnAppRoleAccessPrivilegesDTO> cmnAppRoleAccessPrivilegesDTOList;
	
	private List<CmnAppRoleAccessPrivilegesEntity> cmnAppRoleAccessPrivilegesList;

}
