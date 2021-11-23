package com.momsitter.momsitterassignment.domain

import javax.persistence.Column
import javax.persistence.Embeddable

private const val MIN_AGE = 1

@Embeddable
data class Age(
    @Column(nullable = false)
    val value: Int = MIN_AGE
) {
    init {
        check(value >= MIN_AGE) { "나이는 1이상의 숫자만 가능합니다." }
    }

    operator fun Age.inc(): Age = Age(this.value + 1) // 1년마다 스케줄링으로 아이 나이 한살씩 올려주기!

    operator fun compareTo(other: Age): Int = value.compareTo(other.value)

    operator fun rangeTo(other: Age): IntRange = value..other.value
}
