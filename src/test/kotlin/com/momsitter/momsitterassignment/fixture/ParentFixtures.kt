package com.momsitter.momsitterassignment.fixture

import com.momsitter.momsitterassignment.domain.Age
import com.momsitter.momsitterassignment.domain.parent.Child
import com.momsitter.momsitterassignment.domain.Gender
import com.momsitter.momsitterassignment.domain.parent.Parent
import com.momsitter.momsitterassignment.ui.dto.ChildData
import java.time.LocalDate

fun createParent(
    requestInformation: String = "맞벌이 부부입니다. 등하원 시켜주실 맘시터를 원합니다.",
    children: MutableList<Child> = mutableListOf(createChild()),
    id: Long = 0L
): Parent = Parent(requestInformation, children, id)

fun createChildData(
    age: Int = 2,
    year: Int = 2020,
    month: Int = 1,
    dayOfMonth: Int = 1,
    gender: Gender = Gender.MALE,
    name: String = "김애기",
    note: String = ""
): ChildData {
    return ChildData(createChild(age, year, month, dayOfMonth, gender, name, note))
}

fun createChild(
    age: Int = 2,
    year: Int = 2020,
    month: Int = 1,
    dayOfMonth: Int = 1,
    gender: Gender = Gender.MALE,
    name: String = "김애기",
    note: String = ""
) = Child(Age(age), LocalDate.of(year, month, dayOfMonth), gender, name, note)
