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
}

data class CreateIssueRequest(val title: String)
data class CreateIssueResponse(val id: String, val key: String, val self: String)
data class IssueDetails(val id: String, val key: String, val fields: Map<String, Any>)
data class CommentRequest(val body: String)
data class CommentResponse(val id: String, val body: String, val author: Map<String, Any>)
data class TransitionRequest(val transition: Map<String, String>)
