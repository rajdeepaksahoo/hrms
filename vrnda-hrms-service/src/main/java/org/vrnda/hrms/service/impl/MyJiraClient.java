package org.vrnda.hrms.service.impl;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

public class MyJiraClient {
	private String username;
	private String password;
	private String jiraUrl;
	private JiraRestClient restClient;

	private JiraRestClient getJiraRestClient() {
		return new AsynchronousJiraRestClientFactory().createWithBasicHttpAuthentication(getJiraUri(), this.username,
				this.password);
	}

	private URI getJiraUri() {
		return URI.create(this.jiraUrl);
	}

	public MyJiraClient(String username, String password, String jiraUrl) {
		this.username = username;
		this.password = password;
		this.jiraUrl = jiraUrl;
		this.restClient = getJiraRestClient();
	}

	public Issue getIssue(String issueKey) throws Exception {
		return restClient.getIssueClient().getIssue(issueKey).get();
	}

	public SearchResult getIssueByJQL(String jql, int maxResult, int startAt) throws Exception {
		Set<String> set = new LinkedHashSet<String>();
		set.add("*all");

		SearchResult searchResult = restClient.getSearchClient().searchJql(jql, maxResult, startAt, set).get();
		return searchResult;
	}

	public Object jiraIssueDetailsByRestApi(String taskId, RestTemplate restTemplate) {
		restTemplate.setInterceptors(
				Collections.singletonList((ClientHttpRequestInterceptor) (httpRequest, bytes, execution) -> {
					HttpHeaders headers = httpRequest.getHeaders();
					String credentials = this.username + ":" + this.password;
					String encodedCredentials = Base64.getEncoder()
							.encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
					headers.add("Authorization", "Basic " + encodedCredentials);
					return execution.execute(httpRequest, bytes);
				}));
		// https://syscon.atlassian.net
		Object issueCompleteDetails = restTemplate
				.getForObject(this.jiraUrl + "/rest/api/3/issue/" + taskId + "?expand=renderedFields", Object.class);

		return issueCompleteDetails;
	}

}
