package org.vrnda.hrms.entity;

import java.sql.Blob;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TASKS_AND_ALLOCATIONS_COMMENTS")
public class TasksAndAllocationsCommentsEntity {

	@Id
	@Column(name = "TASKS_AND_ALLOC_COMMENT_ID")
	private Long tasksAndAllocCommentId;

	@Column(name = "TASK_ID")
	private String taskId;

	@Column(name = "TASK_COMMENT")
	private Blob taskComment;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

}
