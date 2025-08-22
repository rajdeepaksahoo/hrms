package org.vrnda.hrms.service;

import java.util.List;

import org.vrnda.hrms.entity.EmployeeAllConfigDetailsEntity;
import org.vrnda.hrms.repository.dto.CmnConfigurationsMstDTO;
import org.vrnda.hrms.repository.dto.EmployeeAllConfigDetailsDTO;
import org.vrnda.hrms.service.resultset.EmployeeAllConfigDetailsResultSet;


public interface EmployeeAllConfigDetailsService extends GenericService<EmployeeAllConfigDetailsEntity, String> {

	public EmployeeAllConfigDetailsResultSet saveOrUpdateEmployeeAllConfigDetails(EmployeeAllConfigDetailsDTO employeeAllConfigdto, String loggedInUser) ;
	
	public EmployeeAllConfigDetailsResultSet deleteEmployeeAllConfigDetailsByEmpAllConfigId(Long empAllConfigId);
	
	public EmployeeAllConfigDetailsResultSet deleteEmployeeAllConfigDetails(List<CmnConfigurationsMstDTO> cmnConfigurationList);
	
	public EmployeeAllConfigDetailsResultSet getEmployeeAllConfigDetailsByEmpAllConfigId(Long empAllConfigId);
	
	public EmployeeAllConfigDetailsResultSet getEmployeeAllConfigDetailsByConfigurationIdAndConfigTypeLookupId(Long configurationId, Long configTypeLookupId);
	
	public EmployeeAllConfigDetailsResultSet getEmployeeAllConfigDetailsByYearId(Long yearId);
	
	public EmployeeAllConfigDetailsResultSet getEmployeeAllConfigDetailsByYearIdAndStatusLookupId(Long yearId, Long statusLookupId);
	
	public EmployeeAllConfigDetailsResultSet getEmployeeAllConfigDetailsByEmployeeId(Long employeeId);
	
	public EmployeeAllConfigDetailsResultSet getEmployeeAllConfigDetailsByYearIdandEmployeeId(Long yearId,Long employeeId);
}
