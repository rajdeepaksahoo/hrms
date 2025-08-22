package org.vrnda.hrms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.vrnda.hrms.service.EmployeeSalaryDetailsService;

@RestController
@CrossOrigin(origins = "*")
public class EmployeeSalaryDetailsController extends GenericController {

	@Autowired
	private EmployeeSalaryDetailsService employeeSalaryDetailsService;

}
