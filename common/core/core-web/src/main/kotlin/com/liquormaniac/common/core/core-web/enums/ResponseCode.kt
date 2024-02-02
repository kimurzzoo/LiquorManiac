package com.liquormaniac.common.core.`core-web`.enums

enum class ResponseCode(val code: Int, val message: String) {
    SUCCESS(200, "success"),
    REGISTER_EMAIL_EXISTS(1001, "Your email is already registered."),
    REGISTER_PASSWORD_NOT_CONFIRMED(1002, "Your password is not confirmed."),

}