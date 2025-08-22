package org.vrnda.hrms.service.resultset;

import java.util.List;

import org.vrnda.hrms.entity.CmnDeductionTypeConfigDetailsEntity;
import org.vrnda.hrms.entity.CmnLeaveConfigDetailsEntity;
import org.vrnda.hrms.repository.dto.CmnDeductionTypesConfigDetailsDTO;
import org.vrnda.hrms.repository.dto.CmnLeaveConfigDetailsDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class CmnDeductionTypesConfigDetailsResultset extends GenericResultSet{
	List<CmnDeductionTypeConfigDetailsEntity> deductiontypeconfigdetailsentitylist;
	List<CmnDeductionTypesConfigDetailsDTO> deductiontypeconfigdetailsdtolist;
}
