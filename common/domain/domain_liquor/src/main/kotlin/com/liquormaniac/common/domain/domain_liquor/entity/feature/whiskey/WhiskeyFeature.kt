package com.liquormaniac.common.domain.domain_liquor.entity.feature.whiskey

import com.liquormaniac.common.domain.domain_core.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name="whiskey_feature")
class WhiskeyFeature(alcoholFeatureId : Long, smokey : Byte) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id : Long? = null

    @Column(name = "alcohol_feature_id", columnDefinition = "bigint", nullable = false)
    var alcoholFeatureId : Long = alcoholFeatureId

    @Column(name = "smokey", columnDefinition = "tinyint", nullable = false)
    var smokey : Byte = smokey
}