package org.vrnda.hrms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.EmployeeContactPersonsEntity;
import org.vrnda.hrms.repository.EmployeeContactPersonsRepository;
import org.vrnda.hrms.repository.dto.EmployeeContactPersonsDTO;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.EmployeeContactPersonsService;
import org.vrnda.hrms.service.resultset.EmployeeContactPersonsResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class EmployeeContactPersonsServiceImpl extends GenericServiceImpl<EmployeeContactPersonsEntity, Long>
		implements EmployeeContactPersonsService {

	@Autowired
	private CmnTableSeqService cmnTableSeqService;

	@Autowired
	private EmployeeContactPersonsRepository employeeContactPersonsRepository;

	public EmployeeContactPersonsServiceImpl(
			PagingAndSortingRepository<EmployeeContactPersonsEntity, Long> typeRepository) {
		super(typeRepository);
		// TODO Auto-generated constructor stub
	}

	@Override
	public EmployeeContactPersonsResultSet getEmployeeContactPersonsByPersonId(Long employeePrsnId) {
		EmployeeContactPersonsResultSet employeeContactPersonsResultSet = new EmployeeContactPersonsResultSet();
		try {
			EmployeeContactPersonsEntity employeeContactPersonsEntity = employeeContactPersonsRepository
					.getEmployeeContactPersonByPersonId(employeePrsnId);
			List<EmployeeContactPersonsEntity> employeeContactPersonsEntityList = new ArrayList<EmployeeContactPersonsEntity>();
			if (employeeContactPersonsEntity != null) {
				employeeContactPersonsEntityList.add(employeeContactPersonsEntity);
				employeeContactPersonsResultSet.setEmployeeContactPersonsEntityList(employeeContactPersonsEntityList);
				employeeContactPersonsResultSet.setEmployeeContactPersonsEntity(employeeContactPersonsEntity);
			} else {
				employeeContactPersonsResultSet.setStatus(false);
				employeeContactPersonsResultSet.setMessage("Failes");
				employeeContactPersonsResultSet.setMessageDescription("Employee contact ID doesn't exists");
			}
		} catch (Exception e) {
			employeeContactPersonsResultSet.setStatus(false);
			employeeContactPersonsResultSet.setMessage("Exception");
			employeeContactPersonsResultSet.setMessageDescription(e.getMessage());
		}
		return employeeContactPersonsResultSet;
	}

	@Override
	public EmployeeContactPersonsResultSet saveOrUpdateEmployeeContactPersons(
			EmployeeContactPersonsDTO employeeContactPersonsDto, String loggedInUser) {

		EmployeeContactPersonsResultSet employeeContactPersonsResultSet = new EmployeeContactPersonsResultSet();

		try {

			EmployeeContactPersonsEntity employeeContactPersonsEntity = null;
			if (employeeContactPersonsDto != null) {
				if (employeeContactPersonsDto.getEmployeeCnctPrsnId() == null) {
					employeeContactPersonsEntity = new EmployeeContactPersonsEntity();
					employeeContactPersonsDto.setEmployeeCnctPrsnId(cmnTableSeqService.getNextSequence(
							ApplicationConstants.EMPLOYEE_CONTACT_PERSONS, ApplicationConstants.EMPLOYEE_CNCT_PRSN_ID));
					BeanUtils.copyProperties(employeeContactPersonsDto, employeeContactPersonsEntity);
					BaseUtils.setBaseData(employeeContactPersonsEntity, loggedInUser);
					save(employeeContactPersonsEntity);
					employeeContactPersonsResultSet.setEmployeeContactPersonsEntity(employeeContactPersonsEntity);
				} else {
					employeeContactPersonsEntity = employeeContactPersonsRepository
							.getEmployeeContactPersonByPersonId(employeeContactPersonsDto.getEmployeeCnctPrsnId());
					BeanUtils.copyProperties(employeeContactPersonsDto, employeeContactPersonsEntity);
					BaseUtils.modifyBaseData(employeeContactPersonsEntity, loggedInUser);
					save(employeeContactPersonsEntity);
					employeeContactPersonsResultSet.setEmployeeContactPersonsEntity(employeeContactPersonsEntity);
				}
			}

		} catch (Exception e) {
			employeeContactPersonsResultSet.setStatus(false);
			employeeContactPersonsResultSet.setMessage("Exception");
			employeeContactPersonsResultSet.setMessageDescription(e.getMessage());
		}

		return employeeContactPersonsResultSet;
	}

}
