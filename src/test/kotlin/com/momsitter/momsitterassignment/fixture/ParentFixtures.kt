package com.momsitter.momsitterassignment.fixture

import com.momsitter.momsitterassignment.domain.Age
import com.momsitter.momsitterassignment.domain.Child
import com.momsitter.momsitterassignment.domain.Gender
import com.momsitter.momsitterassignment.ui.dto.ChildData
import java.time.LocalDate

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
