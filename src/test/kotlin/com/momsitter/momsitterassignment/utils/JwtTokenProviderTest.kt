package com.momsitter.momsitterassignment.utils

import com.momsitter.momsitterassignment.domain.Role
import com.momsitter.momsitterassignment.fixture.createMember
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class JwtTokenProviderTest {

    private lateinit var jwtTokenProvider: JwtTokenProvider

    @BeforeEach
    internal fun setUp() {
        jwtTokenProvider = JwtTokenProvider(
            Keys.secretKeyFor(SignatureAlgorithm.HS256),
            1_000L * 60
        )
    }

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
        val role = Role.PARENT
        val parentMember = createMember().apply { addRole(role) }
        val token = jwtTokenProvider.createToken(parentMember)

        //when
        val actual = jwtTokenProvider.findMemberFromToken(token)

        //then
        assertThat(actual.id).isEqualTo(parentMember.id)
        assertThat(actual.name).isEqualTo(parentMember.name)
        assertThat(actual.roles).hasSize(2)
        println(actual.roles)
        assertThat(actual.roles).contains(role)
    }
}
