package com.helia.oauth2.model

import java.time.ZonedDateTime

class UserAccount(
        var userId: Long,
        var email: String,
        var password: String,
        var roles: List<String>,
        var appID: Long?=0,
        var companyID: Long?=0,
        var name: String?="",
        var lastName: String?="",
        var isLock: Boolean?=true,
        var expirationDate: ZonedDateTime?=null,
        var isEnabled: Boolean?=true)