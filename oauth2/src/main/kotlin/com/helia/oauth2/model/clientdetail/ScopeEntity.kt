package com.helia.oauth2.model.clientdetail


import javax.persistence.*

@Entity
@Table(name = "scope")
data class ScopeEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scope_id")
    val roleId:Long,
    @Column(name = "scope_name")
    val name:String,

    @ManyToMany(mappedBy = "clientScopes")
    val clientDetails: Set<ClientDetailEntity>
     )