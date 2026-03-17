package nav.pen.jiraproxy

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class JiraProxyController(
    val jiraClient: JiraClient,
) {

    @GetMapping("/issue/{issueKey}")
    fun getIssue(@PathVariable issueKey: String): IssueDetails {
        return jiraClient.getIssue(issueKey)
    }

    @PostMapping("/issue")
    fun createIssue(@RequestBody request: CreateIssueRequest): CreateIssueResponse {
        return jiraClient.createIssue(request)
    }

    @PostMapping("/issues")
    fun createIssues(@RequestBody requests: List<CreateIssueRequest>): List<CreateIssueResponse> {
        return requests.map { jiraClient.createIssue(it) }
    }

}