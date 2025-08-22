package org.vrnda.hrms.service.resultset;

import java.util.List;

import org.vrnda.hrms.entity.AppUsersMstEntity;
import org.vrnda.hrms.repository.dto.AppUsersMstDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class AppUsersMstResultSet extends GenericResultSet{

	AppUsersMstEntity appUsersMstEntity;

	List<AppUsersMstDTO> appUsersMstDtoList;

}
