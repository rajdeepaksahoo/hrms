package org.vrnda.hrms.service.resultset;

import java.util.List;

import org.vrnda.hrms.entity.CmnLeaveTypesMstEntity;
import org.vrnda.hrms.repository.dto.CmnLeaveTypesMstDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class CmnLeaveTypesMstResultSet  extends GenericResultSet{
	
	List<CmnLeaveTypesMstEntity> cmnLeaveTypesMstEntityList;

	List<CmnLeaveTypesMstDTO> cmnLeaveTypesMstDtoList;
	
}
