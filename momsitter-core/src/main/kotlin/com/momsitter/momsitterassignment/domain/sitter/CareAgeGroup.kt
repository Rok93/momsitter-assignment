package com.momsitter.momsitterassignment.domain.sitter

import com.momsitter.momsitterassignment.domain.Age
import javax.persistence.AttributeOverride
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Embedded

@Embeddable
data class CareAgeGroup(
    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "min_age"))
    val minAge: Age,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "max_age"))
    val maxAge: Age
) {
    init {
        check(minAge <= maxAge) { "최대연령은 최소연령보다 크거나 같아야합니다." }
    }

    constructor(minAge: Int, maxAge: Int) : this(Age(minAge), Age(maxAge))
}
