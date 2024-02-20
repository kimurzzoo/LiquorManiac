package com.liquormaniac.common.core.core_web.enums

enum class ResponseCode(val code: Int, val message: String) {
    //common
    SUCCESS(200, "success"),

    //user
    NO_USER(410, "There is no user."),
    SERVER_ERROR(500, "Server Error"),
    REGISTER_EMAIL_EXISTS(1001, "Your email is already registered."),
    REGISTER_PASSWORD_NOT_CONFIRMED(1002, "Your password is not confirmed."),
    LOGIN_PASSWORD_NOT_MATCHED(1011, "Password does not match."),
    LOGIN_BLOCKED(1012, "Your account is blocked."),
    BLOCK_ALREADY_BLOCKED(1021, "This user is already blocked."),
    UNBLOCK_ALREADY_UNBLOCKED(1031, "This user is already unblocked."),
    REISSUE_BLOCKED(1041, "Your account is blocked."),
    REISSUE_NO_STATUS(1042, "There is no login status of you."),
    REISSUE_WRONG_STATUS(1043, "You have wrong login status."),
    CHANGEPW_CUR_PW_NOT_MATCHED(1051, "Current password doesn't match to your current password."),
    CHANGEPW_PASSWORD_NOT_CONFIRMED(1052, "Your password is not confirmed."),
    SENDVERIFICATIONMAIL_ALREADY_VERIFIED(1061, "You are already verified."),
    LOGOUT_WRONG_REFRESH_TOKEN(1071, "Your refresh token is wrong."),
    //
}