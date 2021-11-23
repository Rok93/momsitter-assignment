package com.momsitter.momsitterassignment.domain

import com.fasterxml.jackson.annotation.JsonFormat
import support.BaseEntity
import java.time.LocalDate
import javax.persistence.*

@Entity
class Child(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    val parent: Parent,

    @Embedded
    val age: Age,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    @Column(nullable = false)
    val birth: LocalDate,

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    val gender: Gender,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val note: String = "",

    id: Long = 0L
) : BaseEntity(id)
