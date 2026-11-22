package com.elenaibraeva.familymessenger.backend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests {
                it.requestMatchers(
                    "/actuator/health",
                    "/actuator/info",
                ).permitAll()

                it.requestMatchers("/api/v1/**").authenticated()

                it.anyRequest().denyAll()
            }
            .oauth2ResourceServer {
                it.jwt { }
            }

        return http.build()
    }
}
