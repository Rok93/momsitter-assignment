package com.momsitter.momsitterassignment.domain.member

import com.momsitter.momsitterassignment.domain.parent.Parent
import com.momsitter.momsitterassignment.domain.sitter.CareAgeGroup
import com.momsitter.momsitterassignment.domain.sitter.Sitter
import com.momsitter.momsitterassignment.exceptions.AlreadyRegisteredException
import com.momsitter.momsitterassignment.exceptions.NotValidPasswordException
import fixture.createMember
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

@DisplayName("회원 테스트")
class MemberTest {

    @DisplayName("회원에게 권한을 부여하는 기능")
    @Test
    fun testAddRole() {
        //given
        val member = createMember()

        //when
        member.registerParent(Parent())

        //then
        assertThat(member.isParent()).isTrue
    }

    @DisplayName("이미 부여한 권한을 회원에게 부여하면 예외가 발생한다")
    @Test
    fun testAddRoleIfAlreadyAddedRole() {
        //given
        val member = createMember()
        member.registerSitter(Sitter(CareAgeGroup(1, 10)))

        //when //then
        assertThrows<AlreadyRegisteredException> { member.registerSitter(Sitter(CareAgeGroup(1, 10))) }
    }

    @DisplayName("비밀번호가 일치하는지 확인하는 기능")
    @Test
    internal fun testValidatePassword() {
        //given
        val password = "1q2w3e4r!"
        val member = createMember(password = Password(password))

        ///when //then
        assertDoesNotThrow { member.validatePassword(password) }
    }

    @DisplayName("비밀번호가 일치하지 않으면 예외를 발생시킨다")
    @Test
    internal fun testValidatePasswordIfNotEqualsPassword() {
        //given
        val inputPassword = "1q2w3e4r!"
        val member = createMember(password = Password("1q2q3q4q!"))

        ///when //then
        assertThrows<NotValidPasswordException> { member.validatePassword(inputPassword) }
    }
}
