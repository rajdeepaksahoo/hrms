package org.vrnda.hrms.service.resultset;

import java.util.List;

import org.vrnda.hrms.entity.CmnCompanyRolesMstEntity;
import org.vrnda.hrms.repository.dto.CmnCompanyRolesMstDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class CmnCompanyRolesMstResultSet extends GenericResultSet {

	CmnCompanyRolesMstEntity cmnCompanyRolesMstEntity;

	List<CmnCompanyRolesMstEntity> cmnCompanyRolesMstEntityList;
	
	CmnCompanyRolesMstDTO cmnCompanyRolesMsDTO;

}
