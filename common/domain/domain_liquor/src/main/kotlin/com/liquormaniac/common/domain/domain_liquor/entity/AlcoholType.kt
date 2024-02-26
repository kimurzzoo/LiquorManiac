package com.liquormaniac.common.domain.domain_liquor.entity

import jakarta.persistence.*

@Entity
@Table(name="alcohol_type")
class AlcoholType(name: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id : Long? = null

    @Column(name = "name", columnDefinition = "varchar(100)", nullable = false)
    var name : String = name
}