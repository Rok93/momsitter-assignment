package com.momsitter.momsitterassignment.ui.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class LoginRequest(
    @field: NotBlank(message = "아이디를 입력해주세요!")
    val accountId: String,

    @field: Pattern(
        regexp = "^(?=.*\\d)(?=.*[\$@\$!%*#?&])[A-Za-z\\d\$@\$!%*#?&]{8,}\$",
        message = "문자, 숫자 특수 문자를 최소 한개씩 포함한 8자리 이상의 패스워드를 입력해주세요!"
    )
    val password: String
)

data class TokenResponse(val token: String)
