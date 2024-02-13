package com.liquormaniac.user.service

import com.liquormaniac.common.client.`client-util-dep`.encryption.hash.BCryptPasswordEncoder
import com.liquormaniac.common.client.`client-util-dep`.jwt.JwtProvider
import com.liquormaniac.common.client.`client-util-dep`.jwt.JwtResolver
import org.springframework.stereotype.Service
import com.liquormaniac.common.core.`core-web`.dto.ResponseDTO
import com.liquormaniac.common.core.`core-web`.enums.ResponseCode
import com.liquormaniac.common.domain.`domain-user`.repository.UserRepository
import com.liquormaniac.user.dto.RegisterDTO
import com.liquormaniac.common.domain.`domain-user`.entity.User
import com.liquormaniac.common.domain.`domain-user`.entity.UserStatus
import com.liquormaniac.common.domain.`domain-user`.repository.UserStatusRepository
import com.liquormaniac.user.dto.LoginDTO
import com.liquormaniac.user.dto.TokenDTO
import io.jsonwebtoken.Claims
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserService(private val userRepository: UserRepository,
                  private val userStatusRepository: UserStatusRepository,
                  private val bCryptPasswordEncoder: BCryptPasswordEncoder,
                  private val jwtProvider: JwtProvider,
                  private val jwtResolver: JwtResolver) {

    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = [Exception::class] )
    fun register(registerInfo : RegisterDTO) : ResponseDTO<String>
    {
        try {
            if(userRepository.existsByEmailAddress(registerInfo.emailAddress))
            {
                return ResponseDTO(ResponseCode.REGISTER_EMAIL_EXISTS)
            }

            if(registerInfo.password != registerInfo.passwordConfirm)
            {
                return ResponseDTO(ResponseCode.REGISTER_PASSWORD_NOT_CONFIRMED)
            }

            //email regex, pw regex 추가

            userRepository.save(User(registerInfo.nickName, registerInfo.emailAddress, bCryptPasswordEncoder.encode(registerInfo.password), "ROLE_USER"))

            return ResponseDTO(ResponseCode.SUCCESS)
        }
        catch (e : Exception)
        {
            return ResponseDTO(ResponseCode.SERVER_ERROR, errorMessage = e.message)
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = [Exception::class] )
    fun withdrawal(email: String) : ResponseDTO<Unit>
    {
        try {
            val user : User? = userRepository.findByEmailAddress(email)
            if(user == null)
            {
                return ResponseDTO(ResponseCode.NO_USER)
            }
            else
            {
                userRepository.delete(user)
                userStatusRepository.deleteByEmail(email)
                return ResponseDTO(ResponseCode.SUCCESS)
            }
        }
        catch (e : Exception)
        {
            return ResponseDTO(ResponseCode.SERVER_ERROR, errorMessage = e.message)
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = [Exception::class])
    fun login(loginInfo: LoginDTO) : ResponseDTO<TokenDTO>
    {
        try
        {
            val user : User? = userRepository.findByEmailAddress(loginInfo.emailAddress)
            if(user == null)
            {
                return ResponseDTO(ResponseCode.NO_USER)
            }

            if(!bCryptPasswordEncoder.matches(loginInfo.password, user.m_password))
            {
                return ResponseDTO(ResponseCode.LOGIN_PASSWORD_NOT_MATCHED)
            }

            if(!user.enabled)
            {
                return ResponseDTO(ResponseCode.LOGIN_BLOCKED)
            }

            val indicator : String = UUID.randomUUID().toString()

            val accessToken : String = jwtProvider.createAccessToken(user.emailAddress, indicator, user.role)
            val refreshToken : String = jwtProvider.createRefreshToken(indicator)

            userStatusRepository.save(UserStatus(refreshToken, indicator, loginInfo.emailAddress))

            return ResponseDTO(ResponseCode.SUCCESS, TokenDTO(accessToken, refreshToken))
        }
        catch (e : Exception)
        {
            return ResponseDTO(ResponseCode.SERVER_ERROR, errorMessage =  e.message)
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = [Exception::class])
    fun logout(refreshToken: String) : ResponseDTO<Unit>
    {
        try
        {
            val claims: Claims = jwtResolver.getClaims(refreshToken)
            val indicator: String = claims.subject
            val userStatus: UserStatus? = userStatusRepository.findByIdAndIndicator(refreshToken, indicator)
            if(userStatus != null)
            {
                userStatusRepository.deleteById(refreshToken)
            }
            return ResponseDTO(ResponseCode.SUCCESS)
        }
        catch (e : Exception)
        {
            return ResponseDTO(ResponseCode.SERVER_ERROR, errorMessage = e.message)
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = [Exception::class])
    fun reissue(accessToken: String, refreshToken: String) : ResponseDTO<TokenDTO>
    {
        try {
            val accessTokenClaims: Claims = jwtResolver.getClaims(accessToken)
            val refreshTokenClaims: Claims = jwtResolver.getClaims(refreshToken)

            val userStatus = userStatusRepository.findByIdAndIndicator(refreshToken, refreshTokenClaims.subject)

            if(userStatus == null)
            {
                return ResponseDTO(ResponseCode.REISSUE_NO_STATUS)
            }

            if(accessTokenClaims.subject == userStatus.email
                && accessTokenClaims["indicator"].toString() == refreshTokenClaims.subject
                && refreshTokenClaims.subject == userStatus.indicator)
            {
                val user : User? = userRepository.findByEmailAddress(userStatus.email)
                if(user == null)
                {
                    return ResponseDTO(ResponseCode.NO_USER)
                }

                if(!user.enabled)
                {
                    return ResponseDTO(ResponseCode.REISSUE_BLOCKED)
                }

                val indicator : String = UUID.randomUUID().toString()

                val accessToken : String = jwtProvider.createAccessToken(user.emailAddress, indicator, user.role)
                val refreshToken : String = jwtProvider.createRefreshToken(indicator)

                userStatusRepository.save(UserStatus(refreshToken, indicator, user.emailAddress))

                return ResponseDTO(ResponseCode.SUCCESS)
            }
            else
            {
                return ResponseDTO(ResponseCode.REISSUE_WRONG_STATUS)
            }
        }
        catch (e : Exception)
        {
            return ResponseDTO(ResponseCode.SERVER_ERROR, errorMessage = e.message)
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = [Exception::class])
    fun block(userId : Long) : ResponseDTO<Unit>
    {
        try {
            val userOptional : Optional<User> = userRepository.findById(userId)
            if(userOptional.isEmpty)
            {
                return ResponseDTO(ResponseCode.NO_USER)
            }

            val user : User = userOptional.get()

            if(!user.enabled)
            {
                return ResponseDTO(ResponseCode.BLOCK_ALREADY_BLOCKED)
            }

            user.enabled = false

            userRepository.save(user)

            return ResponseDTO(ResponseCode.SUCCESS)
        }
        catch (e : Exception)
        {
            return ResponseDTO(ResponseCode.SERVER_ERROR, errorMessage =  e.message)
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = [Exception::class])
    fun unblock(userId : Long) : ResponseDTO<Unit>
    {
        try {
            val userOptional : Optional<User> = userRepository.findById(userId)
            if(userOptional.isEmpty)
            {
                return ResponseDTO(ResponseCode.NO_USER)
            }

            val user : User = userOptional.get()

            if(user.enabled)
            {
                return ResponseDTO(ResponseCode.UNBLOCK_ALREADY_UNBLOCKED)
            }

            user.enabled = true

            userRepository.save(user)

            return ResponseDTO(ResponseCode.SUCCESS)
        }
        catch (e : Exception)
        {
            return ResponseDTO(ResponseCode.SERVER_ERROR, errorMessage =  e.message)
        }
    }
}