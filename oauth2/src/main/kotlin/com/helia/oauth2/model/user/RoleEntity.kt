package com.helia.oauth2.model.user

import javax.persistence.*

@Entity
@Table(name = "role")
data class RoleEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    val roleId:Long,
    @Column(name = "role_name")
    val name:String,

    @ManyToMany(mappedBy = "userRoleEntity")
    val userAccountEntities: Set<UserAccountEntity>

     )