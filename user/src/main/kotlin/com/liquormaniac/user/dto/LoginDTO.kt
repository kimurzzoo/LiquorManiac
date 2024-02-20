package com.liquormaniac.user.dto

import io.swagger.v3.oas.annotations.media.Schema

class LoginDTO(
    @Schema(description = "이메일 주소")
    val emailAddress: String,

    @Schema(description = "비밀번호")
    val password: String)