package org.vrnda.hrms.service.resultset;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class GenericResultSet {

	private Boolean status = true;

	private String message = "Success";

	private String messageDescription = "Success";

	int failureCount;

	int successCount;

}