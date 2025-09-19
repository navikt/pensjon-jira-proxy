package nav.pen.jiraproxy

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.DefaultUriBuilderFactory


@Configuration
class JiraConfiguration {

    @Bean
    fun jiraRestTemplate(
        @Value("\${jira.url}") jiraUrl: String,
        @Value("\${jira_access_token}") jiraAccessToken: String,
    ) = RestTemplate().apply {
        // Set up authentication
        this.interceptors.add { request, body, execution ->
            request.headers.add("Authorization", "Bearer $jiraAccessToken")
            execution.execute(request, body)
        }
        // set baseurl
        this.uriTemplateHandler = DefaultUriBuilderFactory(jiraUrl)
    }

}