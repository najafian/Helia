package com.helia.oauth2.model.user

import javax.persistence.*

@Entity
@Table(name = "role")
data class RoleEntity (
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "role_id")
    var roleId:Long?=null,
    @Column(name = "role_name")
    var name:String?=null,

    @ManyToMany(mappedBy = "userRoleEntity")
    var userAccountEntities: Set<UserAccountEntity>?=null

     )