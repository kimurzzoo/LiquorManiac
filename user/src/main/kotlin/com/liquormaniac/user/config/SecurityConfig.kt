package com.liquormaniac.user.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    @Throws(Exception::class)
    fun filterChain(http : HttpSecurity) : SecurityFilterChain
    {
        http
            .cors{ cors -> cors.disable() } // TODO 변경해야됨
            .csrf { csrf -> csrf.disable() }
            .httpBasic{ httpBasic -> httpBasic.disable() }
            .formLogin { formLogin -> formLogin.disable() }
            .sessionManagement { sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { authorizeHttpRequests -> authorizeHttpRequests.anyRequest().permitAll() } // TODO 변경해야됨
            // TODO 필터 추가

        return http.build()
    }
}