package com.momsitter.momsitterassignment.domain.sitter

import support.BaseEntity
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.Lob

@Entity
class Sitter(
    @Embedded
    var careAgeGroup: CareAgeGroup,

    @Lob
    @Column(nullable = false)
    var bio: String = "",

    id: Long = 0L
) : BaseEntity(id) {
    fun change(sitter: Sitter) {
        this.careAgeGroup = sitter.careAgeGroup
        this.bio = sitter.bio
    }
}
