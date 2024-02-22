package com.liquormaniac.common.domain.domain_liquor.repository

import com.liquormaniac.common.domain.domain_liquor.entity.Alcohol
import org.springframework.data.jpa.repository.JpaRepository

interface AlcoholRepository : JpaRepository<Alcohol, Long> {
}