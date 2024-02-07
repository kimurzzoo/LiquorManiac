package com.liquormaniac.user.service

import com.liquormaniac.common.client.`client-util-dep`.encryption.hash.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import com.liquormaniac.common.core.`core-web`.dto.ResponseDTO
import com.liquormaniac.common.core.`core-web`.enums.ResponseCode
import com.liquormaniac.common.domain.`domain-user`.repository.UserRepository
import com.liquormaniac.user.dto.RegisterDTO
import com.liquormaniac.common.domain.`domain-user`.entity.User
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository, private val bCryptPasswordEncoder: BCryptPasswordEncoder) {

    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = [Exception::class] )
    fun register(registerInfo : RegisterDTO) : ResponseDTO
    {
        try {
            if(userRepository.existsByEmailAddress(registerInfo.emailAddress))
            {
                return ResponseDTO(ResponseCode.REGISTER_EMAIL_EXISTS, null);
            }

            if(registerInfo.password != registerInfo.passwordConfirm)
            {
                return ResponseDTO(ResponseCode.REGISTER_PASSWORD_NOT_CONFIRMED, null)
            }

            //email regex, pw regex 추가

            userRepository.save(User(registerInfo.nickName, registerInfo.emailAddress, bCryptPasswordEncoder.encode(registerInfo.password), "ROLE_USER"))

            return ResponseDTO(ResponseCode.SUCCESS, null)
        }
        catch (e : Exception)
        {
            return ResponseDTO(ResponseCode.SERVER_ERROR, e.message)
        }
    }


}