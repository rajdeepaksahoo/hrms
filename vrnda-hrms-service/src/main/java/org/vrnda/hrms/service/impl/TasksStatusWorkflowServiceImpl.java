package org.vrnda.hrms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.TasksStatusWorkflowEntity;
import org.vrnda.hrms.entity.TasksStatusWorkflowEntityPk;
import org.vrnda.hrms.repository.TasksStatusWorkflowRepository;
import org.vrnda.hrms.repository.dto.TasksStatusWorkflowDTO;
import org.vrnda.hrms.service.TasksStatusWorkflowService;
import org.vrnda.hrms.service.resultset.TasksStatusWorkflowResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Slf4j
public class TasksStatusWorkflowServiceImpl
		extends GenericServiceImpl<TasksStatusWorkflowEntity, TasksStatusWorkflowEntityPk>
		implements TasksStatusWorkflowService {

	@Autowired
	private TasksStatusWorkflowRepository tasksStatusWorkflowRepository;

	public TasksStatusWorkflowServiceImpl(
			PagingAndSortingRepository<TasksStatusWorkflowEntity, TasksStatusWorkflowEntityPk> typeRepository) {
		super(typeRepository);
		// TODO Auto-generated constructor stub
	}

	@Override
	public TasksStatusWorkflowResultSet getAllTasksStatusWorkFlow() {
		TasksStatusWorkflowResultSet tasksStatusWorkflowResultSet = new TasksStatusWorkflowResultSet();
		try {
			List<Object[]> taskFlowResultObject = tasksStatusWorkflowRepository.getAllTasksStatusWorkFlow();
			List<TasksStatusWorkflowDTO> tasksStatusWorkflowDTOList = new ArrayList<TasksStatusWorkflowDTO>();
			for (Object[] object : taskFlowResultObject) {
				TasksStatusWorkflowEntity tasksStatusWorkflowEntity = (TasksStatusWorkflowEntity) object[0];
				TasksStatusWorkflowDTO tasksStatusWorkflowDTO = new TasksStatusWorkflowDTO();
				BeanUtils.copyProperties(tasksStatusWorkflowEntity, tasksStatusWorkflowDTO);
				tasksStatusWorkflowDTO.setTaskTypeName((String) object[1]);
				tasksStatusWorkflowDTO.setTaskStatusName((String) object[2]);
				tasksStatusWorkflowDTO.setWorkFlowBlobToString(new String(tasksStatusWorkflowDTO.getWorkFlow()
						.getBytes(1, (int) tasksStatusWorkflowDTO.getWorkFlow().length())));

				HashMap<String, Object> workFlowMap = new ObjectMapper()
						.readValue(tasksStatusWorkflowDTO.getWorkFlowBlobToString(), HashMap.class);
				tasksStatusWorkflowDTO.setWorkFlowMap(workFlowMap);
				tasksStatusWorkflowDTO.setWorkFlow(null);
				tasksStatusWorkflowDTOList.add(tasksStatusWorkflowDTO);

			}
			tasksStatusWorkflowResultSet.setTasksStatusWorkflowList(tasksStatusWorkflowDTOList);
		} catch (Exception e) {
			tasksStatusWorkflowResultSet.setStatus(false);
			tasksStatusWorkflowResultSet.setMessage(ApplicationConstants.FAILED);
			tasksStatusWorkflowResultSet.setMessageDescription("Unable to load Task Status WorkFlow");
			log.error("Exception in TasksStatusWorkflowServiceImpl.getAllTasksStatusWorkFlow()");
		}
		return tasksStatusWorkflowResultSet;
	}

}
