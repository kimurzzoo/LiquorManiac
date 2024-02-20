package com.liquormaniac.user.dto

import io.swagger.v3.oas.annotations.media.Schema

class TokenDTO(
    @Schema(description = "access token")
    val accessToken: String?,

    @Schema(description = "refresh token")
    val refreshToken: String?)