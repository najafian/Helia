package com.helia.oauth2.model.user

import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "user_account")
class UserAccountEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id")
        var userId: Long,
        @Column(name = "email")
        var email: String,
        @Column(name = "password")
        var password: String,
        @ManyToMany
        @JoinTable(
                name = "user_role",
                joinColumns = [JoinColumn(name = "user_id")],
                inverseJoinColumns = [JoinColumn(name = "role_id")])
        var userRoleEntity: Set<RoleEntity>?=null,
        @Column(name = "app_id")
        var appId: Long?=0,
        @Column(name = "company_id")
        var companyId: Long?=0,
        @Column(name = "name")
        var name: String?="",
        @Column(name = "lastName")
        var lastName: String?="",
        @Column(name = "locked")
        var isLocked: Boolean?=true,
        @Column(name = "expirationDate")
        var expirationDate: ZonedDateTime?=null,
        @Column(name = "enabled")
        var isEnabled: Boolean?=true,
        @Column(name = "created")
        var created:ZonedDateTime?=null,
        @Column(name = "deleted")
        var deleted:ZonedDateTime?=null
)