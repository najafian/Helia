package com.helia.oauth2.config

import com.helia.oauth2.data.repository.ClientDetailRepository
import com.helia.oauth2.model.clientdetail.ClientDetailEntity
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.provider.ClientRegistrationException
import org.springframework.security.oauth2.provider.client.BaseClientDetails
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
import java.security.KeyPair
import javax.annotation.PostConstruct


/**
 * https://docs.spring.io/spring-security-oauth2-boot/docs/2.3.x-SNAPSHOT/reference/html5/#oauth2-boot-authorization-server-spring-security-oauth2-resource-server
 */
@EnableAuthorizationServer
@Configuration
class AuthorizationServerConfig(private val clientDetailRepositories: ClientDetailRepository, authenticationConfiguration: AuthenticationConfiguration, props: JwtProperties, jwtClamsEnhancer: JwtClamsEnhancer) : AuthorizationServerConfigurerAdapter() {
    private val authenticationManager: AuthenticationManager
    private val keyPair: KeyPair
    private val jwtClamsEnhancer: JwtClamsEnhancer
    private lateinit var clientDetailEntities:MutableMap<String,BaseClientDetails>
    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        val tokenEnhancerChain = TokenEnhancerChain()
        tokenEnhancerChain.setTokenEnhancers(listOf(jwtClamsEnhancer, jwtAccessTokenConverter(), IdTokenEnhancer(jwtAccessTokenConverter())))
        endpoints
                .authenticationManager(authenticationManager)
                .tokenEnhancer(tokenEnhancerChain)
                .tokenStore(tokenStore())
    }
    @PostConstruct
    fun fillClientList(){
        val findAll = clientDetailRepositories.findAll()
        findAll.forEach {
            clientDetailEntities[it.clientId!!]=(BaseClientDetails(
                it.clientId,
                it.resourceIds,
                it.clientScopes!!.map {m-> m.name }.reduce { acc, s -> "$acc,$s" },
                it.grantType,it.clientAuthorities!!.map { m-> m.name }.reduce { acc, s -> "$acc,$s" },
                it.redirectUrl))
        }
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {

        clients.withClientDetails { clientId: String ->
            val clientDetails =clientDetailEntities[clientId]
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