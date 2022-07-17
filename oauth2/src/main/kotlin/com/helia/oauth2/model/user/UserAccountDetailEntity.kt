package com.helia.oauth2.model.user

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails

class UserAccountDetailEntity(private val userAccountEntity: UserAccountEntity) : UserDetails {

    fun getUserAccount()=userAccountEntity

    override fun getAuthorities(): Collection<GrantedAuthority?> {
//        return AuthorityUtils.createAuthorityList(*userAccount.userRole!!.map { it.name }.toTypedArray())
   return AuthorityUtils.createAuthorityList("")
    }

    override fun getPassword(): String {
        return userAccountEntity.password
    }

    override fun getUsername(): String {
        return userAccountEntity.email
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