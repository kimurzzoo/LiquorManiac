package com.liquormaniac.common.domain.domain_liquor.repository.wine

import com.liquormaniac.common.domain.domain_liquor.entity.wine.Grape
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GrapeRepository : JpaRepository<Grape, Long> {
}