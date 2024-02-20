package com.liquormaniac.user.controller

import com.liquormaniac.common.core.core_web.dto.ResponseDTO
import com.liquormaniac.user.dto.RegisterDTO
import com.liquormaniac.user.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {
    @PostMapping("/register")
    fun register(@RequestBody registerInfo : RegisterDTO) : ResponseDTO<String>
    {
        return userService.register(registerInfo)
    }
}