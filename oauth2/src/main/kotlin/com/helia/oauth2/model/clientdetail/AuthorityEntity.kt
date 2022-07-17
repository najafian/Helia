package com.helia.oauth2.model.clientdetail

import javax.persistence.*



@Entity
@Table(name = "authority")
data class AuthorityEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authority_id")
    val id:Long,
    @Column(name = "name")
    val name:String,
    @ManyToMany(mappedBy = "clientAuthorities")
    val userAccountEntities: Set<ClientDetailEntity>
)