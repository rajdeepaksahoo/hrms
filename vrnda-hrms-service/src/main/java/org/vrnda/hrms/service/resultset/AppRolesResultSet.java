package org.vrnda.hrms.service.resultset;

import java.util.List;

import org.vrnda.hrms.entity.CmnAppRolesMstEntity;
import org.vrnda.hrms.repository.dto.CmnAppRolesMstDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class AppRolesResultSet extends GenericResultSet {

	CmnAppRolesMstEntity cmnAppRolesMstEntity;

	List<CmnAppRolesMstEntity> cmnAppRolesMstEntityList;
	List<CmnAppRolesMstDTO> cmnAppRolesMstEntityDtoList;

}