package nav.pen.jiraproxy

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class TexasClient(
    @param:Value("\${NAIS_TOKEN_INTROSPECTION_ENDPOINT}") private val texasIntrospectionUrl: String,
) {

    private val restTemplate = RestTemplate()

    fun validateToken(token: String): Boolean {

        val request = TexasRequest(
            identity_provider = "azuread",
            token = token
        )

        val response = restTemplate.postForObject(texasIntrospectionUrl, request, TexasResponse::class.java)
            ?: throw RuntimeException("Failed to validate token")

        if (response.error != null) {
            throw RuntimeException("Token validation error: ${response.error}")
        }
        return response.active
    }

    data class TexasResponse(
        val active: Boolean,
        val error: String?,
    )

    data class TexasRequest(
        val identity_provider: String,
        val token: String,
    )

}