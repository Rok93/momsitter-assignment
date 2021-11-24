package com.momsitter.momsitterassignment.application

import com.momsitter.momsitterassignment.domain.Member
import com.momsitter.momsitterassignment.domain.MemberRepository
import com.momsitter.momsitterassignment.fixture.createMember
import com.momsitter.momsitterassignment.ui.dto.RegisterSitterRequest
import com.support.IntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import javax.persistence.EntityManager

@IntegrationTest
internal class SitterServiceTest {
    @Autowired
    private lateinit var entityManager: EntityManager

    @Autowired
    private lateinit var memberRepository: MemberRepository

    @Autowired
    private lateinit var sitterService: SitterService

    private lateinit var member: Member

    @BeforeEach
    internal fun setUp() {
        member = memberRepository.save(createMember())
    }

    @DisplayName("기존 회원이 시터회원으로 등록하는 기능")
    @Test
    internal fun testRegisterSitter() {
        //given
        val request = RegisterSitterRequest(1, 8, "성심성의껏 열심히하겠습니다.")

        //when
        val response = sitterService.register(member.id, request)
        flushAndClear()

        //then
        val findMember = memberRepository.findByIdOrNull(member.id)!!
        assertThat(response.id).isNotNull
        assertThat(findMember.isSitter()).isTrue
        assertThat(findMember.isParent()).isFalse
        assertThat(response.id).isEqualTo(findMember.sitter!!.id)
    }

    private fun flushAndClear() {
        entityManager.flush()
        entityManager.clear()
    }
}
