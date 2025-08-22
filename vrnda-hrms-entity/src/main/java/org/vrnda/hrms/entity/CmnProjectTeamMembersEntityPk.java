package org.vrnda.hrms.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class CmnProjectTeamMembersEntityPk implements Serializable {

	private Long projectId;

	private Long employeeId;

	private Long teamId;

}
