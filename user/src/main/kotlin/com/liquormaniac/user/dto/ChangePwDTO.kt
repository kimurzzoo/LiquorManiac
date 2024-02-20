package com.liquormaniac.user.dto

import io.swagger.v3.oas.annotations.media.Schema

data class ChangePwDTO(
    @Schema(description = "현재 비밀번호")
    val curPw : String,

    @Schema(description = "새 비밀번호")
    val newPw : String,

    @Schema(description = "새 비밀번호 확인")
    val newPwConfirm : String
)