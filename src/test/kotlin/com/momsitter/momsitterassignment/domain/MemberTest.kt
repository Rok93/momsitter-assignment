package com.momsitter.momsitterassignment.domain

import com.momsitter.momsitterassignment.fixture.createMember
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MemberTest {

    @DisplayName("회원에게 권한을 부여하는 기능")
    @Test
    fun testAddRole() {
        //given
        val member = createMember()

        //when
        member.addRole(Role.PARENT)

        //then
        assertThat(member.isParent()).isTrue
    }

    @DisplayName("이미 부여한 권한을 회원에게 부여하면 예외가 발생한다")
    @Test
    fun testAddRoleIfAlreadyAddedRole() {
        //given
        val member = createMember()
        member.addRole(Role.PARENT)

        //when //then
        assertThrows<IllegalStateException> { member.addRole(Role.PARENT) }
    }
}
