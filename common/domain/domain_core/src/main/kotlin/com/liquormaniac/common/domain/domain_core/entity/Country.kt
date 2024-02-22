package com.liquormaniac.common.domain.domain_core.entity

import jakarta.persistence.*

@Entity
@Table(name="alcohol")
class Country(name : String) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id : Long? = null

    @Column(name = "name", columnDefinition = "varchar(100)", nullable = false)
    var name : String = name
}