package com.liquormaniac.user.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(info = Info(title = "User", description = "User 서비스 명세서", version = "0.1.0"))
@Configuration
class SwaggerConfig