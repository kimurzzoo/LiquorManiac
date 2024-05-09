package com.liquormaniac.common.core.core_web.exception

import com.liquormaniac.common.core.core_web.enums.ResponseCode

/**
 * @author         : 김영호
 * @date           : 24. 5. 9.
 * @description    :
 */
class ResponseException(responseCode: ResponseCode, e: Throwable) : Exception(responseCode.code.toString(), e)