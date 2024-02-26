package com.liquormaniac.common.domain.domain_liquor.entity.feature

import jakarta.persistence.*

@Entity
@Table(name="alcohol_pairing")
class AlcoholPairing(alcoholFeatureId : Long, name : String) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id : Long? = null

    @Column(name = "alcohol_feature_id", columnDefinition = "bigint", nullable = false)
    var alcoholFeatureId : Long = alcoholFeatureId

    @Column(name = "name", columnDefinition = "varchar(30)", nullable = false)
    var name : String = name
}