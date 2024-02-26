package com.liquormaniac.common.domain.domain_liquor.repository.feature

import com.liquormaniac.common.domain.domain_liquor.entity.feature.AlcoholFeature
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AlcoholFeatureRepository : JpaRepository<AlcoholFeature, Long> {
}