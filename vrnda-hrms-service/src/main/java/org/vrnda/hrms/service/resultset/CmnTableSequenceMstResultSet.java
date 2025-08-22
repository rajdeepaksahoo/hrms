package org.vrnda.hrms.service.resultset;

import org.vrnda.hrms.entity.CmnTableSeqMstEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class CmnTableSequenceMstResultSet extends GenericResultSet {

	CmnTableSeqMstEntity cmnTableSeqMstEntity;

}
