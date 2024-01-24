package com.liquormaniac.user.service

import org.springframework.stereotype.Service
import com.liquormaniac.common.domain.`domain-user`.repository.UserRepository

@Service
class UserDetailService(private val userRepository : UserRepository,) {

}