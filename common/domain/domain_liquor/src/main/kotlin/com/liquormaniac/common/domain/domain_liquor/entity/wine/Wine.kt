package com.liquormaniac.common.domain.domain_liquor.entity.wine

import jakarta.persistence.*

@Entity
@Table(name="wine")
class Wine(alcoholId : Long, wineType : Long) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id : Long? = null

    @Column(name = "alcohol_id", columnDefinition = "bigint", nullable = false)
    var alcoholId : Long = alcoholId

    @Column(name = "wine_type", columnDefinition = "bigint", nullable = false)
    var wineType : Long = wineType
}