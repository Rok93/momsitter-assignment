package com.momsitter.momsitterassignment.domain.member

import javax.persistence.Column
import javax.persistence.Embeddable

private val PASSWORD_REGEX: Regex = Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#\$%^&*()+|=])[A-Za-z\\d~!@#\$%^&*()+|=]{8,16}\$")

@Embeddable
data class Password(
    @Column(length = 8, nullable = false)
    val value: String
) {
    init {
        require(PASSWORD_REGEX.containsMatchIn(value)) { "문자, 숫자 특수 문자를 최소 한개씩 포함한 8자리 이상의 패스워드를 입력해주세요!" }
    }
}
