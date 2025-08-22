package org.vrnda.hrms.service.resultset;

import java.util.List;

import org.vrnda.hrms.entity.CmnConfigurationsMstEntity;
import org.vrnda.hrms.repository.dto.CmnConfigurationsMstDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class CmnConfigurationsMstResultSet extends GenericResultSet{

	List<CmnConfigurationsMstEntity> cmnConfigurationsMstEntityList;

	List<CmnConfigurationsMstDTO> cmnConfigurationsMstDtoList;

}
