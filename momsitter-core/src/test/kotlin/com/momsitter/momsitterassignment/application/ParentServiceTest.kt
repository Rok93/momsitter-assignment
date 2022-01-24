package com.momsitter.momsitterassignment.application

import com.momsitter.momsitterassignment.application.dto.RegisterParentRequest
import com.momsitter.momsitterassignment.application.dto.UpdateParentRequest
import com.momsitter.momsitterassignment.domain.Gender
import com.momsitter.momsitterassignment.domain.member.Member
import com.momsitter.momsitterassignment.domain.member.MemberRepository
import com.momsitter.momsitterassignment.domain.parent.ParentRepository
import com.momsitter.momsitterassignment.exceptions.NotFoundMemberException
import fixture.createChildData
import fixture.createMember
import fixture.createParent
import com.support.IntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
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
        memberRepository.deleteAll()
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

    @DisplayName("부모회원의 정보를 수정하는 기능")
    @Test
    fun testUpdateParentInfo() {
        //given
        val savedParent = parentRepository.save(createParent())
        member.registerParent(savedParent)
        flushAndClear()

        val changedRequestInformation = "잘 부탁드립니다"
        val changedChildren = listOf(createChildData(2, 2020, 5, 5, Gender.FEMALE, "나은이"))
        val request = UpdateParentRequest(changedRequestInformation, changedChildren)

        //when
        parentService.update(member.id, request)

        //then
        val findParent = parentRepository.findByIdOrNull(savedParent.id)!!
        assertThat(findParent.requestInformation).isEqualTo(changedRequestInformation)
        assertThat(findParent.children[0]).isEqualTo(changedChildren[0].toChild())
    }

    @DisplayName("존재하지 않은 회원이 자신의 부모정보를 수정하려고하면 예외가 발생한다")
    @Test
    fun testUpdateParentInfoIfNotExistMember() {
        //given
        val request = UpdateParentRequest("잘 부탁드립니다", listOf(createChildData()))

        //when //then
        assertThrows<NotFoundMemberException> { parentService.update(100L, request) }
    }

    private fun flushAndClear() {
        entityManager.flush()
        entityManager.clear()
    }
}
