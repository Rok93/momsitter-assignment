package com.momsitter.momsitterassignment.ui.dto

import com.momsitter.momsitterassignment.domain.Age
import com.momsitter.momsitterassignment.domain.Child
import com.momsitter.momsitterassignment.domain.Gender
import com.momsitter.momsitterassignment.domain.Parent
import java.time.LocalDate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class RegisterParentRequest(
    @field: NotNull(message = "요청 정보는 null일 수 없습니다. ")
    val requestInformation: String = "",

    val children: List<ChildData> = emptyList()

) {
    fun toEntity(): Parent = Parent(requestInformation, children.map { it.toChild() }.toMutableList())
}

data class ChildData(
    @field:NotNull
    val age: Age,

    @field: NotNull
    val birth: LocalDate,

    @field:NotNull
    val gender: Gender,

    @field:NotBlank
    val name: String,

    @field:NotNull
    val note: String = "",
) {
    constructor(child: Child) : this(child.age, child.birth, child.gender, child.name, child.note)

    fun toChild(): Child = Child(age, birth, gender, name, note)
}

data class RegisterParentResponse(
    @field:NotNull(message = "요청정보는 null이 될 수 없습니다.")
    val requestInformation: String = "",

    @field:Size(message = "최소 1명이상의 아이를 등록해야합니다.")
    val children: List<ChildData> = emptyList()
) {
    constructor(parent: Parent) : this(parent.requestInformation, parent.children.map { ChildData(it) })
}
