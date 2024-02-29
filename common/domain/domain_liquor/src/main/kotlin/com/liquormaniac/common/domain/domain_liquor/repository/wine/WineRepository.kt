package com.liquormaniac.common.domain.domain_liquor.repository.wine

import com.liquormaniac.common.domain.domain_liquor.entity.wine.Wine
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WineRepository : JpaRepository<Wine, Long> {
}