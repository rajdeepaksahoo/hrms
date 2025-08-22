package org.vrnda.hrms.service.resultset;

import java.util.List;

import org.vrnda.hrms.entity.CmnSalaryAllowanceConfigDetailsEntity;
import org.vrnda.hrms.repository.dto.CmnSalaryAllowanceConfigDetailsDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class CmnSalaryAllowanceConfigDetailResultSet extends GenericResultSet {

	Long configurationId;

	List<CmnSalaryAllowanceConfigDetailsEntity> cmnSalaryAllowanceConfigDetailsEntityList;

	List<CmnSalaryAllowanceConfigDetailsDTO> cmnSalaryAllowanceConfigDetailsDtoList;

}
