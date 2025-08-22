package org.vrnda.hrms.service.resultset;

import org.vrnda.hrms.entity.CmnLeaveConfigDetailsEntity;
import org.vrnda.hrms.repository.dto.CmnLeaveConfigDetailsDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class CmnLeaveConfigDetailsResultSet  extends GenericResultSet{

	CmnLeaveConfigDetailsEntity cmnLeaveConfigDetailsEntity;
	
	CmnLeaveConfigDetailsDTO cmnLeaveConfigDetailsDTO;

}
