package com.momsitter.momsitterassignment.domain

import support.BaseEntity
import javax.persistence.*

@Entity
class Parent(
    @Lob
    @Column(nullable = false)
    val requestInformation: String = "",

    @OneToMany(mappedBy = "parent", cascade = [CascadeType.PERSIST, CascadeType.REMOVE], orphanRemoval = true)
    val children: MutableList<Child> = mutableListOf(),

    id: Long = 0L
) : BaseEntity(id) {
    fun addChild(child: Child) {
        children.add(child)
    }
}
