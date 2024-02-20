package com.liquormaniac.common.core.core_web.dto

import com.liquormaniac.common.core.core_web.enums.ResponseCode

class ResponseDTO<T>(
    val code : Int,
    val message: String,
    val data : T? = null,
    val errorMessage: String? = null)
{
    constructor(responseCode: ResponseCode, data: T? = null, errorMessage: String? = null) : this(responseCode.code, responseCode.message, data, errorMessage)
}