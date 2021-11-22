package com.momsitter.momsitterassignment.domain

import support.BaseEntity
import javax.persistence.*

@Entity
class Parent(
    @Embedded
    val information: ParentInformation,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member,

    id: Long
) : BaseEntity(id) {
    val childrenInfo
        get() = information.childrenInfo
    val requestContent
        get() = information.requestContent
}
