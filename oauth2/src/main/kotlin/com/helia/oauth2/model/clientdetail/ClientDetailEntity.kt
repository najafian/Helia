package com.helia.oauth2.model.clientdetail


import javax.persistence.*

@Entity
@Table(name = "client_detail")
data class ClientDetailEntity(
        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val Id:Long,
        @Column(name = "resource_id")
    val resourceIds:String,
    @ManyToMany
    @JoinTable(
        name = "client_scopes",
        joinColumns = [JoinColumn(name = "id")],
        inverseJoinColumns = [JoinColumn(name = "scope_id")])
    var clientScopes: Set<ScopeEntity>?=null,

    @ManyToMany
    @JoinTable(
        name = "client_authority",
        joinColumns = [JoinColumn(name = "id")],
        inverseJoinColumns = [JoinColumn(name = "authority_id")])
    var clientAuthorities: Set<AuthorityEntity>?=null,


        @Column(name = "client_id")
    val clientId:String,
        @Column(name = "client_secret")
    val clientSecret:String,
        @Column(name = "redirect_url")
    val redirectUrl:String,
        @Column(name = "grant_type")
    val grantType:String,
)