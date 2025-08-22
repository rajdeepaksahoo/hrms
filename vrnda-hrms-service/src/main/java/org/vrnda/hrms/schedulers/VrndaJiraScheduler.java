package org.vrnda.hrms.schedulers;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class VrndaJiraScheduler {

	private static final String JIRA_URL = "https://syscon.atlassian.net/jira/software/c/projects/S4/issues";
	private static final String JIRA_ADMIN_USERNAME = "ramesh.sorupaka@vrnda.com";
	private static final String JIRA_ADMIN_PASSWORD = "SanuTanu@12";

//	@Scheduled(cron = "0 15 1 31 * *")
//	public void getJiraIssues() throws Exception {
//		// Construct the JRJC client
//		try {
//			System.out.println(String.format("Logging in to %s with username '%s' and password '%s'", JIRA_URL, JIRA_ADMIN_USERNAME, JIRA_ADMIN_PASSWORD));
//			JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
//			URI uri = new URI(JIRA_URL);
//			JiraRestClient client = factory.createWithBasicHttpAuthentication(uri, JIRA_ADMIN_USERNAME, JIRA_ADMIN_PASSWORD);
//			// Invoke the JRJC Client
//			Promise<User> promise = client.getUserClient().getUser("Fph3XIg2Ruu4wVlzn6PmD3A6");
//			User user = promise.claim();
//			for (BasicProject project : client.getProjectClient().getAllProjects().claim()) {
//				System.out.println(project.getKey() + ": " + project.getName());
//			}
//			Promise<SearchResult> searchJqlPromise = client.getSearchClient().searchJql("project = MYPURRJECT AND status in (Closed, Completed, Resolved) ORDER BY assignee, resolutiondate");
//			for (Issue issue : searchJqlPromise.claim().getIssues()) {
//				System.out.println(issue.getSummary());
//			}
//			// Print the result
//			System.out.println(String.format("Your admin user's email address is: %s\r\n", user.getEmailAddress()));
//			System.exit(0);
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
}