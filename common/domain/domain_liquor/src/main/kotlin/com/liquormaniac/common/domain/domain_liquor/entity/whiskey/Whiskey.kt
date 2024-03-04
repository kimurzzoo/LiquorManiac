package com.liquormaniac.common.domain.domain_liquor.entity.whiskey

import com.liquormaniac.common.domain.domain_core.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name="whiskey")
class Whiskey(alcoholId : Long, whiskeyType : Long) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id : Long? = null

    @Column(name = "alcohol_id", columnDefinition = "bigint", nullable = false)
    var alcoholId : Long = alcoholId

    @Column(name = "whiskey_type", columnDefinition = "bigint", nullable = false)
    var whiskeyType : Long = whiskeyType
}