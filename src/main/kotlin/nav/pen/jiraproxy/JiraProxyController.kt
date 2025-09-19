package nav.pen.jiraproxy

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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

}