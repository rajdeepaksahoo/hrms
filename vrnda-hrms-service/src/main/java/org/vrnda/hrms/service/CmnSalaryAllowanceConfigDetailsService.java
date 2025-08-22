package org.vrnda.hrms.service;

import java.util.List;
import org.vrnda.hrms.entity.CmnSalaryAllowanceConfigDetailsEntity;
import org.vrnda.hrms.repository.dto.CmnSalaryAllowanceConfigDetailsDTO;
import org.vrnda.hrms.service.resultset.CmnSalaryAllowanceConfigDetailResultSet;

public interface CmnSalaryAllowanceConfigDetailsService
		extends GenericService<CmnSalaryAllowanceConfigDetailsEntity, Long> {

	public CmnSalaryAllowanceConfigDetailResultSet getAllsalaryAllowanceDetails(Long configurationId);

	public CmnSalaryAllowanceConfigDetailResultSet saveOrUpdatesalaryAllowances(
			CmnSalaryAllowanceConfigDetailsDTO cmnSalaryAllowancDto, String loggedInUser);

	public CmnSalaryAllowanceConfigDetailResultSet deleteSalarySlabBycmnSalaryAllowanceConfigDetlsId(
			Long cmnSalaryAllowanceConfigDetlsId);

	public CmnSalaryAllowanceConfigDetailResultSet deleteSalarySlabsAllowance(
			List<CmnSalaryAllowanceConfigDetailsDTO> cmnSalaryAllowancDto);
	
	public CmnSalaryAllowanceConfigDetailResultSet saveOrUpdateAllowances(List<CmnSalaryAllowanceConfigDetailsDTO> cmnSalaryAllowancDto, String loggedInUser);
	
	public CmnSalaryAllowanceConfigDetailResultSet getAllsalaryAllowanceDetailsBycnfgIdAndLevelId(Long configurationId,Long cmnSalarySlabLevelConfigDetlsId);

}
