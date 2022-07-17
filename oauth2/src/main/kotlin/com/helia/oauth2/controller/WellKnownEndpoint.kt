package com.helia.oauth2.controller

import com.helia.oauth2.config.JwtProperties
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import java.security.KeyPair
import java.security.interfaces.RSAPublicKey
import java.util.List

@RestController
class WellKnownEndpoint(props: JwtProperties) {
    private val keyPair: KeyPair

    init {
        keyPair = props.keyPair
    }

    @GetMapping(path = [".well-known/openid-configuration", "oauth/token/.well-known/openid-configuration"])
    fun openId(builder: UriComponentsBuilder): Map<String, Any> {
        return java.util.Map.of("issuer", builder.replacePath("oauth/token").build().toString(),
                "authorization_endpoint", builder.replacePath("oauth/authorize").build().toString(),
                "token_endpoint", builder.replacePath("oauth/token").build().toString(),
                "jwks_uri", builder.replacePath("token_keys").build().toString(),
                "subject_types_supported", List.of("public"))
    }

    @GetMapping(path = ["token_keys"])
    fun tokens(): Map<String, Any> {
        val publicKey = keyPair.public as RSAPublicKey
        val key = RSAKey.Builder(publicKey).build()
        return JWKSet(key).toJSONObject()
    }
}