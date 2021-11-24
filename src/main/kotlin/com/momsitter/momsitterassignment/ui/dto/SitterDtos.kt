package com.momsitter.momsitterassignment.ui.dto

import com.momsitter.momsitterassignment.domain.CareAgeGroup
import com.momsitter.momsitterassignment.domain.Sitter
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class RegisterSitterRequest(
    @field:Min(value = 1L, message = "나이는 최소 1이상의 숫자여야합니다.")
    val minAge: Int,
    @field:Min(value = 1L, message = "나이는 최소 1이상의 숫자여야합니다.")
    val maxAge: Int,
    @field:NotNull(message = "자기소개는 null을 허용하지 않습니다.")
    val bio: String = "",
) {
    fun toEntity(): Sitter = Sitter(CareAgeGroup(minAge, maxAge), bio)
}

data class RegisterSitterResponse(
    val id: Long
)

