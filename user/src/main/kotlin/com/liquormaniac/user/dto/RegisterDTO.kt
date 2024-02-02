package com.liquormaniac.user.dto

data class RegisterDTO(
    val emailAddress : String,
    val password: String,
    val passwordConfirm : String,
    val nickName: String)