package nav.pen.jiraproxy

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory.getLogger
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
class TexasAuthenticationFilter(
    private val texasClient: TexasClient
) : OncePerRequestFilter() {

    private val logger = getLogger(javaClass)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val isTokenValid = try {
            val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
            logger.info("Authorization header: $authHeader")
            val token = authHeader?.substringAfter("Bearer ") ?: ""
            texasClient.validateToken(token)
        } catch (e: Exception) {
            logger.error(e.message)
            false
        }

        if (isTokenValid) {
            val authenticationToken: Authentication =
                UsernamePasswordAuthenticationToken(null, null, emptyList())
            SecurityContextHolder.getContext().authentication = authenticationToken
        }

        filterChain.doFilter(request, response)
    }
}


