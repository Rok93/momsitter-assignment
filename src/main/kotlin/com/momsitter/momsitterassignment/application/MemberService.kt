package com.momsitter.momsitterassignment.application

import com.momsitter.momsitterassignment.domain.member.MemberRepository
import com.momsitter.momsitterassignment.domain.member.getByMemberId
import com.momsitter.momsitterassignment.ui.dto.MemberResponse
import com.momsitter.momsitterassignment.ui.dto.UpdateMemberRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {
    fun findMemberById(id: Long): MemberResponse {
        val member = memberRepository.getByMemberId(id)
        return MemberResponse(member)
    }

    @Transactional
    fun update(id: Long, request: UpdateMemberRequest) {
        require(request.password == request.confirmPassword) { "입력한 비밀번호가 서로 일치하지 않습니다." }
        val member = memberRepository.getByMemberId(id)
        member.update(request.birth, request.password, request.email, request.gender)
    }
}
