package com.liquormaniac.common.domain.domain_liquor.entity.feature

import jakarta.persistence.*

@Entity
@Table(name="alcohol_feature")
class AlcoholFeature(alcoholId: Long, userId: Long, acidity : Byte, bitterness : Byte, sweetness : Byte, viscosity : Byte, body : Byte, comment : String) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id : Long? = null

    @Column(name = "alcohol_id", columnDefinition = "bigint", nullable = false)
    var alcoholId : Long = alcoholId

    @Column(name = "user_id", columnDefinition = "bigint", unique = true, nullable = false)
    var userId : Long = userId

    @Column(name = "acidity", columnDefinition = "tinyint", nullable = false)
    var acidity : Byte = acidity

    @Column(name = "bitterness", columnDefinition = "tinyint", nullable = false)
    var bitterness : Byte = bitterness

    @Column(name = "sweetness", columnDefinition = "tinyint", nullable = false)
    var sweetness : Byte = sweetness

    @Column(name = "viscosity", columnDefinition = "tinyint", nullable = false)
    var viscosity : Byte = viscosity

    @Column(name = "body", columnDefinition = "tinyint", nullable = false)
    var body : Byte = body

    @Column(name = "comment", columnDefinition = "text", nullable = false)
    var comment : String = comment
}