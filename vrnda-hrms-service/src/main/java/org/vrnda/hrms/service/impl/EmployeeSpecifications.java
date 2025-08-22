package org.vrnda.hrms.service.impl;

import java.util.Date;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.vrnda.hrms.entity.EmployeeDetailsEntity;

public class EmployeeSpecifications {

	public static Specification<EmployeeDetailsEntity> employeeIdEquals(Long employeeId) {
		return (root, query, criteriaBuilder) -> {
			if (employeeId == null)
				return null;
			return criteriaBuilder.equal(root.get("employeeId"), employeeId);
		};
	}

	public static Specification<EmployeeDetailsEntity> employeeLastNameContains(String employeeLastName) {
		return (root, query, criteriaBuilder) -> {
			if (employeeLastName == null)
				return null;
			return criteriaBuilder.like(criteriaBuilder.lower(root.get("employeeLastName")),
					"%" +employeeLastName.toLowerCase() + "%");
		};
	}

	public static Specification<EmployeeDetailsEntity> betweenDatesOfDOJ(Date startDate, Date endDate) {
		return (root, query, criteriaBuilder) -> {
			Predicate startDatePredicate = null;
			Predicate endDatePredicate = null;
			if (startDate != null) {
				startDatePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("dateOfJoining"), startDate);
			}
			if (endDate != null) {
				endDatePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("dateOfJoining"), endDate);
			}

			if (startDatePredicate != null && endDatePredicate == null) {
				return criteriaBuilder.and(startDatePredicate);
			} else if (startDatePredicate == null && endDatePredicate != null) {
				return criteriaBuilder.and(endDatePredicate);
			} else if (startDatePredicate != null && endDatePredicate != null) {
				return criteriaBuilder.and(startDatePredicate, endDatePredicate);
			} else {
				return null;
			}
		};
	}
	
	public static Specification<EmployeeDetailsEntity> managerIdEquals(Long managerId) {
		return (root, query, criteriaBuilder) -> {
			if (managerId == null)
				return null;
			return criteriaBuilder.equal(root.get("managerId"), managerId);
		};
	}
	
	public static Specification<EmployeeDetailsEntity> employeeIdStatus(Long status) {
	return (root, query, criteriaBuilder) -> {
		if (status == null)
			return null;
		return criteriaBuilder.equal(root.get("statusLookupId"), status);
	};
}
}
