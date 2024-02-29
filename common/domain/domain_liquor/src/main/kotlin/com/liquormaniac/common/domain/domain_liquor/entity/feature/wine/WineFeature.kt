package com.liquormaniac.common.domain.domain_liquor.entity.feature.wine

import jakarta.persistence.*

@Entity
@Table(name="wine_feature")
class WineFeature(alcoholFeatureId : Long, tannin : Byte) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id : Long? = null

    @Column(name = "alcohol_feature_id", columnDefinition = "bigint", nullable = false)
    var alcoholFeatureId : Long = alcoholFeatureId

    @Column(name = "tannin", columnDefinition = "tinyint", nullable = false)
    var tannin : Byte = tannin
}