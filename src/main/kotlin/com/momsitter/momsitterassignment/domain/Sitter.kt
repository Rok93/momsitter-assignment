package com.momsitter.momsitterassignment.domain

import support.BaseEntity
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.Lob

@Entity
class Sitter(
    @Embedded
    val careAgeGroup: CareAgeGroup,

    @Lob
    @Column(nullable = false)
    val bio: String = "",

    id: Long = 0L
) : BaseEntity(id)
