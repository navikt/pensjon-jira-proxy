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
        @Value("\${jira.username}") jiraUser: String,
        @Value("\${jira.password}") jiraPassword: String
    ) = RestTemplate().apply {
        // Set up basic authentication
        val auth = "$jiraUser:$jiraPassword"
        val encodedAuth = java.util.Base64.getEncoder().encodeToString(auth.toByteArray())
        this.interceptors.add { request, body, execution ->
            request.headers.add("Authorization", "Basic $encodedAuth")
            execution.execute(request, body)
        }
        // set baseurl
        this.uriTemplateHandler = DefaultUriBuilderFactory(jiraUrl)
    }

}