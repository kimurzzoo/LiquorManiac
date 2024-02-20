package com.liquormaniac.user.service

import com.liquormaniac.common.core.core_web.dto.ResponseDTO
import com.liquormaniac.common.core.core_web.enums.ResponseCode
import com.liquormaniac.common.domain.domain_user.entity.User
import com.liquormaniac.common.domain.domain_user.redis_repository.UserStatusRepository
import com.liquormaniac.common.domain.domain_user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserAdminService(private val userRepository: UserRepository,
                       private val userStatusRepository: UserStatusRepository
) {
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
            userStatusRepository.deleteByEmail(user.emailAddress)

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