package org.vrnda.hrms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.EmployeeSalaryDetailsEntity;
import org.vrnda.hrms.repository.EmployeeSalaryDetailsRepositry;
import org.vrnda.hrms.service.EmployeeSalaryDetailsService;

@Service
public class EmployeeSalaryDetailsServiceImpl extends GenericServiceImpl<EmployeeSalaryDetailsEntity, Long>
		implements EmployeeSalaryDetailsService {

	@Autowired
	private EmployeeSalaryDetailsRepositry employeeSalaryDetailsRepositry;

	public EmployeeSalaryDetailsServiceImpl(
			PagingAndSortingRepository<EmployeeSalaryDetailsEntity, Long> typeRepository) {
		super(typeRepository);

	}

}
