package com.helia.oauth2.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails

class UserAccountDetails(private val userAccount: UserAccount) : UserDetails {

    fun getUserAccount()=userAccount

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return AuthorityUtils.createAuthorityList(*userAccount.roles.toTypedArray())
    }

    override fun getPassword(): String {
        return userAccount.password
    }

    override fun getUsername(): String {
        return userAccount.email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}