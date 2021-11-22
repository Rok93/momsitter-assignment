package com.momsitter.momsitterassignment.fixture

import com.momsitter.momsitterassignment.domain.Gender
import com.momsitter.momsitterassignment.domain.Member
import com.momsitter.momsitterassignment.domain.Role
import java.time.LocalDate

fun createMember(
    name: String = "김경록",
    birth: LocalDate = LocalDate.of(1993, 9, 5),
    gender: Gender = Gender.MALE,
    accountId: String = "rok93",
    password: String = "password123#",
    email: String = "printf1004@naver.com",
    role: Role = Role.SITTER,
    id: Long = 0L
): Member {
    return Member(name, birth, gender, accountId, password, email, id).apply {
        addRole(role)
    }
}
