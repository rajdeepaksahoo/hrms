package org.vrnda.hrms.service;

import java.util.List;

import org.vrnda.hrms.entity.CmnDepartmentsMstEntity;
import org.vrnda.hrms.repository.dto.CmnDepartmentsMstDTO;
import org.vrnda.hrms.service.resultset.DepartmentsResultSet;

public interface CmnDepartmentsMstService extends GenericService<CmnDepartmentsMstEntity, Long>{

	public DepartmentsResultSet saveOrUpdateDepartments(CmnDepartmentsMstDTO cmnDepartmentsMstDto, String loggedInUser);

	public DepartmentsResultSet deleteDepartmentByDepartmentId(Long departmentId);

	public DepartmentsResultSet deleteDepartmentsList(List<CmnDepartmentsMstDTO> cmnDepartmentsMstDtoList);

	public DepartmentsResultSet getAllDepartments();

	public DepartmentsResultSet getDepartmentByDepartmentId(Long departmentId);

	public DepartmentsResultSet getDepartmentsByStatusLookupId(Long statusLookupId);

	public boolean verifyDuplicateDepartmentName(CmnDepartmentsMstDTO cmnDepartmentsMstDto);

}
