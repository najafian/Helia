package com.helia.oauth2.service

import com.helia.oauth2.model.user.UserAccountEntity
import com.helia.oauth2.model.user.UserAccountDetailEntity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class InMemoryAccountUserDetailsService : UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {

        val userAccountEntity = UserAccountEntity()
                ?: throw UsernameNotFoundException(String.format("%s is not found.", username)) //this.accounts.get(username /* email */); check from DB
        return UserAccountDetailEntity(userAccountEntity)
    }
}