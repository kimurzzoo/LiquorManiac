package com.liquormaniac.common.core.core_web.exception

import com.liquormaniac.common.core.core_web.dto.ResponseDTO
import com.liquormaniac.common.core.core_web.enums.ResponseCode
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * @author         : 김영호
 * @date           : 24. 5. 9.
 * @description    :
 */

@RestControllerAdvice
class ResponseExceptionControllerAdvice {
    @ExceptionHandler(ResponseException::class)
    fun responseCodeExceptionHandler(e : ResponseException) : ResponseDTO<Unit>
    {
        val responseCode : ResponseCode = ResponseCode.valueOf(e.message!!)

        return ResponseDTO(responseCode, errorMessage = e.cause?.message)
    }
}