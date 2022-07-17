package com.helia.oauth2.model.clientdetail


import javax.persistence.*

@Entity
@Table(name = "client_detail")
class ClientDetailEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    var id: Long?=null
    @Column(name = "resource_id")
    var resourceIds: String?=null
    @ManyToMany
    @JoinTable(
        name = "client_scopes",
        joinColumns = [JoinColumn(name = "id")],
        inverseJoinColumns = [JoinColumn(name = "scope_id")]
    )
    var clientScopes: Set<ScopeEntity>? = null

    @ManyToMany
    @JoinTable(
        name = "client_authority",
        joinColumns = [JoinColumn(name = "id")],
        inverseJoinColumns = [JoinColumn(name = "authority_id")]
    )
    var clientAuthorities: Set<AuthorityEntity>? = null
    @Column(name = "client_id")
    var clientId: String?=null
    @Column(name = "client_secret")
    var clientSecret: String?=null
    @Column(name = "redirect_url")
    var redirectUrl: String?=null
    @Column(name = "grant_type")
    var grantType: String?=null
}
