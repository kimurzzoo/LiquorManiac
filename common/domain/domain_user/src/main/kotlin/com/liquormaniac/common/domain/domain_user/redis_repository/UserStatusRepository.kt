package com.liquormaniac.common.domain.domain_user.redis_repository

import com.liquormaniac.common.domain.domain_user.redis_entity.UserStatus
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserStatusRepository : CrudRepository<UserStatus, String> {
    fun findByIdAndIndicator(refreshToken: String, indicator: String) : UserStatus?

    fun findByEmail(email: String) : List<UserStatus?>
}