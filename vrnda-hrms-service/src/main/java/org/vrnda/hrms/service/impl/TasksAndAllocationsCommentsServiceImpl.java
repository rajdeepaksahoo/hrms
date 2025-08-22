package org.vrnda.hrms.service.impl;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.vrnda.hrms.entity.TasksAndAllocationsCommentsEntity;
import org.vrnda.hrms.repository.TasksAndAllocationsCommentsRepository;
import org.vrnda.hrms.repository.dto.TasksAndAllocationsCommentsDTO;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.TasksAndAllocationsCommentsService;
import org.vrnda.hrms.service.resultset.TasksAndAllocationsCommentsResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Slf4j
@Service
public class TasksAndAllocationsCommentsServiceImpl extends GenericServiceImpl<TasksAndAllocationsCommentsEntity, Long>
		implements TasksAndAllocationsCommentsService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private CmnTableSeqService cmnTableSeqService;

	@Autowired
	private TasksAndAllocationsCommentsRepository tasksAndAllocationsCommentsRepository;

	public TasksAndAllocationsCommentsServiceImpl(
			PagingAndSortingRepository<TasksAndAllocationsCommentsEntity, Long> typeRepository) {
		super(typeRepository);
		// TODO Auto-generated constructor stub
	}

	@Override
	public TasksAndAllocationsCommentsResultSet createComment(MultipartFile commentBlob, String taskId,
			String loggedInUser) {
		TasksAndAllocationsCommentsResultSet tasksAndAllocationsCommentsResultSet = new TasksAndAllocationsCommentsResultSet();
		try {
			TasksAndAllocationsCommentsEntity tasksAndAllocationsCommentsEntity = new TasksAndAllocationsCommentsEntity();
			Blob blob = entityManager.unwrap(Session.class).getLobHelper().createBlob(commentBlob.getInputStream(),
					commentBlob.getSize());
			tasksAndAllocationsCommentsEntity.setTasksAndAllocCommentId(
					cmnTableSeqService.getNextSequence(ApplicationConstants.TASKS_AND_ALLOCATIONS_COMMENTS,
							ApplicationConstants.TASKS_AND_ALLOC_COMMENT_ID));
			tasksAndAllocationsCommentsEntity.setTaskComment(blob);
			tasksAndAllocationsCommentsEntity.setTaskId(taskId);
			tasksAndAllocationsCommentsEntity.setCreatedBy(loggedInUser);
			tasksAndAllocationsCommentsEntity.setCreatedDate(BaseUtils.getCurrentTime());
			save(tasksAndAllocationsCommentsEntity);

		} catch (Exception e) {
			tasksAndAllocationsCommentsResultSet.setStatus(false);
			tasksAndAllocationsCommentsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			tasksAndAllocationsCommentsResultSet.setMessageDescription("Unable to create comment");
			log.error("Exception in TasksAndAllocationsCommentsServiceImpl.createComment");
		}
		return tasksAndAllocationsCommentsResultSet;
	}

	@Override
	public TasksAndAllocationsCommentsResultSet getCommentsByTaskId(String taskId) {
		TasksAndAllocationsCommentsResultSet tasksAndAllocationsCommentsResultSet = new TasksAndAllocationsCommentsResultSet();
		try {
			List<TasksAndAllocationsCommentsEntity> tasksAndAllocationsCommentsEntityList = tasksAndAllocationsCommentsRepository
					.getCommentsByTaskId(taskId);
			if (tasksAndAllocationsCommentsEntityList != null) {
				List<TasksAndAllocationsCommentsDTO> TasksAndAllocationsCommentsDTOList = new ArrayList<TasksAndAllocationsCommentsDTO>();
				tasksAndAllocationsCommentsEntityList.forEach(e -> {
					TasksAndAllocationsCommentsDTO tasksAndAllocationsCommentsDTO = new TasksAndAllocationsCommentsDTO();
					BeanUtils.copyProperties(e, tasksAndAllocationsCommentsDTO);
//					new String(Base64.getEncoder().encode(tasksAndAllocationsCommentsDTO.getTaskComment().getBytes());
					try {
						tasksAndAllocationsCommentsDTO
								.setTaskCommentBlobToString(new String(tasksAndAllocationsCommentsDTO.getTaskComment()
										.getBytes(1, (int) tasksAndAllocationsCommentsDTO.getTaskComment().length())));
						tasksAndAllocationsCommentsDTO.setTaskComment(null);
					} catch (Exception e1) {
						tasksAndAllocationsCommentsDTO.setTaskComment(null);
						e1.printStackTrace();
					}
					TasksAndAllocationsCommentsDTOList.add(tasksAndAllocationsCommentsDTO);
				});
				tasksAndAllocationsCommentsResultSet
						.setTasksAndAllocationsCommentsDTOList(TasksAndAllocationsCommentsDTOList);
			}
		} catch (Exception e) {
			tasksAndAllocationsCommentsResultSet.setStatus(false);
			tasksAndAllocationsCommentsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			tasksAndAllocationsCommentsResultSet.setMessageDescription("Unable to load comments");
			log.error("Exception in TasksAndAllocationsCommentsServiceImpl.getCommentsByTaskId");
		}
		return tasksAndAllocationsCommentsResultSet;
	}

}
