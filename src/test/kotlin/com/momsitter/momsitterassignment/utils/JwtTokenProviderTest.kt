package com.momsitter.momsitterassignment.utils

import com.momsitter.momsitterassignment.domain.parent.Parent
import com.momsitter.momsitterassignment.domain.member.Role
import com.momsitter.momsitterassignment.fixture.createMember
import com.support.IntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired

@IntegrationTest
internal class JwtTokenProviderTest {

    @Autowired
    private lateinit var jwtTokenProvider: JwtTokenProvider

    @DisplayName("여러가지 id값 범위에 대한 JWT 생성 및 파싱 테스트")
    @ParameterizedTest
    @ValueSource(longs = [0L, 1L, 100L, 10_000L, 2_147_483_647L, 1_000_000_000_000_000_000L])
    fun testFindMemberFromTokenAboutManyOfIdNumbers(id: Long) {
        //given
        val member = createMember(id = id)
        val token = jwtTokenProvider.createToken(member)

        //when
        val actual = jwtTokenProvider.findMemberFromToken(token)

        //then
        assertThat(actual.id).isEqualTo(member.id)
        assertThat(actual.name).isEqualTo(member.name)
    }

    @DisplayName("여러가지 Role에 대한 JWT 생성 및 파싱 테스트")
    @Test
    fun testFindMemberFromTokenAboutManyOfIdNumbers() {
        //given
        val parentMember = createMember().apply { registerParent(Parent()) }
        val token = jwtTokenProvider.createToken(parentMember)

        //when
        val actual = jwtTokenProvider.findMemberFromToken(token)

        //then
        assertThat(actual.id).isEqualTo(parentMember.id)
        assertThat(actual.name).isEqualTo(parentMember.name)
        assertThat(actual.roles).hasSize(1)
        assertThat(actual.roles).contains(Role.PARENT)
    }
}
