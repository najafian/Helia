package com.helia.oauth2.model.user

import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "user_account")
class UserAccountEntity{
        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        @Column(name = "user_id")
        var userId: Long?=null
        @Column(name = "email")
        var email: String?=null
        @Column(name = "password")
        var password: String?=null
        @ManyToMany
        @JoinTable(
                name = "user_role",
                joinColumns = [JoinColumn(name = "user_id")],
                inverseJoinColumns = [JoinColumn(name = "role_id")])
        var userRoleEntity: Set<RoleEntity>?=null
        @Column(name = "app_id")
        var appId: Long?=null
        @Column(name = "company_id")
        var companyId: Long?=null
        @Column(name = "name")
        var name: String?=null
        @Column(name = "lastName")
        var lastName: String?=null
        @Column(name = "locked")
        var isLocked: Boolean?=null
        @Column(name = "expirationDate")
        var expirationDate: ZonedDateTime?=null
        @Column(name = "enabled")
        var isEnabled: Boolean?=null
        @Column(name = "created")
        var created:ZonedDateTime?=null
        @Column(name = "deleted")
        var deleted:ZonedDateTime?=null
}

