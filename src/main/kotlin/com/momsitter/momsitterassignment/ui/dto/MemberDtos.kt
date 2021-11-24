package com.momsitter.momsitterassignment.ui.dto

import com.momsitter.momsitterassignment.domain.Gender
import com.momsitter.momsitterassignment.domain.member.Member
import com.momsitter.momsitterassignment.domain.member.Role
import java.time.LocalDate
import javax.validation.constraints.*

data class MemberResponse(
    val name: String,
    val birth: LocalDate,
    val gender: Gender,
    val accountId: String,
    val email: String,
    val role: Set<Role>,
    val id: Long
) {
    constructor(member: Member) : this(
        member.name,
        member.birth,
        member.gender,
        member.accountId,
        member.email,
        member.roles,
        member.id
    )
}

data class SignupMemberRequest(
    @field: NotNull(message = "이름을 최소 1글자 이상 입력해주세요.")
    val name: String,
    @field: NotNull(message = "생년월일을 입력해주세요.")
    val birth: LocalDate,
    @field: NotNull(message = "이름은 null을 허용하지 않습니다.")
    val gender: Gender,
    @field: NotBlank(message = "아이디는 공백을 허용하지 않습니다.")
    @field: Size(min = 6, max = 20, message = "아이디는 최소 6글자 이상 20자이하로 입력해주세요.")
    val accountId: String,
    @field: NotBlank(message = "비밀번호는 공백을 허용하지 않습니다.")
    @field: Size(min = 8, max = 16, message = "비밀번호는 최소 6글자 이상 16자이하로 입력해주세요.")
    val password: String,
    @field: NotBlank(message = "비밀번호 확인 값은 공백을 허용하지 않습니다.")
    @field: Size(min = 6, max = 16, message = "비밀번호는 최소 6글자 이상 16자이하로 입력해주세요.")
    val confirmPassword: String,
    @field: Email(message = "올바른 이메일 형식이 아닙니다.")
    val email: String
) {
    fun toEntity(): Member = Member(name, birth, gender, accountId, password, email)
}

data class LoginRequest(
    @field: NotBlank(message = "아이디를 입력해주세요!")
    val accountId: String,

    @field: Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#\$%^&*()+|=])[A-Za-z\\d~!@#\$%^&*()+|=]{8,16}\$",
        message = "문자, 숫자 특수 문자를 최소 한개씩 포함한 8자리 이상의 패스워드를 입력해주세요!"
    )
    val password: String
)

data class TokenResponse(val token: String)

data class UpdateMemberRequest(
    @field: NotNull(message = "생년월일을 입력해주세요.")
    val birth: LocalDate,
    @field: NotNull(message = "이름은 null을 허용하지 않습니다.")
    val gender: Gender,
    @field: NotBlank(message = "비밀번호는 공백을 허용하지 않습니다.")
    @field: Size(min = 8, max = 16, message = "비밀번호는 최소 6글자 이상 16자이하로 입력해주세요.")
    val password: String,
    @field: NotBlank(message = "비밀번호 확인 값은 공백을 허용하지 않습니다.")
    @field: Size(min = 6, max = 16, message = "비밀번호는 최소 6글자 이상 16자이하로 입력해주세요.")
    val confirmPassword: String,
    @field: Email(message = "올바른 이메일 형식이 아닙니다.")
    val email: String
)
