package org.vrnda.hrms.service.resultset;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class UploadEmployeesDataResultSet  extends GenericResultSet {

}
