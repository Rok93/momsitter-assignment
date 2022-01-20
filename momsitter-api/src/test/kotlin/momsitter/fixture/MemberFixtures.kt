package com.momsitter.momsitterassignment.fixture

import com.momsitter.momsitterassignment.domain.Gender
import com.momsitter.momsitterassignment.domain.member.Member
import com.momsitter.momsitterassignment.domain.member.Password
import java.time.LocalDate

fun createMember(
    name: String = "김경록",
    birth: LocalDate = LocalDate.of(1993, 9, 5),
    gender: Gender = Gender.MALE,
    accountId: String = "rok93",
    password: Password = Password("password123#"),
    email: String = "printf1004@naver.com",
    id: Long = 0L
): Member {
    //todo: "현재 TestFixture를 core와 api 모듈에서 공통적으로 쓰고있다. 어떻게 처리하는게 좋을까?"
    return Member(name, birth, gender, accountId, password, email, id)
}

