package org.vrnda.hrms.service.resultset;

import java.util.List;
import java.util.Map;

import org.vrnda.hrms.repository.dto.LoggedInUserInformationDTO;
import org.vrnda.hrms.repository.dto.StatusLookupIdDTO;
import org.vrnda.hrms.repository.dto.UIMenusDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class AllUserDetailsResultSet extends GenericResultSet {

	List<UIMenusDTO> uiMenusDtoList;

	Map<String,String> rolesMap;

	LoggedInUserInformationDTO loggedInUserInformationDto;

	StatusLookupIdDTO statusLookupIdDto;

}
