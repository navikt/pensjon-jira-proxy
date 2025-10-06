package nav.pen.jiraproxy

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val TexasAuthenticationFilter: TexasAuthenticationFilter,
) {

    @Bean
    fun filterChain(http: org.springframework.security.config.annotation.web.builders.HttpSecurity): org.springframework.security.web.SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/isalive").permitAll()
                    .requestMatchers("/api/**").authenticated()
            }
            .addFilterBefore(TexasAuthenticationFilter, BasicAuthenticationFilter::class.java)
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
        return http.build()
    }

}