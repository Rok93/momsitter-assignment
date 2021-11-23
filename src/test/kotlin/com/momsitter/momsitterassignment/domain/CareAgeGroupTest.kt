package com.momsitter.momsitterassignment.domain

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class CareAgeGroupTest {

    @DisplayName("CareAgeGroup의 최소 연령이 최대연령보다 크면 예외를 발생시킨다")
    @ParameterizedTest
    @CsvSource(value = ["2,1", "10, 2"])
    fun testInitCareAgeGroupIfMinAgeBiggerThanMaxAge(minAge: Int, maxAge: Int) {
        //when //then
        assertThrows<IllegalStateException> { CareAgeGroup(minAge, maxAge) }
    }

    @DisplayName("CareAgeGroup의 최소 연령 혹은 최대연령이 최소 나이보다 작으면 예외를 발생시킨다")
    @ParameterizedTest
    @CsvSource(value = ["0, 1", "-1, 2", "0, 0"])
    fun testInitCareAgeGroupIfMinAgeOrMaxAgeIsNegativeNumberClosedZero(minAge: Int, maxAge: Int) {
        //when //then
        assertThrows<IllegalStateException> { CareAgeGroup(minAge, maxAge) }
    }
}
