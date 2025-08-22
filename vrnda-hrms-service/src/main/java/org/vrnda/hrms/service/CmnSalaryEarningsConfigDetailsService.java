package org.vrnda.hrms.service;

import java.util.List;

import org.vrnda.hrms.repository.dto.CmnSalaryEarningsConfigDetailsDTO;
import org.vrnda.hrms.service.resultset.CmnSalaryEarningsConfigDetailsResultSet;

public interface CmnSalaryEarningsConfigDetailsService {

	public CmnSalaryEarningsConfigDetailsResultSet getSalaryEarningsConfigDetailsByConfigId(Long configurationId);

	public CmnSalaryEarningsConfigDetailsResultSet saveOrUpdateSalaryEarningsConfigDetails(
			List<CmnSalaryEarningsConfigDetailsDTO> cmnSalaryEarningsConfigDetailsDTO, String loginUser);
	
	public CmnSalaryEarningsConfigDetailsResultSet deleteCmnSalaryEarningsConfigDetailsByConfigId(Long configurationId);

}
