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
    BLOCKED(1012, "Your account is blocked."),
    BLOCK_ALREADY_BLOCKED(1021, "This user is already blocked."),
    UNBLOCK_ALREADY_UNBLOCKED(1031, "This user is already unblocked."),
    REISSUE_NO_STATUS(1042, "There is no login status of you."),
    REISSUE_WRONG_STATUS(1043, "You have wrong login status."),
    CHANGEPW_CUR_PW_NOT_MATCHED(1051, "Current password doesn't match to your current password."),
    CHANGEPW_PASSWORD_NOT_CONFIRMED(1052, "Your password is not confirmed."),
    SENDVERIFICATIONMAIL_ALREADY_VERIFIED(1061, "You are already verified."),
    LOGOUT_WRONG_REFRESH_TOKEN(1071, "Your refresh token is wrong."),
    VERIFICATION_ALREADY_VERIFIED(1081, "You are already verified."),
    VERIFICATION_CODE_EXPIRED(1082, "Your code is expired"),
    VERIFICATION_WRONG_CODE(1083, "Your code is wrong"),

    //chat
    CREATECHATROOM_ALREADY_EXISTS(2002, "Chatroom already exists."),
    CREATECHATROOM_NOT_DELETED_YET(2003, "Chatroom is not deleted yet."),
    CREATECHATROOM_NO_SELF_CHAT(2004, "Self-chat is not allowed."),
    CREATECHATROOM_MANY_SAME_CHATROOMS(2005, "There are many same chatrooms."),
    CREATECHATROOM_WRONG_CHATROOM(2006, "Wrong chatroom is selected."),
    LEAVECHATROOM_NOT_YOURS(2011, "This chatroom is not yours."),
    LEAVECHATROOM_ALREADY_LEAVED(2012, "You already leaved this chatroom."),
    LEAVECHATROOM_NO_CHATROOM(2013, "There is no chatroom to leave."),
    MESSAGES_NO_CHATROOM(2021, "There is no chatroom"),
    MESSAGES_NOT_YOURS(2022, "This chatroom is not yours."),
    UPLOADMESSAGEIMAGES_NO_CHATROOM(2031, "There is no chatroom"),
    UPLOADMESSAGEIMAGES_NOT_YOUR_CHATROOM(2032, "This chatroom is not yours."),
    SENDMESSAGE_NO_CHATROOM(2041, "There is no chatroom"),
    SENDMESSAGE_NOT_YOUR_CHATROOM(2042, "This chatroom is not yours."),
}