package com.momsitter.momsitterassignment.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("패스워드 테스트")
internal class PasswordTest {

    @DisplayName("입력된 비밀번호가 요구사항을 만족하면 객체를 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = ["1Q2w3e4r!", "Password1@", "#1wwwwwwww", "#1ABCDEFGHIJKLMN"])
    internal fun testInitPassword(value: String) {
        //when
        val password = Password(value)

        //then
        assertThat(password.value).isEqualTo(value)
    }

    @DisplayName("입력된 비밀번호가 요구사항을 만족하지 않으면 객체를 생성할 수 없다")
    @ParameterizedTest
    @ValueSource(strings = ["1q2w3e4r", "Password#", "#11111111", "#1ABCDEFGHIJKLMNO", "#1ABCDEFGHIJKLMNOPQRSTUVWXYZ"])
    internal fun testInitPasswordIfUnsatisfiedValue(value: String) {
        //when //then
        assertThrows<IllegalArgumentException> { Password(value) }
    }
}
