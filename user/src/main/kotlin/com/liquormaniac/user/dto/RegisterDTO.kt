package com.liquormaniac.user.dto

import io.swagger.v3.oas.annotations.media.Schema

data class RegisterDTO(
    @Schema(description = "이메일 주소")
    val emailAddress : String,

    @Schema(description = "비밀번호")
    val password: String,

    @Schema(description = "비밀번호 확인")
    val passwordConfirm : String,

    @Schema(description = "닉네임")
    val nickName: String,

    @Schema(description = "국가")
    val country: Long
)