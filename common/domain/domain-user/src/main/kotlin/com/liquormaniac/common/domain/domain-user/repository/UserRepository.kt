package com.liquormaniac.common.domain.`domain-user`.repository

import org.springframework.data.jpa.repository.JpaRepository
import com.liquormaniac.common.domain.`domain-user`.entity.User
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmailAddress(emailAddress : String?) : User?

    fun existsByEmailAddress(emailAddress: String?) : Boolean
}