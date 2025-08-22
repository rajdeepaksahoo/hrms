package org.vrnda.hrms.service.resultset;

import java.util.List;

import org.vrnda.hrms.entity.CmnContactsMstEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class CmnContactsMstResultSet  extends GenericResultSet{
	
	CmnContactsMstEntity cmnContactsMstEntity;

	List<CmnContactsMstEntity> cmnContactsMstEntityList;

}
