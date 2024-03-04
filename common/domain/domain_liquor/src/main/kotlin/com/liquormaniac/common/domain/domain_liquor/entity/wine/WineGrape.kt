package com.liquormaniac.common.domain.domain_liquor.entity.wine

import com.liquormaniac.common.domain.domain_core.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name="wine_grape")
class WineGrape(wineId : Long, grapeId : Long, grapeHarvestYear : Short, soilId : Long, location : String) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id : Long? = null

    @Column(name = "wine_id", columnDefinition = "bigint", nullable = false)
    var wineId : Long = wineId

    @Column(name = "grape_id", columnDefinition = "bigint", nullable = false)
    var grapeId : Long = grapeId

    @Column(name = "grape_harvest_year", columnDefinition = "smallint", nullable = false)
    var grapeHarvestYear : Short = grapeHarvestYear

    @Column(name = "soil", columnDefinition = "bigint", nullable = false)
    var soilId : Long = soilId

    @Column(name = "location", columnDefinition = "bigint", nullable = false)
    var location : String = location
}