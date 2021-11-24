package com.momsitter.momsitterassignment.application

import com.momsitter.momsitterassignment.domain.member.Member
import com.momsitter.momsitterassignment.domain.member.MemberRepository
import com.momsitter.momsitterassignment.domain.sitter.CareAgeGroup
import com.momsitter.momsitterassignment.domain.sitter.SitterRepository
import com.momsitter.momsitterassignment.exception.NotFoundMemberException
import com.momsitter.momsitterassignment.fixture.createMember
import com.momsitter.momsitterassignment.fixture.createSitter
import com.momsitter.momsitterassignment.ui.dto.RegisterSitterRequest
import com.momsitter.momsitterassignment.ui.dto.UpdateSitterRequest
import com.support.IntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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

    @Autowired
    private lateinit var sitterRepository: SitterRepository

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

    @DisplayName("시터회원의 정보를 수정하는 기능")
    @Test
    fun testUpdateSitterInfo() {
        //given
        val savedSitter = sitterRepository.save(createSitter()).also {
            member.registerSitter(it)
        }
        flushAndClear()

        val changedCareMinAge = 2
        val changedCareMaxAge = 6
        val changedBio = "애기 예절 교육도 가능합니다."
        val request = UpdateSitterRequest(changedCareMinAge, changedCareMaxAge, changedBio)

        //when
        sitterService.update(member.id, request)
        flushAndClear()

        //then
        val findSitter = sitterRepository.findByIdOrNull(savedSitter.id)!!
        assertThat(findSitter.bio).isEqualTo(changedBio)
        assertThat(findSitter.careAgeGroup).isEqualTo(CareAgeGroup(changedCareMinAge, changedCareMaxAge))
    }

    @DisplayName("존재하지 않은 회원이 자신의 부모정보를 수정하려고하면 예외가 발생한다")
    @Test
    fun testUpdateParentInfoIfNotExistMember() {
        //given
        val request = UpdateSitterRequest(2, 6, "사실 저는 없는 사람입니다.")

        //when //then
        assertThrows<NotFoundMemberException> { sitterService.update(100L, request) }
    }

    private fun flushAndClear() {
        entityManager.flush()
        entityManager.clear()
    }
}
