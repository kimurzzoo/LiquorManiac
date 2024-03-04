package com.liquormaniac.common.domain.domain_liquor.entity

import com.liquormaniac.common.domain.domain_core.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name="alcohol_sub_type")
class AlcoholSubType(alcoholTypeId : Long, name : String) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id : Long? = null

    @Column(name = "alcohol_type_id", columnDefinition = "bigint", nullable = false)
    var alcoholTypeId : Long = alcoholTypeId

    @Column(name = "name", columnDefinition = "varchar(100)", nullable = false)
    var name : String = name
}