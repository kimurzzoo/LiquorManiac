package com.liquormaniac.common.domain.domain_liquor.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name="alcohol")
class Alcohol(name : String, alcoholType : Long, abv : Double, isYeast : Boolean = false, releaseDate : Date? = null, country : Long, releaseCompany : Long) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id : Long? = null

    @Column(name = "name", columnDefinition = "varchar(100)", nullable = false)
    var name : String = name

    @Column(name = "alcohol_type", columnDefinition = "bigint", nullable = false)
    var alcoholType : Long = alcoholType

    @Column(name = "abv", columnDefinition = "double", nullable = false)
    var abv : Double = abv

    @Column(name = "is_yeast", nullable = false)
    var isYeast : Boolean = isYeast

    @Column(name = "release_date", columnDefinition = "date")
    var releaseDate : Date? = releaseDate

    @Column(name = "country", columnDefinition = "bigint", nullable = false)
    var country : Long = country

    @Column(name = "release_company", columnDefinition = "bigint", nullable = false)
    var releaseCompany : Long = releaseCompany
}