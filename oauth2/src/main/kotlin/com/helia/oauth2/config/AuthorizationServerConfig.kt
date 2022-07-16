package com.helia.oauth2.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.provider.ClientRegistrationException
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
import java.security.KeyPair
import java.util.List


/**
 * https://docs.spring.io/spring-security-oauth2-boot/docs/2.3.x-SNAPSHOT/reference/html5/#oauth2-boot-authorization-server-spring-security-oauth2-resource-server
 */
@EnableAuthorizationServer
@Configuration
class AuthorizationServerConfig(private val oauthProperties: OauthProperties, authenticationConfiguration: AuthenticationConfiguration, props: JwtProperties, jwtClamsEnhancer: JwtClamsEnhancer) : AuthorizationServerConfigurerAdapter() {
    private val authenticationManager: AuthenticationManager
    private val keyPair: KeyPair
    private val jwtClamsEnhancer: JwtClamsEnhancer
    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        val tokenEnhancerChain = TokenEnhancerChain()
        tokenEnhancerChain.setTokenEnhancers(listOf(jwtClamsEnhancer, jwtAccessTokenConverter(), IdTokenEnhancer(jwtAccessTokenConverter())))
        endpoints
                .authenticationManager(authenticationManager)
                .tokenEnhancer(tokenEnhancerChain)
                .tokenStore(tokenStore())
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.withClientDetails { clientId: String ->
            val clientDetails = oauthProperties.getClients()[clientId]
                    ?: throw ClientRegistrationException(String.format("%s is not registered.", clientId))
            clientDetails
        }
    }

    @Bean
    fun tokenStore(): TokenStore {
        return JwtTokenStore(jwtAccessTokenConverter())
    }

    @Bean
    fun jwtAccessTokenConverter(): JwtAccessTokenConverter {
        val converter = JwtAccessTokenConverter()
        converter.setKeyPair(keyPair)
        return converter
    }

    init {
        authenticationManager = authenticationConfiguration.authenticationManager
        keyPair = props.keyPair
        this.jwtClamsEnhancer = jwtClamsEnhancer
    }
}