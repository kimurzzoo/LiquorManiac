package com.liquormaniac.common.domain.domain_user.redis_repository

import com.liquormaniac.common.domain.domain_user.entity.VerificationCode
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface VerificationCodeRepository : CrudRepository<VerificationCode, String> {
}