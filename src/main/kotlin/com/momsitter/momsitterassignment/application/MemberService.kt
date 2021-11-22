package com.momsitter.momsitterassignment.application

import com.momsitter.momsitterassignment.domain.MemberRepository
import com.momsitter.momsitterassignment.ui.dto.MemberResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {
    fun findMemberById(id: Long): MemberResponse {
        val member = memberRepository.getById(id)
        return MemberResponse(member)
    }
}
