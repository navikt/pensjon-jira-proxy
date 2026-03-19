package nav.pen.jiraproxy

import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class JiraClient(
    private val jiraRestTemplate: RestTemplate,
) {

    fun createIssue(request: CreateIssueRequest): CreateIssueResponse {
        return jiraRestTemplate.postForObject("/rest/api/2/issue", request, CreateIssueResponse::class.java)
            ?: throw RuntimeException("Failed to create issue")
    }

    fun getIssue(issueId: String): IssueDetails {
        return jiraRestTemplate.getForObject("/rest/api/2/issue/$issueId", IssueDetails::class.java)
            ?: throw RuntimeException("Issue not found")
    }

    fun addComment(issueId: String, comment: CommentRequest): CommentResponse {
        return jiraRestTemplate.postForObject("/rest/api/2/issue/$issueId/comment", comment, CommentResponse::class.java)
            ?: throw RuntimeException("Failed to add comment")
    }

    fun transitionIssue(issueId: String, transition: TransitionRequest) {
        jiraRestTemplate.postForObject("/rest/api/2/issue/$issueId/transitions", transition, Void::class.java)
            ?: throw RuntimeException("Failed to transition issue")
    }

    fun getCreateMetaIssueTypes(projectKey: String): Map<String, Any> {
        return jiraRestTemplate.getForObject(
            "/rest/api/2/issue/createmeta/$projectKey/issuetypes",
            Map::class.java,
        ) as? Map<String, Any> ?: throw RuntimeException("Failed to get issue types for $projectKey")
    }

    fun getCreateMetaFields(projectKey: String, issueTypeId: String): Map<String, Any> {
        return jiraRestTemplate.getForObject(
            "/rest/api/2/issue/createmeta/$projectKey/issuetypes/$issueTypeId",
            Map::class.java,
        ) as? Map<String, Any> ?: throw RuntimeException("Failed to get fields for $projectKey/$issueTypeId")
    }
}

data class CreateIssueRequest(val fields: Map<String, Any>)
data class CreateIssueResponse(val id: String, val key: String, val self: String)
data class IssueDetails(val id: String, val key: String, val fields: Map<String, Any>)
data class CommentRequest(val body: String)
data class CommentResponse(val id: String, val body: String, val author: Map<String, Any>)
data class TransitionRequest(val transition: Map<String, String>)
