package com.liquormaniac.common.domain.domain_core.repository

import com.liquormaniac.common.domain.domain_core.entity.Country
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CountryRepository : JpaRepository<Country, Long> {
}