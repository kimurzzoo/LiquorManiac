package com.liquormaniac.common.domain.`domain-user`.repository

import com.liquormaniac.common.domain.`domain-user`.entity.UserStatus
import org.springframework.data.repository.CrudRepository

interface UserStatusRepository : CrudRepository<UserStatus, String> {
    fun findByIdAndIndicator(refreshToken: String, indicator: String) : UserStatus?

    fun deleteByEmail(email: String)
}