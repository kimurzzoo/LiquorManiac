package com.liquormaniac.user.config

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import kotlin.jvm.Throws

@EnableWebSecurity
class SecurityConfig {
    @Bean
    @Throws(Exception::class)
    fun filterChain(http : HttpSecurity) : SecurityFilterChain
    {
        http
            .cors{cors -> cors.disable()}
            .csrf { csrf -> csrf.disable() }
            .httpBasic{ httpBasic -> httpBasic.disable() }
            .formLogin { formLogin -> formLogin.disable() }
            .sessionManagement { sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { authorizeHttpRequests -> authorizeHttpRequests.anyRequest().permitAll() } // 변경해야함
            // 필터 추가

        return http.build()
    }
}