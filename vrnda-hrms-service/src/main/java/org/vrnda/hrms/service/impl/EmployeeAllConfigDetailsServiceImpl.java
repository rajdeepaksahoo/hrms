package org.vrnda.hrms.service.impl;

import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.EmployeeAllConfigDetailsEntity;
import org.vrnda.hrms.repository.EmployeeAllConfigDetailsRepository;
import org.vrnda.hrms.repository.dto.CmnConfigurationsMstDTO;
import org.vrnda.hrms.repository.dto.EmployeeAllConfigDetailsDTO;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.EmployeeAllConfigDetailsService;
import org.vrnda.hrms.service.resultset.EmployeeAllConfigDetailsResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class EmployeeAllConfigDetailsServiceImpl  extends GenericServiceImpl<EmployeeAllConfigDetailsEntity, String> implements EmployeeAllConfigDetailsService {

	@Autowired
	EmployeeAllConfigDetailsRepository employeeAllConfigDetailsRepository;
	
	@Autowired
	CmnTableSeqService cmnTableSeqService;
	
	public EmployeeAllConfigDetailsServiceImpl(PagingAndSortingRepository<EmployeeAllConfigDetailsEntity, String> typeRepository) {
		super(typeRepository);
	}

	@Override
	public EmployeeAllConfigDetailsResultSet saveOrUpdateEmployeeAllConfigDetails (EmployeeAllConfigDetailsDTO employeeAllConfigdto, String loggedInUser)  {

		EmployeeAllConfigDetailsResultSet employeeAllConfigResultset = new EmployeeAllConfigDetailsResultSet();
		try {
			if (employeeAllConfigdto != null) {
				if (employeeAllConfigdto.getEmpAllConfigId() == null) {
					EmployeeAllConfigDetailsEntity employeeAllConfigEntity = new EmployeeAllConfigDetailsEntity();
					BeanUtils.copyProperties(employeeAllConfigdto, employeeAllConfigEntity);
					String allConfig = employeeAllConfigdto.getAllConfigsString();
					byte[] empallConfig = allConfig.getBytes();
					employeeAllConfigEntity.setAllConfigs(new SerialBlob(empallConfig));
					employeeAllConfigEntity.setEmpAllConfigId(cmnTableSeqService.getNextSequence(
							ApplicationConstants.EMPLOYEE_ALL_CONFIG_DETAILS, ApplicationConstants.EMP_ALL_CONFIG_ID));
					BaseUtils.setBaseData(employeeAllConfigEntity, loggedInUser);
					save(employeeAllConfigEntity);
					employeeAllConfigResultset.setMessageDescription("Record saved successfully.");
				} else if (employeeAllConfigdto != null && employeeAllConfigdto.getEmpAllConfigId() > 0) {
					EmployeeAllConfigDetailsEntity employeeAllConfigEntity = employeeAllConfigDetailsRepository
							.getEmployeeAllConfigDetailsByempAllConfigId(employeeAllConfigdto.getEmpAllConfigId());
					if (employeeAllConfigEntity != null) {
						BeanUtils.copyProperties(employeeAllConfigdto, employeeAllConfigEntity);
						String allConfig = employeeAllConfigdto.getAllConfigsString();
						byte[] empallConfig = allConfig.getBytes();
						employeeAllConfigEntity.setAllConfigs(new SerialBlob(empallConfig));
						BaseUtils.modifyBaseData(employeeAllConfigEntity, loggedInUser);
						save(employeeAllConfigEntity);
						employeeAllConfigResultset.setMessageDescription("Record Updated successfully.");
					}
				} else {
					employeeAllConfigResultset.setStatus(false);
					employeeAllConfigResultset.setMessage("Error");
					employeeAllConfigResultset.setMessageDescription("Unable to save Leave Configurations");
				}

			}
		} catch (Exception e) {
			employeeAllConfigResultset.setStatus(false);
			employeeAllConfigResultset.setMessage("Exception");
			employeeAllConfigResultset.setMessageDescription(e.getMessage());
		}
		return employeeAllConfigResultset;
	}

	@Override
	public EmployeeAllConfigDetailsResultSet deleteEmployeeAllConfigDetailsByEmpAllConfigId(Long empAllConfigId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmployeeAllConfigDetailsResultSet deleteEmployeeAllConfigDetails(
			List<CmnConfigurationsMstDTO> cmnConfigurationList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmployeeAllConfigDetailsResultSet getEmployeeAllConfigDetailsByEmpAllConfigId(Long empAllConfigId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmployeeAllConfigDetailsResultSet getEmployeeAllConfigDetailsByConfigurationIdAndConfigTypeLookupId(
			Long configurationId, Long configTypeLookupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmployeeAllConfigDetailsResultSet getEmployeeAllConfigDetailsByYearId(Long yearId) {
		EmployeeAllConfigDetailsResultSet empAllConfigRS = new EmployeeAllConfigDetailsResultSet();
		if (yearId != null) {
			try {
				List<EmployeeAllConfigDetailsEntity> employeeAllConfigDetailsEntityList = employeeAllConfigDetailsRepository
						.getAllEmployeesConfigsByYearId(yearId);
				if (employeeAllConfigDetailsEntityList != null && employeeAllConfigDetailsEntityList.size() > 0) {
					empAllConfigRS.setEmployeeAllConfigDetailsEntityList(employeeAllConfigDetailsEntityList);
				} else {
					empAllConfigRS.setStatus(false);
					empAllConfigRS.setMessage("No data avaialble.");
				}
			} catch (Exception e) {
				empAllConfigRS.setStatus(false);
				empAllConfigRS.setMessage("Exception");
				empAllConfigRS.setEmployeeAllConfigDetailsEntityList(null);
			}
		}
		return empAllConfigRS;
	}

	@Override
	public EmployeeAllConfigDetailsResultSet getEmployeeAllConfigDetailsByYearIdAndStatusLookupId(Long yearId,
			Long statusLookupId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public EmployeeAllConfigDetailsResultSet getEmployeeAllConfigDetailsByEmployeeId(Long employeeId) {
		EmployeeAllConfigDetailsResultSet employeeAllConfigDetailsResultSet =new EmployeeAllConfigDetailsResultSet();
		
		try {
			
			EmployeeAllConfigDetailsEntity employeeConfig=employeeAllConfigDetailsRepository.getEmployeeAllConfigDetailsByEmployeeId(employeeId);
			if(employeeConfig!=null) {
				
				employeeAllConfigDetailsResultSet.setEmployeeDetailsConfigEntity(employeeConfig);	
			}
			else {
				employeeAllConfigDetailsResultSet.setStatus(false);
				employeeAllConfigDetailsResultSet.setMessage("Failed");
				employeeAllConfigDetailsResultSet.setMessageDescription("Employee not existed");
			}
		} catch (Exception e) {
			employeeAllConfigDetailsResultSet.setStatus(false);
			employeeAllConfigDetailsResultSet.setMessage("Error");
			employeeAllConfigDetailsResultSet.setMessageDescription(e.getMessage());
		}
		return employeeAllConfigDetailsResultSet;
	}

	@Override
	public EmployeeAllConfigDetailsResultSet getEmployeeAllConfigDetailsByYearIdandEmployeeId(Long yearId,Long employeeId) {
		EmployeeAllConfigDetailsResultSet employeeAllConfigDetailsResultSet =new EmployeeAllConfigDetailsResultSet();
		try {
			EmployeeAllConfigDetailsEntity employeeConfig=employeeAllConfigDetailsRepository.getEmployeeAllConfigDetailsByYearIdandEmployeeId(yearId,employeeId);
			if(employeeConfig!=null) {
					EmployeeAllConfigDetailsDTO employeeAllConfigDetailsDTO = new EmployeeAllConfigDetailsDTO();
					BeanUtils.copyProperties(employeeConfig, employeeAllConfigDetailsDTO);
					if (employeeConfig.getAllConfigs() != null) {
						byte[] allconfig=employeeConfig.getAllConfigs().getBytes(1, (int) employeeConfig.getAllConfigs().length());
						employeeAllConfigDetailsDTO.setAllConfigsString(new String(allconfig));
					}
				employeeAllConfigDetailsResultSet.setEmployeeAllConfigDetailsDTO(employeeAllConfigDetailsDTO);	
			}
			else {
				employeeAllConfigDetailsResultSet.setStatus(false);
				employeeAllConfigDetailsResultSet.setMessage("Failed");
				employeeAllConfigDetailsResultSet.setMessageDescription("Employee not existed");
			}
		} catch (Exception e) {
			employeeAllConfigDetailsResultSet.setStatus(false);
			employeeAllConfigDetailsResultSet.setMessage("Error");
			employeeAllConfigDetailsResultSet.setMessageDescription(e.getMessage());
		}
		return employeeAllConfigDetailsResultSet;
	}
}
