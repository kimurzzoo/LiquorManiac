package com.liquormaniac.common.domain.domain_liquor.repository

import com.liquormaniac.common.domain.domain_liquor.entity.Company
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CompanyRepository : JpaRepository<Company, Long> {
}