package com.liquormaniac.common.domain.`domain-user`.entity

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name="user")
class User(nickname : String, emailAddress : String, m_password : String, role : String) : UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id : Long? = null

    @Column(name = "nickname", columnDefinition = "varchar(200)", nullable = false)
    var nickname : String = nickname

    @Column(name = "email_address", columnDefinition = "varchar(200)", nullable = false, unique = true)
    var emailAddress : String = emailAddress

    @Column(name = "password", columnDefinition = "varchar(200)", nullable = false)
    var m_password : String = m_password

    @Column(name = "role", columnDefinition = "varchar(200)", nullable = false)
    var role : String = role

    @Column(name = "enabled", nullable = false)
    var enabled : Boolean = true

    @Column(name = "verified", nullable = false)
    var verified : Boolean = false

    override fun getPassword(): String {
        return m_password
    }

    override fun getUsername(): String {
        return emailAddress
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(role))
    }

    override fun isAccountNonExpired(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return enabled
    }
}