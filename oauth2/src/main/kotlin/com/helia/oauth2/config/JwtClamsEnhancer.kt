package com.helia.oauth2.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.helia.oauth2.model.user.UserAccountDetailEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames
import org.springframework.security.oauth2.jwt.JwtClaimNames
import org.springframework.security.oauth2.provider.ClientDetails
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.stereotype.Component
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.time.Instant
import java.util.*

@Component
class JwtClamsEnhancer(private val clientDetailsService: ClientDetailsService) : TokenEnhancer {
    override fun enhance(accessToken: OAuth2AccessToken, authentication: OAuth2Authentication): OAuth2AccessToken {
        val issuer = ServletUriComponentsBuilder.fromCurrentRequest().build().toString()
        val expiration = accessToken.expiration.toInstant()
        val client = SecurityContextHolder.getContext().authentication
        val clientId = client.name
        val clientDetails = clientDetailsService.loadClientByClientId(clientId)
        (accessToken as DefaultOAuth2AccessToken).additionalInformation =
                setAdditionalInformation(issuer, expiration, clientDetails, clientId, authentication)
        return accessToken
    }

    private fun setAdditionalInformation(issuer: String, expiration: Instant, clientDetails: ClientDetails, clientId: String?, authentication: OAuth2Authentication): MutableMap<String, Any> {
        val additionalInformation: MutableMap<String, Any> = LinkedHashMap()
        additionalInformation[JwtClaimNames.ISS] = issuer
        additionalInformation[JwtClaimNames.EXP] = expiration.epochSecond
        additionalInformation[JwtClaimNames.IAT] = expiration.minusSeconds(clientDetails.accessTokenValiditySeconds.toLong()).epochSecond
        additionalInformation[JwtClaimNames.AUD] = listOf(clientId)
        val nonce = authentication.oAuth2Request.requestParameters[OidcParameterNames.NONCE]
        if (nonce != null) {
            additionalInformation[OidcParameterNames.NONCE] = nonce
        }
        if (authentication.principal is UserAccountDetailEntity) {
            val principal = authentication.principal as UserAccountDetailEntity
            val account = principal.getUserAccount();
            additionalInformation[JwtClaimNames.SUB] = account.userId!!;
            additionalInformation["email_verified"] = true;
            additionalInformation["user"] = ObjectMapper().writeValueAsString(account)
        }
        return additionalInformation
    }
}