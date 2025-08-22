package org.vrnda.hrms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "APP_USERS_MST")
public class AppUsersMstEntity extends EntityBaseModel{

	@Id
	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "USER_NAME")
	private String username;

	@Column(name = "APP_ROLE_ID")
	private Long appRoleId;

	@Column(name = "EMPLOYEE_ID")
	private Long employeeId;

	@Column(name = "ACTIVATE_FLAG")
	private Long activateFlag;

	@Column(name = "ALWAYS_LOGIN")
	private char alwaysLogin;

	@Column(name = "FIRSTLOGIN")
	private char firstLogin;

	@Column(name = "INVALID_LOGIN_CNT")
	private Integer invalidLoginCount;

	@Column(name = "IP_ADDRESS")
	private String ipAddress;

	@Column(name = "IP_LOGIN")
	private String ipLogin;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "PWDCHANGED_DATE")
	private Date passwordChangedDate;

	@Column(name = "SECRET_QUE_LOOKUP_ID")
	private Long secretQuestionId;

	@Column(name = "SECRET_QUE_OTHER")
	private String secretQuestionOther;

	@Column(name = "SECRET_ANSWER")
	private String secretAnswer;

	@Column(name = "SYSTEM_CONFIG_FLAG")
	private String  systemConfigFlag;

}
