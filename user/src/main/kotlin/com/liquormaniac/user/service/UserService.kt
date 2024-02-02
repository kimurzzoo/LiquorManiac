package com.liquormaniac.user.service

import org.springframework.stereotype.Service
import com.liquormaniac.common.core.`core-web`.dto.ResponseDTO
import com.liquormaniac.common.core.`core-web`.enums.ResponseCode
import com.liquormaniac.common.domain.`domain-user`.repository.UserRepository
import com.liquormaniac.user.dto.RegisterDTO

@Service
class UserService(private val userRepository: UserRepository) {

    fun register(registerInfo : RegisterDTO) : ResponseDTO
    {
        if(userRepository.existsByEmailAddress(registerInfo.emailAddress))
        {
           return ResponseDTO(ResponseCode.REGISTER_EMAIL_EXISTS, null);
        }

        if(registerInfo.password != registerInfo.passwordConfirm)
        {
            return ResponseDTO(ResponseCode.REGISTER_PASSWORD_NOT_CONFIRMED, null)
        }
    }
}