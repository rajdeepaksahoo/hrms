package org.vrnda.hrms.service.resultset;

import java.util.List;

import org.vrnda.hrms.entity.CmnSalaryEarningsConfigDetailsEntity;
import org.vrnda.hrms.repository.dto.CmnSalaryEarningsConfigDetailsDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class CmnSalaryEarningsConfigDetailsResultSet extends GenericResultSet {

	List<CmnSalaryEarningsConfigDetailsEntity> CmnSalaryEarningsConfigDetailsEntityList;

	List<CmnSalaryEarningsConfigDetailsDTO> CmnSalaryEarningsConfigDetailsDtoList;

}
