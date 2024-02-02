package com.liquormaniac.user.service

import org.springframework.stereotype.Service
import com.liquormaniac.common.domain.`domain-user`.repository.UserRepository
import com.liquormaniac.common.domain.`domain-user`.entity.User;
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

@Service
class UserDetailService(private val userRepository : UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val user : User = userRepository.findByEmailAddress(username)
        return user
    }
}