package com.liquormaniac.common.domain.domain_liquor.repository

import com.liquormaniac.common.domain.domain_liquor.entity.AlcoholType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AlcoholTypeRepository : JpaRepository<AlcoholType, Long> {
}