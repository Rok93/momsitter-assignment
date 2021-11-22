package com.momsitter.momsitterassignment.domain

import support.BaseEntity
import javax.persistence.*

@Entity
class Sitter(
    @Embedded
    val information: SitterInformation,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member,

    id: Long
) : BaseEntity(id) {

}
