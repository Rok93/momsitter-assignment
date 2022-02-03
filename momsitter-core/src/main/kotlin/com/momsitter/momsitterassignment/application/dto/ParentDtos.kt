package com.momsitter.momsitterassignment.application.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.momsitter.momsitterassignment.domain.Age
import com.momsitter.momsitterassignment.domain.parent.Child
import com.momsitter.momsitterassignment.domain.Gender
import com.momsitter.momsitterassignment.domain.parent.Parent
import java.time.LocalDate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class RegisterParentRequest(
    @field: NotNull(message = "요청 정보는 null일 수 없습니다. ")
    val requestInformation: String = "",

    @field:Size(min = 1, message = "최소 1명이상의 아이를 등록해야합니다.")
    val children: List<ChildData> = emptyList()
) {
    fun toEntity(): Parent = Parent(requestInformation, children.map { it.toChild() }.toMutableList())
}

data class UpdateParentRequest(
    @field: NotNull(message = "요청 정보는 null일 수 없습니다. ")
    val requestInformation: String = "",

    @field:Size(min = 1, message = "최소 1명이상의 아이를 등록해야합니다.")
    val children: List<ChildData> = emptyList()
) {
    fun toEntity(): Parent = Parent(requestInformation, children.map { it.toChild() }.toMutableList())
}

data class ChildData(
    @field:NotNull
    val age: Int,

    @field: JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    @field: NotNull
    val birth: LocalDate,

    @field:NotNull
    val gender: Gender,

    @field:NotBlank
    val name: String,

    @field:NotNull
    val note: String = "",
) {
    constructor(child: Child) : this(child.age.value, child.birth, child.gender, child.name, child.note)

    fun toChild(): Child = Child(Age(age), birth, gender, name, note)
}

data class RegisterParentResponse(
    val id: Long
)
