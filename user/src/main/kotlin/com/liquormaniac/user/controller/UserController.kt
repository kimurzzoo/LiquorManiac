package com.liquormaniac.user.controller

import com.liquormaniac.common.core.core_web.dto.ResponseDTO
import com.liquormaniac.user.dto.ChangePwDTO
import com.liquormaniac.user.dto.LoginDTO
import com.liquormaniac.user.dto.RegisterDTO
import com.liquormaniac.user.dto.TokenDTO
import com.liquormaniac.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
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

    @Operation(summary = "토큰 재발급", description = "토큰 재발급 API")
    @GetMapping("/reissue")
    fun reissue(@RequestHeader(name = "accessToken") accessToken : String,
                @RequestHeader(name = "refreshToken") refreshToken : String) : ResponseDTO<TokenDTO>
    {
        return userService.reissue(accessToken, refreshToken)
    }

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴 API")
    @GetMapping("/withdrawal")
    fun withdrawal(@RequestHeader(name = "X-User-Id") userId: Long) : ResponseDTO<Unit>
    {
        return userService.withdrawal(userId)
    }

    @Operation(summary = "로그아웃", description = "로그아웃 API")
    @GetMapping("/logout")
    fun logout(@RequestHeader(name = "X-User-Id") userId: Long,
                @RequestHeader(name = "refreshToken") refreshToken : String) : ResponseDTO<Unit>
    {
        return userService.logout(userId, refreshToken)
    }

    @Operation(summary = "인증 코드 메일 전송", description = "인증 코드 메일 전송 API")
    @GetMapping("/sendVerificationMail")
    fun sendVerificationMail(@RequestHeader(name = "X-User-Id") userId: Long) : ResponseDTO<Unit>
    {
        return userService.sendVerificationMail(userId)
    }

    @Operation(summary = "코드 인증", description = "코드 인증 API")
    @GetMapping("/verification")
    fun verification(@RequestHeader(name = "X-User-Id") userId: Long,
                     @RequestParam(name = "code", required = true) @Schema(description = "인증 코드") code : String) : ResponseDTO<Unit>
    {
        return userService.verification(userId, code)
    }

    @Operation(summary = "닉네임 변경", description = "닉네임 변경 API")
    @GetMapping("/changeNickname")
    fun changeNickname(@RequestHeader(name = "X-User-Id") userId: Long,
                       @RequestParam(name = "newNickname", required = true) @Schema(description = "새 닉네임") newNickname : String) : ResponseDTO<Unit>
    {
        return userService.changeNickname(userId, newNickname)
    }

    @Operation(summary = "비밀번호 변경", description = "비밀번호 변경 API")
    @PostMapping("/changePw")
    fun changePw(@RequestHeader(name = "X-User-Id" ) userId: Long,
                 @RequestHeader(name = "refreshToken") refreshToken : String,
                 @RequestBody changePwDTO: ChangePwDTO) : ResponseDTO<Unit>
    {
        return userService.changePw(userId, refreshToken, changePwDTO.curPw, changePwDTO.newPw, changePwDTO.newPwConfirm)
    }
}