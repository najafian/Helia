package com.helia.oauth2.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.security.oauth2.provider.client.BaseClientDetails
import java.util.stream.Collectors

@ConfigurationProperties(prefix = "oauth")
class OauthProperties @ConstructorBinding constructor(val clients: List<BaseClientDetails>) {
    fun getClients(): MutableMap<String, BaseClientDetails?> = clients
            .stream()
            .collect(
                    Collectors.toMap({ obj: BaseClientDetails -> obj.clientId })
                    { x: BaseClientDetails? -> x })

}