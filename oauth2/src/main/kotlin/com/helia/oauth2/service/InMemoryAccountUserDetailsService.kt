package com.helia.oauth2.service

import com.helia.oauth2.model.UserAccount
import com.helia.oauth2.model.UserAccountDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class InMemoryAccountUserDetailsService : UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {

        val userAccount = UserAccount(1, "", "", listOf(""))
                ?: throw UsernameNotFoundException(String.format("%s is not found.", username)) //this.accounts.get(username /* email */); check from DB
        return UserAccountDetails(userAccount)
    }
}