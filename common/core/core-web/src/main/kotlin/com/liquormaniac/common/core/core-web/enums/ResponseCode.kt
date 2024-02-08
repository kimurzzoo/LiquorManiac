package com.liquormaniac.common.core.`core-web`.enums

enum class ResponseCode(code: Int, message: String) {
    SUCCESS(200, "success"),
    NO_USER(410, "There is no user."),
    SERVER_ERROR(500, "Server Error"),
    REGISTER_EMAIL_EXISTS(1001, "Your email is already registered."),
    REGISTER_PASSWORD_NOT_CONFIRMED(1002, "Your password is not confirmed."),
    LOGIN_PASSWORD_NOT_MATCHED(1011, "Password does not match."),
    LOGIN_BLOCKED(1012, "Your account is blocked."),
    BLOCK_ALREADY_BLOCKED(1021, "This user is already blocked."),
    UNBLOCK_ALREADY_UNBLOCKED(1031, "This user is already unblocked."),
}