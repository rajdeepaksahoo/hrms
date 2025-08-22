package org.vrnda.hrms.repository.dto;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class TasksAndAllocationsCommentsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long tasksAndAllocCommentId;

	private String taskId;

	private Blob taskComment;

	private String taskCommentBlobToString;

	private String createdBy;

	private Timestamp createdDate;

}
