package com.momsitter.momsitterassignment.domain

import support.BaseEntity
import javax.persistence.*

@Entity
class Parent(
    @Lob
    @Column(nullable = false)
    val requestInformation: String = "",

    @ElementCollection(fetch = FetchType.EAGER) // 여기 어떻게 해야할까...?
    val children: MutableList<Child> = mutableListOf(),

    id: Long = 0L
) : BaseEntity(id) {
    fun addChild(child: Child) {
        children.add(child)
    }
}
