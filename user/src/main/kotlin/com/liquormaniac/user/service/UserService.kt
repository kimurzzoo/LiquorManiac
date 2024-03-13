package com.liquormaniac.user.service

import com.liquormaniac.common.client.client_util_dep.encryption.hash.BCryptPasswordEncoder
import com.liquormaniac.common.client.client_util_dep.jwt.JwtProvider
import com.liquormaniac.common.client.client_util_dep.jwt.JwtResolver
import org.springframework.stereotype.Service
import com.liquormaniac.common.core.core_web.dto.ResponseDTO
import com.liquormaniac.common.core.core_web.enums.ResponseCode
import com.liquormaniac.common.core.core_web.enums.Role
import com.liquormaniac.common.domain.domain_user.repository.UserRepository
import com.liquormaniac.user.dto.RegisterDTO
import com.liquormaniac.common.domain.domain_user.entity.User
import com.liquormaniac.common.domain.domain_user.entity.UserStatus
import com.liquormaniac.common.domain.domain_user.redis_repository.UserStatusRepository
import com.liquormaniac.user.dto.LoginDTO
import com.liquormaniac.user.dto.TokenDTO
import io.jsonwebtoken.Claims
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*
import com.liquormaniac.common.client.client_util_dep.random.verificationCodeGenerator
import com.liquormaniac.common.domain.domain_user.entity.VerificationCode
import com.liquormaniac.common.domain.domain_user.redis_repository.VerificationCodeRepository

@Service
class UserService(private val userRepository: UserRepository,
                  private val userStatusRepository: UserStatusRepository,
                  private val verificationCodeRepository: VerificationCodeRepository,
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

            userRepository.save(User(registerInfo.nickName, registerInfo.emailAddress, bCryptPasswordEncoder.encode(registerInfo.password), Role.ROLE_USER.toString(), registerInfo.country))

            return ResponseDTO(ResponseCode.SUCCESS)
        }
        catch (e : Exception)
        {
            e.printStackTrace()
            return ResponseDTO(ResponseCode.SERVER_ERROR, errorMessage =  e.message)
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = [Exception::class] )
    fun withdrawal(userId: Long) : ResponseDTO<Unit>
    {
        try {
            val user : User? = userRepository.findById(userId).orElse(null)
            if(user == null)
            {
                return ResponseDTO(ResponseCode.NO_USER)
            }
            else
            {
                userRepository.delete(user)

                //TODO 다른 서비스들에서 유저 관련 데이터 전부 삭제

                val userStatus = userStatusRepository.findByEmail(user.emailAddress)
                if(userStatus.isNotEmpty())
                {
                    userStatusRepository.deleteAll(userStatus)
                }
                return ResponseDTO(ResponseCode.SUCCESS)
            }
        }
        catch (e : Exception)
        {
            e.printStackTrace()
            return ResponseDTO(ResponseCode.SERVER_ERROR, errorMessage =  e.message)
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = [Exception::class])
    fun login(loginInfo: LoginDTO) : ResponseDTO<TokenDTO>
    {
        try
        {
            val user : User = userRepository.findByEmailAddress(loginInfo.emailAddress)
                ?: return ResponseDTO(ResponseCode.NO_USER)

            if(!bCryptPasswordEncoder.matches(loginInfo.password, user.m_password))
            {
                return ResponseDTO(ResponseCode.LOGIN_PASSWORD_NOT_MATCHED)
            }

            if(!user.enabled)
            {
                return ResponseDTO(ResponseCode.BLOCKED)
            }

            val indicator : String = UUID.randomUUID().toString()

            val accessToken : String = jwtProvider.createAccessToken(user.id!!, indicator, user.role)
            val refreshToken : String = jwtProvider.createRefreshToken(indicator)

            userStatusRepository.save(UserStatus(refreshToken, indicator, loginInfo.emailAddress))

            return ResponseDTO(ResponseCode.SUCCESS, TokenDTO(accessToken, refreshToken))
        }
        catch (e : Exception)
        {
            e.printStackTrace()
            return ResponseDTO(ResponseCode.SERVER_ERROR, errorMessage =  e.message)
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = [Exception::class])
    fun logout(userId: Long, refreshToken: String) : ResponseDTO<Unit>
    {
        try
        {
            val user : User? = userRepository.findById(userId).orElse(null)
            if(user == null)
            {
                return ResponseDTO(ResponseCode.NO_USER)
            }

            val claims: Claims = jwtResolver.getClaims(refreshToken)
            val indicator: String = claims.subject
            val userStatus: UserStatus? = userStatusRepository.findByIdAndIndicator(refreshToken, indicator)
            if(userStatus != null)
            {
                if(userStatus.email != user.emailAddress)
                {
                    return ResponseDTO(ResponseCode.LOGOUT_WRONG_REFRESH_TOKEN)
                }
                userStatusRepository.deleteById(refreshToken)
            }
            return ResponseDTO(ResponseCode.SUCCESS)
        }
        catch (e : Exception)
        {
            e.printStackTrace()
            return ResponseDTO(ResponseCode.SERVER_ERROR, errorMessage =  e.message)
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = [Exception::class])
    fun sendVerificationMail(userId: Long) : ResponseDTO<Unit>
    {
        try {
            val user : User = userRepository.findById(userId).orElse(null) ?: return ResponseDTO(ResponseCode.NO_USER)

            if(!user.enabled)
            {
                return ResponseDTO(ResponseCode.BLOCKED)
            }

            if(user.verified)
            {
                return ResponseDTO(ResponseCode.SENDVERIFICATIONMAIL_ALREADY_VERIFIED)
            }

            val code : String = verificationCodeGenerator(user.emailAddress)
            val verificationCode = VerificationCode(user.emailAddress, code)

            // TODO 이메일 발송

            verificationCodeRepository.save(verificationCode)
            return ResponseDTO(ResponseCode.SUCCESS)
        }
        catch (e : Exception)
        {
            e.printStackTrace()
            return ResponseDTO(ResponseCode.SERVER_ERROR, errorMessage =  e.message)
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = [Exception::class])
    fun verification(userId: Long, code : String) : ResponseDTO<Unit>
    {
        try {
            val user : User = userRepository.findById(userId).orElse(null) ?: return ResponseDTO(ResponseCode.NO_USER)

            if(!user.enabled)
            {
                return ResponseDTO(ResponseCode.BLOCKED)
            }

            if(user.verified)
            {
                return ResponseDTO(ResponseCode.VERIFICATION_ALREADY_VERIFIED)
            }

            val verificationCodeOptional = verificationCodeRepository.findById(user.emailAddress)
            if(verificationCodeOptional.isEmpty)
            {
                return ResponseDTO(ResponseCode.VERIFICATION_CODE_EXPIRED)
            }

            val verificationCode = verificationCodeOptional.get()

            if(verificationCode.code != code)
            {
                return ResponseDTO(ResponseCode.VERIFICATION_WRONG_CODE)
            }

            user.role = Role.ROLE_VERIFIED.toString()
            user.verified = true
            userRepository.save(user)
            verificationCodeRepository.deleteById(user.emailAddress)
            return ResponseDTO(ResponseCode.SUCCESS)
        }
        catch (e : Exception)
        {
            e.printStackTrace()
            return ResponseDTO(ResponseCode.SERVER_ERROR, errorMessage =  e.message)
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

            val user = userRepository.findById(accessTokenClaims.subject.toLong()).orElse(null)

            if(user == null)
            {
                return ResponseDTO(ResponseCode.NO_USER)
            }

            if(user.emailAddress == userStatus.email
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
                    return ResponseDTO(ResponseCode.BLOCKED)
                }

                val indicator : String = UUID.randomUUID().toString()

                val newAccessToken : String = jwtProvider.createAccessToken(user.id!!, indicator, user.role)
                val newRefreshToken : String = jwtProvider.createRefreshToken(indicator)

                userStatusRepository.deleteById(refreshToken)
                userStatusRepository.save(UserStatus(newRefreshToken, indicator, user.emailAddress))

                return ResponseDTO(ResponseCode.SUCCESS, TokenDTO(newAccessToken, newRefreshToken))
            }
            else
            {
                return ResponseDTO(ResponseCode.REISSUE_WRONG_STATUS)
            }
        }
        catch (e : Exception)
        {
            e.printStackTrace()
            return ResponseDTO(ResponseCode.SERVER_ERROR, errorMessage =  e.message)
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = [Exception::class])
    fun changeNickname(userId : Long, newNickname : String) : ResponseDTO<Unit>
    {
        try {
            val user : User? = userRepository.findById(userId).orElse(null)

            if(user != null)
            {
                user.nickname = newNickname
                userRepository.save(user)
                return ResponseDTO(ResponseCode.SUCCESS)
            }
            else
            {
                return ResponseDTO(ResponseCode.NO_USER)
            }
        }
        catch (e : Exception)
        {
            e.printStackTrace()
            return ResponseDTO(ResponseCode.SERVER_ERROR, errorMessage =  e.message)
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = [Exception::class])
    fun changePw(userId : Long, refreshToken: String, curPw : String, newPw : String, newPwConfirm : String) : ResponseDTO<Unit>
    {
        try {
            val user : User? = userRepository.findById(userId).orElse(null)

            if(user != null)
            {
                if(!bCryptPasswordEncoder.matches(curPw, user.m_password))
                {
                    return ResponseDTO(ResponseCode.CHANGEPW_CUR_PW_NOT_MATCHED)
                }

                if(newPw != newPwConfirm)
                {
                    return ResponseDTO(ResponseCode.CHANGEPW_PASSWORD_NOT_CONFIRMED)
                }

                // TODO pw regex 추가
                user.m_password = newPw
                userRepository.save(user)
                logout(userId, refreshToken)
                return ResponseDTO(ResponseCode.SUCCESS)
            }
            else
            {
                return ResponseDTO(ResponseCode.NO_USER)
            }
        }
        catch (e : Exception)
        {
            e.printStackTrace()
            return ResponseDTO(ResponseCode.SERVER_ERROR, errorMessage =  e.message)
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = [Exception::class])
    fun changeCountry(userId : Long, countryId : Long) : ResponseDTO<Unit>
    {
        try {
            val user : User? = userRepository.findById(userId).orElse(null)

            if(user != null)
            {
                user.country = countryId
                userRepository.save(user)
                return ResponseDTO(ResponseCode.SUCCESS)
            }
            else
            {
                return ResponseDTO(ResponseCode.NO_USER)
            }
        }
        catch (e : Exception)
        {
            e.printStackTrace()
            return ResponseDTO(ResponseCode.SERVER_ERROR, errorMessage =  e.message)
        }
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, rollbackFor = [Exception::class])
    fun isBlocked(userId : Long) : ResponseDTO<Boolean>
    {
        try {
            val user : User? = userRepository.findById(userId).orElse(null)

            if(user != null)
            {
                return ResponseDTO(ResponseCode.SUCCESS, user.enabled)
            }
            else
            {
                return ResponseDTO(ResponseCode.NO_USER)
            }
        }
        catch (e : Exception)
        {
            e.printStackTrace()
            return ResponseDTO(ResponseCode.SERVER_ERROR, errorMessage =  e.message)
        }
    }
}