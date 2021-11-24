package com.momsitter.momsitterassignment.domain

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import javax.persistence.*

@Embeddable
data class Child(
    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "age", nullable = false))
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
    val note: String = ""
)
