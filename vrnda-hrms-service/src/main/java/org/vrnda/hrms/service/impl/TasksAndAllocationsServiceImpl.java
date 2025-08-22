package org.vrnda.hrms.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vrnda.hrms.entity.CmnCompanyRolesMstEntity;
import org.vrnda.hrms.entity.CmnProjectTeamMembersEntity;
import org.vrnda.hrms.entity.CmnProjectsMstEntity;
import org.vrnda.hrms.entity.TasksAndAllocationsEntity;
import org.vrnda.hrms.repository.CmnCompanyRolesMstRepository;
import org.vrnda.hrms.repository.CmnProjectTeamMembersRepository;
import org.vrnda.hrms.repository.CmnProjectsMstRepository;
import org.vrnda.hrms.repository.TasksAndAllocationsRepository;
import org.vrnda.hrms.repository.dto.CmnLookupMstDTO;
import org.vrnda.hrms.repository.dto.CmnProjectTeamMembersDTO;
import org.vrnda.hrms.repository.dto.EmployeeDetailsDTO;
import org.vrnda.hrms.repository.dto.TasksAndAllocationsDTO;
import org.vrnda.hrms.service.CmnLookupMstService;
import org.vrnda.hrms.service.CmnProjectTeamMembersService;
import org.vrnda.hrms.service.CmnProjectsMstService;
import org.vrnda.hrms.service.TasksAndAllocationsService;
import org.vrnda.hrms.service.resultset.CmnLookupMstResultSet;
import org.vrnda.hrms.service.resultset.CmnProjectTeamMembersResultSet;
import org.vrnda.hrms.service.resultset.CmnProjectsMstResultSet;
import org.vrnda.hrms.service.resultset.TasksAndAllocationsResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueField;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.api.domain.Version;

@Slf4j
@Service
public class TasksAndAllocationsServiceImpl extends GenericServiceImpl<TasksAndAllocationsEntity, Long>
		implements TasksAndAllocationsService {

	@Autowired
	private CmnProjectsMstRepository cmnProjectsMstRepository;

	@Autowired
	private CmnProjectTeamMembersRepository cmnProjectMembersRepository;

	public TasksAndAllocationsServiceImpl(PagingAndSortingRepository<TasksAndAllocationsEntity, Long> typeRepository) {
		super(typeRepository);
		// TODO Auto-generated constructor stub
	}

	private final static String JQLQUERY = "project = \"S4\" and assignee in (61095553e6e6f80071fc2132, 604a304879f9a9007005e08e) ORDER BY created DESC";

	@Autowired
	private TasksAndAllocationsRepository tasksAndAllocationsRepository;

	@Autowired
	private CmnLookupMstService cmnLookupMstService;

	@Autowired
	private CmnProjectsMstService cmnProjectsMstService;

	@Autowired
	private CmnProjectTeamMembersService cmnProjectTeamMembersService;

	@Autowired
	private CmnCompanyRolesMstRepository cmnCompanyRolesMstRepository;

	@Autowired
	private RestTemplate restTemplate;

	private static Map<String, Long> TASK_TYPE_MAP = new HashMap<String, Long>();
	private static Map<String, Long> PRIORITY_TYPE_MAP = new HashMap<String, Long>();
	private static Map<String, Long> TASK_STATUS_MAP = new HashMap<String, Long>();

	private void getTaskConfigurationMap() {
		try {
			CmnLookupMstResultSet cmnLookupMstTaskTypeResultSet = cmnLookupMstService
					.getLookupIdAndNameListByParentLookupName(ApplicationConstants.TASK_TYPE);

			CmnLookupMstResultSet cmnLookupMstPriorityTypeResultSet = cmnLookupMstService
					.getLookupIdAndNameListByParentLookupName(ApplicationConstants.PRIORITY);

			CmnLookupMstResultSet cmnLookupMstTaskStatusResultSet = cmnLookupMstService
					.getLookupIdAndNameListByParentLookupName(ApplicationConstants.TASK_STATUS);

			if (cmnLookupMstTaskTypeResultSet.getCmnLookupMstDtoList() != null) {
				TASK_TYPE_MAP = cmnLookupMstTaskTypeResultSet.getCmnLookupMstDtoList().stream()
						.collect(Collectors.toMap(CmnLookupMstDTO::getLookupName, CmnLookupMstDTO::getLookupId));
			}

			if (cmnLookupMstPriorityTypeResultSet.getCmnLookupMstDtoList() != null) {
				PRIORITY_TYPE_MAP = cmnLookupMstPriorityTypeResultSet.getCmnLookupMstDtoList().stream()
						.collect(Collectors.toMap(CmnLookupMstDTO::getLookupName, CmnLookupMstDTO::getLookupId));
			}

			if (cmnLookupMstTaskStatusResultSet.getCmnLookupMstDtoList() != null) {
				TASK_STATUS_MAP = cmnLookupMstTaskStatusResultSet.getCmnLookupMstDtoList().stream()
						.collect(Collectors.toMap(CmnLookupMstDTO::getLookupName, CmnLookupMstDTO::getLookupId));
			}
		} catch (Exception e) {
			log.error("Exception occured in " + this.getClass().getName() + " .getTaskConfigurationMap() ");
		}
	}

	@Override
	public TasksAndAllocationsResultSet loadIssuesFromJiraByProjectName(String projectName) {
		TasksAndAllocationsResultSet tasksAndAllocationsResultSet = new TasksAndAllocationsResultSet();
		try {
			CmnProjectsMstEntity cmnProjectsMstEntity = cmnProjectsMstRepository
					.getAllProjectsByProjectName(projectName);
			if (cmnProjectsMstEntity != null) {
				MyJiraClient myJiraClient = new MyJiraClient("sahoorajdeepak3@gmail.com",
						"rajdeepak12#",
						"https://home.atlassian.com/");
//				int maxResult = 50;
//				int startAt = 0;
//				SearchResult searchResult = myJiraClient.getIssueByJQL(JQLQUERY, maxResult, startAt);
//				Iterable<Issue> issues = searchResult.getIssues();
				List<TasksAndAllocationsEntity> tasksAndAllocationsEntityList = new ArrayList<TasksAndAllocationsEntity>();
				getTaskConfigurationMap();
				List<TasksAndAllocationsEntity> taskAlloctionsList = tasksAndAllocationsRepository
						.getAllTasksAndAllocations();

//				for (Issue issue : issues) {
//					TasksAndAllocationsEntity tasksAndAllocationsDetails = validateIsuue(issue, taskAlloctionsList,
//							cmnProjectsMstEntity);
//					if (tasksAndAllocationsDetails != null) {
//						tasksAndAllocationsEntityList
//								.add(validateIsuue(issue, taskAlloctionsList, cmnProjectsMstEntity));
//					}
//				}
				tasksAndAllocationsEntityList = loadJiraIssues(tasksAndAllocationsEntityList, myJiraClient,
						taskAlloctionsList, cmnProjectsMstEntity, 0);
				saveAll(tasksAndAllocationsEntityList);
			} else {
				tasksAndAllocationsResultSet.setStatus(false);
				tasksAndAllocationsResultSet.setMessage(ApplicationConstants.FAILED);
				tasksAndAllocationsResultSet
						.setMessageDescription("Unable to Find scrum Master for the project " + projectName);
			}
		} catch (Exception e) {
			tasksAndAllocationsResultSet.setStatus(false);
			tasksAndAllocationsResultSet.setMessage(ApplicationConstants.FAILED);
			tasksAndAllocationsResultSet.setMessageDescription("Unable to load issues from JIRA");
			log.error("Exception in TasksAndAllocationsServiceImpl.loadIssuesFromJiraByProjectName");
		}
		return tasksAndAllocationsResultSet;

	}

	private List<TasksAndAllocationsEntity> loadJiraIssues(
			List<TasksAndAllocationsEntity> tasksAndAllocationsEntityList, MyJiraClient myJiraClient,
			List<TasksAndAllocationsEntity> taskAlloctionsList, CmnProjectsMstEntity cmnProjectsMstEntity, int startAt)
			throws Exception {
		int maxResult = 50;
		SearchResult searchResult = myJiraClient.getIssueByJQL(JQLQUERY, maxResult, startAt);
		Iterable<Issue> issues = searchResult.getIssues();
		int count = 0;
		for (Issue issue : issues) {
			TasksAndAllocationsEntity tasksAndAllocationsDetails = validateIsuue(issue, taskAlloctionsList,
					cmnProjectsMstEntity);
			if (tasksAndAllocationsDetails != null) {
				tasksAndAllocationsEntityList.add(tasksAndAllocationsDetails);
			}
			count++;
		}

		if (count == 50) {
			startAt = startAt + 50;
			loadJiraIssues(tasksAndAllocationsEntityList, myJiraClient, taskAlloctionsList, cmnProjectsMstEntity,
					startAt);
		}

		return tasksAndAllocationsEntityList;
	}

	private TasksAndAllocationsEntity validateIsuue(Issue issue, List<TasksAndAllocationsEntity> taskAlloctionsList,
													CmnProjectsMstEntity cmnProjectsMstEntity) {
		TasksAndAllocationsEntity tasksAndAllocationsEntityObj = new TasksAndAllocationsEntity();
		Long assignee = cmnProjectsMstEntity.getProjectHead();
		List<TasksAndAllocationsEntity> tempEntity = taskAlloctionsList.stream()
				.filter(e -> e.getTaskId().equals(issue.getKey())).collect(Collectors.toList());
		if (tempEntity != null && tempEntity.size() > 0) {
			tasksAndAllocationsEntityObj = tempEntity.get(0);
			Long deliveredId = TASK_STATUS_MAP.get(ApplicationConstants.DELIVERED);
			Long closedId = TASK_STATUS_MAP.get(ApplicationConstants.CLOSED);
			Timestamp issueUpdateDate = new Timestamp(issue.getUpdateDate().toDate().getTime());
			if (issueUpdateDate != null) {
				LocalDateTime currentDateTime = LocalDateTime.now();
				LocalDateTime updateDateTime = issueUpdateDate.toInstant().atZone(ZoneId.systemDefault())
						.toLocalDateTime();
				long hoursDifference = ChronoUnit.HOURS.between(updateDateTime, currentDateTime);
				if (hoursDifference > 24) {
					return null;
				}
			}
			if (tasksAndAllocationsEntityObj.getCurrentStatus() != null
					&& (tasksAndAllocationsEntityObj.getCurrentStatus() == deliveredId
					|| tasksAndAllocationsEntityObj.getCurrentStatus() == closedId)) {
				if (issue.getStatus() != null && issue.getStatus().getName() != null) {
					if (issue.getStatus().getName().equalsIgnoreCase("Reopened")) {
						tasksAndAllocationsEntityObj
								.setCurrentStatus(TASK_STATUS_MAP.get(ApplicationConstants.RE_OPEN));
						assignee = tasksAndAllocationsEntityObj.getDeveloper();
					} else if (issue.getStatus().getName().equalsIgnoreCase("Closed")) {
						tasksAndAllocationsEntityObj.setCurrentStatus(closedId);
					}
				}
			}
			setIssueDetails(issue, tasksAndAllocationsEntityObj);
			tasksAndAllocationsEntityObj.setUpdatedBy("hrms.admin@vrnda.com");
			tasksAndAllocationsEntityObj.setUpdatedDate(BaseUtils.getCurrentTime());

		} else if (issue.getStatus() != null && issue.getStatus().getName() != null
				&& !issue.getStatus().getName().equalsIgnoreCase("Closed")) {
			tasksAndAllocationsEntityObj.setTaskId(issue.getKey());
			tasksAndAllocationsEntityObj.setCurrentStatus(TASK_STATUS_MAP.get(ApplicationConstants.BACKLOG));
			setIssueDetails(issue, tasksAndAllocationsEntityObj);
			tasksAndAllocationsEntityObj.setProjectId(cmnProjectsMstEntity.getProjectId());
			tasksAndAllocationsEntityObj.setAssignee(assignee);
			tasksAndAllocationsEntityObj.setScrumMaster(cmnProjectsMstEntity.getProjectHead());
			tasksAndAllocationsEntityObj.setCreatedDate(BaseUtils.getCurrentTime());
			tasksAndAllocationsEntityObj.setCreatedBy("hrms.admin@vrnda.com");
		} else {
			return null;
		}

		return tasksAndAllocationsEntityObj;
	}

	private void setIssueDetails(Issue issue, TasksAndAllocationsEntity tasksAndAllocationsEntityObj) {
		Long taskType = TASK_TYPE_MAP.get(issue.getIssueType().getName().toUpperCase());
		taskType = taskType != null ? taskType : TASK_TYPE_MAP.get(ApplicationConstants.BUG);
		tasksAndAllocationsEntityObj.setTaskType(taskType);
		Long priorityType = null;
		if (issue.getPriority() != null && issue.getPriority().getName() != null) {
			priorityType = PRIORITY_TYPE_MAP.get(issue.getPriority().getName().toUpperCase());
		}
		if (priorityType == null) {
			priorityType = PRIORITY_TYPE_MAP.get(ApplicationConstants.HIGH);
		}
		tasksAndAllocationsEntityObj.setPriority(priorityType);
		tasksAndAllocationsEntityObj.setTaskDescription(issue.getSummary());

		tasksAndAllocationsEntityObj.setExternalAssignee(issue.getAssignee().getDisplayName());
		tasksAndAllocationsEntityObj.setExternalStatus(issue.getStatus().getName());
		tasksAndAllocationsEntityObj.setReporter(issue.getReporter().getDisplayName());
		Iterable<Version> fixVersions = issue.getFixVersions();
		String sprint = null;
		if (fixVersions != null) {
			for (Version ele : fixVersions) {
				sprint = ele.getDescription();
			}
		}
		tasksAndAllocationsEntityObj.setSprint(sprint);

		for (IssueField ele : issue.getFields()) {
			if (ele.getId() != null && ele.getId().equals("customfield_10200") && ele.getValue() != null) {
				Double storyPoint = (Double) ele.getValue();
				tasksAndAllocationsEntityObj.setStoryPoints(storyPoint.intValue());
			}
		}
		tasksAndAllocationsEntityObj.setIssueCreatedDate(new Timestamp(issue.getCreationDate().toDate().getTime()));
		if (issue.getUpdateDate() == null) {
			tasksAndAllocationsEntityObj.setIssueUpdatedDate(tasksAndAllocationsEntityObj.getIssueCreatedDate());
		} else {
			tasksAndAllocationsEntityObj.setIssueUpdatedDate(new Timestamp(issue.getUpdateDate().toDate().getTime()));
		}
	}

	@Override
	public TasksAndAllocationsResultSet getAllTasksAndAllocations(Long employeeId, Long statusLookUpId) {
		TasksAndAllocationsResultSet tasksAndAllocationsResultSet = new TasksAndAllocationsResultSet();
		try {
			List<CmnProjectsMstEntity> cmnProjectMstEntityList = cmnProjectsMstRepository
					.getAllProjectsByProjectHead(employeeId);
			List<Long> projectIdList = new ArrayList<Long>();
			if (cmnProjectMstEntityList != null) {
				for (CmnProjectsMstEntity e : cmnProjectMstEntityList) {
					if (e.getStatusLookupId() != null && e.getStatusLookupId().equals(statusLookUpId)) {
						projectIdList.add(e.getProjectId());
					}
				}
			}
			List<CmnProjectTeamMembersEntity> cmnProjectTeamMembersEntityList = cmnProjectMembersRepository
					.getProjectTeamMembersByEmployeeId(employeeId);
			if (cmnProjectTeamMembersEntityList != null) {
				for (CmnProjectTeamMembersEntity e : cmnProjectTeamMembersEntityList) {
					if (e.getScrumMaster() != null && e.getScrumMaster().equals(ApplicationConstants.Y)
							&& e.getStatusLookupId() != null && e.getStatusLookupId().equals(statusLookUpId)) {
						projectIdList.add(e.getProjectId());
					}
				}
			}
			List<Object[]> resultObject = tasksAndAllocationsRepository
					.getAllTasksAndAllocationsBaseOnEmployeeId(projectIdList);
			List<TasksAndAllocationsDTO> tasksAndAllocationsDTOList = new ArrayList<TasksAndAllocationsDTO>();
			if (resultObject != null) {
				getTaskConfigurationMap();
				for (Object[] result : resultObject) {
					TasksAndAllocationsEntity tasksAndAllocationsEntity = (TasksAndAllocationsEntity) result[0];
					TasksAndAllocationsDTO tasksAndAllocationsDTO = new TasksAndAllocationsDTO();
					BeanUtils.copyProperties(tasksAndAllocationsEntity, tasksAndAllocationsDTO);
					tasksAndAllocationsDTO.setTesterFullName((String) result[1]);
					tasksAndAllocationsDTO.setDeveloperFullName((String) result[2]);
					tasksAndAllocationsDTO.setEmployeeFullName((String) result[3]);
					tasksAndAllocationsDTO.setJiraURL((String) result[4]);
					tasksAndAllocationsDTO.setJiraName((String) result[5]);
					String taskTypeDescription = TASK_TYPE_MAP.entrySet().stream()
							.filter(entry -> tasksAndAllocationsDTO.getTaskType().equals(entry.getValue()))
							.map(Map.Entry::getKey).findFirst().orElse(null);

					String priorityDescription = PRIORITY_TYPE_MAP.entrySet().stream()
							.filter(entry -> tasksAndAllocationsDTO.getPriority().equals(entry.getValue()))
							.map(Map.Entry::getKey).findFirst().orElse(null);

					String currentStatusDescription = TASK_STATUS_MAP.entrySet().stream()
							.filter(entry -> tasksAndAllocationsDTO.getCurrentStatus().equals(entry.getValue()))
							.map(Map.Entry::getKey).findFirst().orElse(null);
					tasksAndAllocationsDTO.setTaskTypeDescription(taskTypeDescription);
					tasksAndAllocationsDTO.setPriorityDescription(priorityDescription);
					tasksAndAllocationsDTO.setCurrentStatusDescription(currentStatusDescription);
					tasksAndAllocationsDTOList.add(tasksAndAllocationsDTO);
				}
				tasksAndAllocationsResultSet.setTasksAndAllocationsDtoList(tasksAndAllocationsDTOList);
			} else {
				tasksAndAllocationsResultSet.setStatus(false);
				tasksAndAllocationsResultSet.setMessage(ApplicationConstants.FAILED);
				tasksAndAllocationsResultSet.setMessageDescription("Unable to get issues");
				log.info("Exception in TasksAndAllocationsServiceImpl.getAllTasksAndAllocations");
			}

		} catch (Exception e) {
			tasksAndAllocationsResultSet.setStatus(false);
			tasksAndAllocationsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			tasksAndAllocationsResultSet.setMessageDescription("Unable to get issues");
			log.error("Exception in TasksAndAllocationsServiceImpl.getAllTasksAndAllocations");
		}
		return tasksAndAllocationsResultSet;
	}

	@Override
	public TasksAndAllocationsResultSet getAllTaskDetailsBasedOnTaskId(String taskId) {
		TasksAndAllocationsResultSet tasksAndAllocationsResultSet = new TasksAndAllocationsResultSet();
		try {
			TasksAndAllocationsEntity tasksAndAllocationsEntity = null;
			List<Object[]> resultObject = tasksAndAllocationsRepository.getTasksAndAllocationsByTaskId(taskId);
			String jiraURL = null;
			if (resultObject != null) {
				for (Object[] e : resultObject) {
					tasksAndAllocationsEntity = (TasksAndAllocationsEntity) e[0];
					jiraURL = (String) e[1];
				}
			}

			if (tasksAndAllocationsEntity != null) {
				getTaskConfigurationMap();
				TasksAndAllocationsDTO tasksAndAllocationsDTO = new TasksAndAllocationsDTO();
				BeanUtils.copyProperties(tasksAndAllocationsEntity, tasksAndAllocationsDTO);
				tasksAndAllocationsDTO.setJiraURL(jiraURL);
				String taskTypeDescription = TASK_TYPE_MAP.entrySet().stream()
						.filter(entry -> tasksAndAllocationsDTO.getTaskType().equals(entry.getValue()))
						.map(Map.Entry::getKey).findFirst().orElse(null);

				String priorityDescription = PRIORITY_TYPE_MAP.entrySet().stream()
						.filter(entry -> tasksAndAllocationsDTO.getPriority().equals(entry.getValue()))
						.map(Map.Entry::getKey).findFirst().orElse(null);

				String currentStatusDescription = TASK_STATUS_MAP.entrySet().stream()
						.filter(entry -> tasksAndAllocationsDTO.getCurrentStatus().equals(entry.getValue()))
						.map(Map.Entry::getKey).findFirst().orElse(null);
				tasksAndAllocationsDTO.setTaskTypeDescription(taskTypeDescription);
				tasksAndAllocationsDTO.setPriorityDescription(priorityDescription);
				tasksAndAllocationsDTO.setCurrentStatusDescription(currentStatusDescription);
				MyJiraClient myJiraClient = new MyJiraClient("satish.duvva@vrnda.com",
						"ATATT3xFfGF0Q-SM-09_rWWVnKl6-h-UoqaU2GqHAtdPqksX-cn70yIRJVpsnPBoXPwuDX3lwH4dlJ9tmKwxFR9ZW8Ce6xdIiar8pS-gAyzJ04B0sH-HEXOv3WWVyW4QLyQCSKGdjUyIk_8bdCVpZ9HozTnWP9zSQ73JOxjNu1sRpD3kUEX56E8=2B1E2FDA",
						"https://syscon.atlassian.net");
				Object issueCompleteDetails = myJiraClient.jiraIssueDetailsByRestApi(taskId, restTemplate);
				tasksAndAllocationsDTO.setIssueCompleteDetails(issueCompleteDetails);
				tasksAndAllocationsResultSet.setTasksAndAllocationsDto(tasksAndAllocationsDTO);
			} else {
				tasksAndAllocationsResultSet.setStatus(false);
				tasksAndAllocationsResultSet.setMessage(ApplicationConstants.FAILED);
				tasksAndAllocationsResultSet.setMessageDescription("Unable to Find issues");
				log.info("Exception in TasksAndAllocationsServiceImpl.getAllTaskDetailsBasedOnTaskId");
			}

		} catch (Exception e) {
			tasksAndAllocationsResultSet.setStatus(false);
			tasksAndAllocationsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			tasksAndAllocationsResultSet.setMessageDescription("Unable to get issues");
			log.error("Exception in TasksAndAllocationsServiceImpl.getAllTaskDetailsBasedOnTaskId");
		}
		return tasksAndAllocationsResultSet;
	}

	@Override
	public TasksAndAllocationsResultSet updateTaskAndAllocations(TasksAndAllocationsDTO tasksAndAllocationsDTO,
																 String loggedInUser) {
		TasksAndAllocationsResultSet tasksAndAllocationsResultSet = new TasksAndAllocationsResultSet();
		try {
			if (tasksAndAllocationsDTO != null) {
				TasksAndAllocationsEntity tasksAndAllocationsEntity = new TasksAndAllocationsEntity();
				BeanUtils.copyProperties(tasksAndAllocationsDTO, tasksAndAllocationsEntity);
//				BaseUtils.modifyBaseData(tasksAndAllocationsEntity, loggedInUser);
				tasksAndAllocationsEntity.setUpdatedDate(BaseUtils.getCurrentTime());
				tasksAndAllocationsEntity.setUpdatedBy(loggedInUser);
				save(tasksAndAllocationsEntity);
				tasksAndAllocationsResultSet.setTasksAndAllocationsEntity(tasksAndAllocationsEntity);
			}
		} catch (Exception e) {
			tasksAndAllocationsResultSet.setStatus(false);
			tasksAndAllocationsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			tasksAndAllocationsResultSet.setMessageDescription("Unable to Update issue");
			log.error("Exception in TasksAndAllocationsServiceImpl.updateTaskAndAllocations");
		}
		return tasksAndAllocationsResultSet;
	}

	@Override
	public TasksAndAllocationsResultSet getAllTaskDetailsBasedOnAssigne(Long assignee, Long statusLookUpId,
																		String viewType) {
		TasksAndAllocationsResultSet tasksAndAllocationsResultSet = new TasksAndAllocationsResultSet();
		try {
			List<Object[]> resultObject = tasksAndAllocationsRepository.getAllTaskDetailsBasedOnAssigne(assignee);
			List<TasksAndAllocationsDTO> tasksAndAllocationsDTOList = new ArrayList<TasksAndAllocationsDTO>();
			List<TasksAndAllocationsDTO> openList = new ArrayList<TasksAndAllocationsDTO>();
			List<TasksAndAllocationsDTO> devInProgressList = new ArrayList<TasksAndAllocationsDTO>();
			List<TasksAndAllocationsDTO> onHoldList = new ArrayList<TasksAndAllocationsDTO>();
			List<TasksAndAllocationsDTO> reOpenList = new ArrayList<TasksAndAllocationsDTO>();
			List<TasksAndAllocationsDTO> readyForTestingList = new ArrayList<TasksAndAllocationsDTO>();
			List<TasksAndAllocationsDTO> backLogList = new ArrayList<TasksAndAllocationsDTO>();
			List<TasksAndAllocationsDTO> testingInProgressList = new ArrayList<TasksAndAllocationsDTO>();
			List<TasksAndAllocationsDTO> readyForDeliveryList = new ArrayList<TasksAndAllocationsDTO>();
			List<TasksAndAllocationsDTO> moveToRegression = new ArrayList<TasksAndAllocationsDTO>();
			List<TasksAndAllocationsDTO> regressionInProgress = new ArrayList<TasksAndAllocationsDTO>();
			List<TasksAndAllocationsDTO> readyForRegression = new ArrayList<TasksAndAllocationsDTO>();
			if (resultObject != null) {
				getTaskConfigurationMap();
				TasksAndAllocationsEntity tasksAndAllocationsEntity = null;
				for (Object[] result : resultObject) {
					tasksAndAllocationsEntity = (TasksAndAllocationsEntity) result[0];
					TasksAndAllocationsDTO tasksAndAllocationsDTO = new TasksAndAllocationsDTO();
					BeanUtils.copyProperties(tasksAndAllocationsEntity, tasksAndAllocationsDTO);
					tasksAndAllocationsDTO.setTesterFullName((String) result[1]);
					tasksAndAllocationsDTO.setDeveloperFullName((String) result[2]);
					tasksAndAllocationsDTO.setEmployeeFullName((String) result[3]);
					tasksAndAllocationsDTO.setJiraURL((String) result[4]);
					tasksAndAllocationsDTO.setJiraName((String) result[5]);
					String taskTypeDescription = TASK_TYPE_MAP.entrySet().stream()
							.filter(entry -> tasksAndAllocationsDTO.getTaskType().equals(entry.getValue()))
							.map(Map.Entry::getKey).findFirst().orElse(null);

					String priorityDescription = PRIORITY_TYPE_MAP.entrySet().stream()
							.filter(entry -> tasksAndAllocationsDTO.getPriority().equals(entry.getValue()))
							.map(Map.Entry::getKey).findFirst().orElse(null);

					String currentStatusDescription = TASK_STATUS_MAP.entrySet().stream()
							.filter(entry -> tasksAndAllocationsDTO.getCurrentStatus().equals(entry.getValue()))
							.map(Map.Entry::getKey).findFirst().orElse(null);
					tasksAndAllocationsDTO.setTaskTypeDescription(taskTypeDescription);
					tasksAndAllocationsDTO.setPriorityDescription(priorityDescription);
					tasksAndAllocationsDTO.setCurrentStatusDescription(currentStatusDescription);
					if (viewType != null && viewType.equals("BOARD")) {
						if (ApplicationConstants.OPEN.equals(currentStatusDescription)) {
							openList.add(tasksAndAllocationsDTO);
						} else if (ApplicationConstants.DEV_IN_PROGRESS.equals(currentStatusDescription)) {
							devInProgressList.add(tasksAndAllocationsDTO);
						} else if (ApplicationConstants.ON_HOLD.equals(currentStatusDescription)) {
							onHoldList.add(tasksAndAllocationsDTO);
						} else if (ApplicationConstants.RE_OPEN.equals(currentStatusDescription)) {
							reOpenList.add(tasksAndAllocationsDTO);
						} else if (ApplicationConstants.READY_FOR_TESTING.equals(currentStatusDescription)) {
							readyForTestingList.add(tasksAndAllocationsDTO);
						} else if (ApplicationConstants.BACKLOG.equals(currentStatusDescription)) {
							backLogList.add(tasksAndAllocationsDTO);
						} else if (ApplicationConstants.TESTING_IN_PROGRESS.equals(currentStatusDescription)) {
							testingInProgressList.add(tasksAndAllocationsDTO);
						} else if (ApplicationConstants.READY_FOR_DELIVERY.equals(currentStatusDescription)) {
							readyForDeliveryList.add(tasksAndAllocationsDTO);
						} else if (ApplicationConstants.READY_FOR_REGRESSION.equals(currentStatusDescription)) {
							readyForRegression.add(tasksAndAllocationsDTO);
						} else if (ApplicationConstants.MOVE_TO_REGRESSION.equals(currentStatusDescription)) {
							moveToRegression.add(tasksAndAllocationsDTO);
						} else if (ApplicationConstants.REGRESSION_IN_PROGRESS.equals(currentStatusDescription)) {
							regressionInProgress.add(tasksAndAllocationsDTO);
						}
					} else {
						tasksAndAllocationsDTOList.add(tasksAndAllocationsDTO);
					}
				}
				if (viewType != null && viewType.equals("BOARD")) {
					tasksAndAllocationsResultSet.setOpenList(openList);
					tasksAndAllocationsResultSet.setDevInProgressList(devInProgressList);
					tasksAndAllocationsResultSet.setOnHoldList(onHoldList);
					tasksAndAllocationsResultSet.setReOpenList(reOpenList);
					tasksAndAllocationsResultSet.setReadyForTestingList(readyForTestingList);
					tasksAndAllocationsResultSet.setBackLogList(backLogList);
					tasksAndAllocationsResultSet.setTestingInProgressList(testingInProgressList);
					tasksAndAllocationsResultSet.setReadyForDeliveryList(readyForDeliveryList);
					tasksAndAllocationsResultSet.setMoveToRegression(moveToRegression);
					tasksAndAllocationsResultSet.setReadyForRegression(readyForRegression);
					tasksAndAllocationsResultSet.setRegressionInProgress(regressionInProgress);
				} else {
					tasksAndAllocationsResultSet.setTasksAndAllocationsDtoList(tasksAndAllocationsDTOList);
				}

			} else {
				tasksAndAllocationsResultSet.setStatus(false);
				tasksAndAllocationsResultSet.setMessage(ApplicationConstants.FAILED);
				tasksAndAllocationsResultSet.setMessageDescription("Unable to get issues");
				log.info("Exception in TasksAndAllocationsServiceImpl.getAllTaskDetailsBasedOnAssigne");
			}

		} catch (Exception e) {
			tasksAndAllocationsResultSet.setStatus(false);
			tasksAndAllocationsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			tasksAndAllocationsResultSet.setMessageDescription("Unable to get issues");
			log.error("Exception in TasksAndAllocationsServiceImpl.getAllTaskDetailsBasedOnAssigne");
		}
		return tasksAndAllocationsResultSet;
	}

	@Override
	public TasksAndAllocationsResultSet getAllTaskDetailsByProjectId(List<Long> projectId) {
		TasksAndAllocationsResultSet tasksAndAllocationsResultSet = new TasksAndAllocationsResultSet();
		try {
			List<TasksAndAllocationsEntity> taskAndAllocationsEntity = tasksAndAllocationsRepository
					.getAllTaskDetailsByProjectId(projectId);
			tasksAndAllocationsResultSet.setTasksAndAllocationsEntityList(taskAndAllocationsEntity);
		} catch (Exception e) {
			tasksAndAllocationsResultSet.setStatus(false);
			tasksAndAllocationsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			tasksAndAllocationsResultSet.setMessageDescription("Unable to get issues by project ID");
			log.error("Exception in TasksAndAllocationsServiceImpl.getAllTaskDetailsByProjectId");
		}
		return tasksAndAllocationsResultSet;
	}

	@Override
	public TasksAndAllocationsResultSet getAllTaskDetailsByProjectId(Long projectId) {
		TasksAndAllocationsResultSet tasksAndAllocationsResultSet = new TasksAndAllocationsResultSet();
		try {
			List<TasksAndAllocationsEntity> taskAndAllocationsEntity = tasksAndAllocationsRepository
					.getAllTaskDetailsByProjectId(projectId);
			tasksAndAllocationsResultSet.setTasksAndAllocationsEntityList(taskAndAllocationsEntity);
		} catch (Exception e) {
			tasksAndAllocationsResultSet.setStatus(false);
			tasksAndAllocationsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			tasksAndAllocationsResultSet.setMessageDescription("Unable to get issues by project ID");
			log.error("Exception in TasksAndAllocationsServiceImpl.getAllTaskDetailsByProjectId");
		}
		return tasksAndAllocationsResultSet;
	}

	@Override
	public TasksAndAllocationsResultSet getAllMembersByProjectIdAndStatusLookUpId(Long projectId, Long statusLookUpId) {
		TasksAndAllocationsResultSet tasksAndAllocationsResultSet = new TasksAndAllocationsResultSet();
		try {
			List<EmployeeDetailsDTO> employeeDetailsDTOList = new ArrayList<EmployeeDetailsDTO>();
			List<EmployeeDetailsDTO> developersList = new ArrayList<EmployeeDetailsDTO>();
			List<EmployeeDetailsDTO> testersList = new ArrayList<EmployeeDetailsDTO>();
			// Scrum Master's List start
			CmnProjectsMstResultSet cmnProjectsMstResultSet = cmnProjectsMstService.getProjectByProjectId(projectId);
			if (cmnProjectsMstResultSet != null && cmnProjectsMstResultSet.getCmnProjectsMstDTO() != null
					&& cmnProjectsMstResultSet.getCmnProjectsMstDTO().getStatusLookupId().equals(statusLookUpId)) {
				EmployeeDetailsDTO employeeDetailsDTO = new EmployeeDetailsDTO();
				employeeDetailsDTO.setEmployeeId(cmnProjectsMstResultSet.getCmnProjectsMstDTO().getProjectHead());
				employeeDetailsDTO
						.setEmployeeFullName(cmnProjectsMstResultSet.getCmnProjectsMstDTO().getProjectHeadFullName());
				employeeDetailsDTOList.add(employeeDetailsDTO);
				developersList.add(employeeDetailsDTO);
				testersList.add(employeeDetailsDTO);

			}

			CmnProjectTeamMembersResultSet cmnProjectMembersResultSet = cmnProjectTeamMembersService
					.getAllProjectTeamMembersByProjectIdAndScrumMaster(projectId);
			if (cmnProjectMembersResultSet != null
					&& cmnProjectMembersResultSet.getCmnProjectTeamMembersDtoList() != null) {
				cmnProjectMembersResultSet.getCmnProjectTeamMembersDtoList().forEach(e -> {
					if (e.getStatusLookupId().equals(statusLookUpId) && !employeeDetailsDTOList.stream()
							.filter(o -> o.getEmployeeId().equals(e.getEmployeeId())).findFirst().isPresent()) {
						EmployeeDetailsDTO employeeDetailsDTO = new EmployeeDetailsDTO();
						employeeDetailsDTO.setEmployeeId(e.getEmployeeId());
						employeeDetailsDTO.setEmployeeFullName(e.getEmployeeFullName());
						employeeDetailsDTOList.add(employeeDetailsDTO);
					}
				});
			}
			tasksAndAllocationsResultSet.setScrumMastersList(employeeDetailsDTOList);
			// Scrum Master's List end

			// Developers List
			List<CmnCompanyRolesMstEntity> developerRoleList = cmnCompanyRolesMstRepository
					.getCompanyRoleByCompanyRoleNames(
							Arrays.asList(ApplicationConstants.DEVTL, ApplicationConstants.DEVELOPER), statusLookUpId);

			// Testers List
			List<CmnCompanyRolesMstEntity> testerRoleList = cmnCompanyRolesMstRepository
					.getCompanyRoleByCompanyRoleNames(
							Arrays.asList(ApplicationConstants.TESTTL, ApplicationConstants.TESTER), statusLookUpId);

			List<Long> devRoleIds = null;
			List<Long> testRoleIds = null;
			if (developerRoleList != null) {
				devRoleIds = developerRoleList.stream().map(e -> e.getCompanyRoleId()).collect(Collectors.toList());
			}

			if (testerRoleList != null) {
				testRoleIds = testerRoleList.stream().map(e -> e.getCompanyRoleId()).collect(Collectors.toList());
			}

			CmnProjectTeamMembersResultSet cmnProjectDevAndTestResultSet = cmnProjectTeamMembersService
					.getAllCmnProjectTeamMembersByProjectId(projectId);
			if (cmnProjectDevAndTestResultSet != null
					&& cmnProjectDevAndTestResultSet.getCmnProjectTeamMembersDtoList() != null) {

				for (CmnProjectTeamMembersDTO obj : cmnProjectDevAndTestResultSet.getCmnProjectTeamMembersDtoList()) {
					if (devRoleIds != null && devRoleIds.contains(obj.getRoleId())
							&& obj.getStatusLookupId().equals(statusLookUpId)
							&& !developersList.stream().filter(o -> o.getEmployeeId().equals(obj.getEmployeeId()))
							.findFirst().isPresent()) {
						EmployeeDetailsDTO employeeDetailsDTO = new EmployeeDetailsDTO();
						employeeDetailsDTO.setEmployeeId(obj.getEmployeeId());
						employeeDetailsDTO.setEmployeeFullName(obj.getEmployeeFullName());
						developersList.add(employeeDetailsDTO);
					}

					if (testRoleIds != null && testRoleIds.contains(obj.getRoleId())
							&& obj.getStatusLookupId().equals(statusLookUpId)
							&& !testersList.stream().filter(o -> o.getEmployeeId().equals(obj.getEmployeeId()))
							.findFirst().isPresent()) {
						EmployeeDetailsDTO employeeDetailsDTO = new EmployeeDetailsDTO();
						employeeDetailsDTO.setEmployeeId(obj.getEmployeeId());
						employeeDetailsDTO.setEmployeeFullName(obj.getEmployeeFullName());
						testersList.add(employeeDetailsDTO);
					}
				}
				tasksAndAllocationsResultSet.setDevelopersList(developersList);
				tasksAndAllocationsResultSet.setTestersList(testersList);

			}
		} catch (Exception e) {
			tasksAndAllocationsResultSet.setStatus(false);
			tasksAndAllocationsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			tasksAndAllocationsResultSet.setMessageDescription("Unable to Load Project Members");
			log.error(
					"Exception in TasksAndAllocationsServiceImpl.getAllScrumMastersByProjectIdAndStatusLookUpId()");
		}
		return tasksAndAllocationsResultSet;
	}

	@Override
	public TasksAndAllocationsResultSet getAllActiveTaskDetailsBasedOnAssigne(Long assignee) {
		TasksAndAllocationsResultSet tasksAndAllocationsResultSet = new TasksAndAllocationsResultSet();
		try {
			List<String> taskIdList = tasksAndAllocationsRepository.getAllActiveTaskDetailsBasedOnAssigne(assignee);
			if (taskIdList != null) {
				tasksAndAllocationsResultSet.setTaskIdsList(taskIdList);
			}
		} catch (Exception e) {
			tasksAndAllocationsResultSet.setStatus(false);
			tasksAndAllocationsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			tasksAndAllocationsResultSet.setMessageDescription("Unable to Load Tasks");
			log.error("Exception in TasksAndAllocationsServiceImpl.getAllActiveTaskDetailsBasedOnAssigne()");
		}
		return tasksAndAllocationsResultSet;
	}

}