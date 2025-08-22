package org.vrnda.hrms.service.resultset;

import java.util.List;

import org.vrnda.hrms.entity.EmployeeCompOffLeaveMstEntity;
import org.vrnda.hrms.repository.dto.EmployeeCompOffLeaveMstDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class EmployeeCompOffLeaveMstResultSet extends GenericResultSet {

	List<EmployeeCompOffLeaveMstDTO> employeeCompOffLeaveMstDtoList;

	List<EmployeeCompOffLeaveMstEntity> employeeCompOffLeaveMstEntityList;

}
