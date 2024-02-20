package com.liquormaniac.common.domain.domain_user.repository

import com.liquormaniac.common.domain.domain_user.entity.UserStatus
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserStatusRepository : CrudRepository<UserStatus, String> {
    fun findByIdAndIndicator(refreshToken: String, indicator: String) : UserStatus?

    fun deleteByEmail(email: String)
}