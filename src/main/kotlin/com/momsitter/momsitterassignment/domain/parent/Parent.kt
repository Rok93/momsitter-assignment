package com.momsitter.momsitterassignment.domain.parent

import com.momsitter.momsitterassignment.exception.DuplicatedChildException
import support.BaseEntity
import javax.persistence.*

@Entity
class Parent(
    @Lob
    @Column(nullable = false)
    var requestInformation: String = "",

    @ElementCollection(fetch = FetchType.EAGER)
    var children: MutableList<Child> = mutableListOf(),

    id: Long = 0L
) : BaseEntity(id) {
    fun addChild(child: Child) {
        if (children.contains(child)) {
            throw DuplicatedChildException()
        }
        children.add(child)
    }

    fun change(parent: Parent) {
        this.requestInformation = parent.requestInformation
        this.children = parent.children
    }
}
