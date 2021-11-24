package com.momsitter.momsitterassignment.application

import com.momsitter.momsitterassignment.domain.Member
import com.momsitter.momsitterassignment.domain.MemberRepository
import com.momsitter.momsitterassignment.domain.ParentRepository
import com.momsitter.momsitterassignment.fixture.createChildData
import com.momsitter.momsitterassignment.fixture.createMember
import com.momsitter.momsitterassignment.ui.dto.RegisterParentRequest
import com.support.IntegrationTest
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import javax.persistence.EntityManager

@IntegrationTest
internal class ParentServiceTest {
    @Autowired
    private lateinit var entityManager: EntityManager

    @Autowired
    private lateinit var memberRepository: MemberRepository

    @Autowired
    private lateinit var parentRepository: ParentRepository

    @Autowired
    private lateinit var parentService: ParentService

    private lateinit var member: Member

    @BeforeEach
    internal fun setUp() {
        member = memberRepository.save(createMember())
    }

    @AfterEach
    internal fun tearDown() {
        parentRepository.deleteAll()
        memberRepository.deleteAll() // member가 삭제되면 고아객체도 같이 삭제될텐데...? 나중에 확인해보기
    }

    @DisplayName("기존 회원이 부모회원으로 등록하는 기능")
    @Test
    fun testRegisterParent() {
        //given
        val request = RegisterParentRequest("요청 정보", listOf(createChildData()))

        //when
        val response = parentService.register(member.id, request)
        flushAndClear()

        //then
        val findMember = memberRepository.findByIdOrNull(member.id)!!
        assertThat(response.id).isNotNull
        assertThat(findMember.isSitter()).isFalse
        assertThat(findMember.isParent()).isTrue
        assertThat(response.id).isEqualTo(findMember.parent!!.id)
    }

    private fun flushAndClear() {
        entityManager.flush()
        entityManager.clear()
    }
}
