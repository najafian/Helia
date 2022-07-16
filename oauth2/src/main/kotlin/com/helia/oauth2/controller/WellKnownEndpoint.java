package com.helia.oauth2.controller;

import com.helia.oauth2.config.JwtProperties;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.Map;


@RestController
public class WellKnownEndpoint {
    private final KeyPair keyPair;

    public WellKnownEndpoint(JwtProperties props) {
        this.keyPair = props.getKeyPair();
    }


    @GetMapping(path = {".well-known/openid-configuration", "oauth/token/.well-known/openid-configuration"})
    public Map<String, Object> openId(UriComponentsBuilder builder) {
        return Map.of("issuer", builder.replacePath("oauth/token").build().toString(),
                "authorization_endpoint", builder.replacePath("oauth/authorize").build().toString(),
                "token_endpoint", builder.replacePath("oauth/token").build().toString(),
                "jwks_uri", builder.replacePath("token_keys").build().toString(),
                "subject_types_supported", List.of("public"));
    }


    @GetMapping(path = "token_keys")
    public Map<String, Object> tokens() {
        final RSAPublicKey publicKey = (RSAPublicKey) this.keyPair.getPublic();
        final RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }
}
