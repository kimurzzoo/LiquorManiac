package com.liquormaniac.user.controller

import com.liquormaniac.common.core.core_web.dto.ResponseDTO
import com.liquormaniac.user.dto.LoginDTO
import com.liquormaniac.user.dto.RegisterDTO
import com.liquormaniac.user.dto.TokenDTO
import com.liquormaniac.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
@Tag(name = "User API")
class UserController(private val userService: UserService) {

    @Operation(summary = "회원가입", description = "회원가입 API")
    @PostMapping("/register")
    fun register(@RequestBody registerInfo : RegisterDTO) : ResponseDTO<String>
    {
        return userService.register(registerInfo)
    }

    @Operation(summary = "로그인", description = "로그인 API")
    @PostMapping("/login")
    fun login(@RequestBody loginDTO: LoginDTO) : ResponseDTO<TokenDTO>
    {
        return userService.login(loginDTO)
    }
}