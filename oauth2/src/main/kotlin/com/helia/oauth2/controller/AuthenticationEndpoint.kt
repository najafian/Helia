package com.helia.oauth2.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AuthenticationEndpoint {


    @GetMapping("login")
    fun login(): String? {
        return "login"
    }
}