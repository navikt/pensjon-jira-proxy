package nav.pen.jiraproxy

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class JiraService(
    private val jiraClient: RestTemplate,
) {
}