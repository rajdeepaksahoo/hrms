package org.vrnda.hrms.service;

import java.util.List;

import org.vrnda.hrms.entity.CmnSalarySlabLevelConfigDetailsEntity;
import org.vrnda.hrms.repository.dto.CmnSalarySlabLevelConfigDetailsDTO;
import org.vrnda.hrms.service.resultset.CmnSalarySlabLevelConfigDetailsResultSet;

public interface CmnSalarySlabLevelConfigDetailsService
		extends GenericService<CmnSalarySlabLevelConfigDetailsEntity, Long> {

	public CmnSalarySlabLevelConfigDetailsResultSet getAllsalarySlabDetails(Long configurationId);

	public CmnSalarySlabLevelConfigDetailsResultSet saveOrUpdateSalarySlab(CmnSalarySlabLevelConfigDetailsDTO cmnSalarySlabLevelDto,String loggedInUser);

	public CmnSalarySlabLevelConfigDetailsResultSet deleteSalarySlabBycmnSalarySlabLevelConfigDetlsId(Long cmnSalarySlabLevelConfigDetlsId);

	public CmnSalarySlabLevelConfigDetailsResultSet deleteSalarySlabs(List<CmnSalarySlabLevelConfigDetailsDTO> cmnSalarySlabLevelDto);
	
}
