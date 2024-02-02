package com.liquormaniac.common.core.`core-web`.dto

import com.liquormaniac.common.core.`core-web`.enums.ResponseCode

class ResponseDTO(
    val code : Int,
    val message: String,
    val data : Any?)
{
    constructor(responseCode: ResponseCode, data: Any?) : this(responseCode.code, responseCode.message, data)
}