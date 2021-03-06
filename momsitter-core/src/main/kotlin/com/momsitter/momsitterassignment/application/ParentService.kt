package com.momsitter.momsitterassignment.application

import com.momsitter.momsitterassignment.application.dto.RegisterParentRequest
import com.momsitter.momsitterassignment.application.dto.RegisterParentResponse
import com.momsitter.momsitterassignment.application.dto.UpdateParentRequest
import com.momsitter.momsitterassignment.domain.member.MemberRepository
import com.momsitter.momsitterassignment.domain.member.getByMemberId
import com.momsitter.momsitterassignment.domain.parent.ParentRepository
import com.momsitter.momsitterassignment.exceptions.NotFoundMemberException
import com.momsitter.momsitterassignment.exceptions.UnRegisteredParentException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ParentService(
    private val memberRepository: MemberRepository,
    private val parentRepository: ParentRepository
) {
    @Transactional
    fun register(memberId: Long, request: RegisterParentRequest): RegisterParentResponse {
        val parent = parentRepository.save(request.toEntity())
        memberRepository.getByMemberId(memberId).apply {
            registerParent(parent)
        }

        return RegisterParentResponse(parent.id)
    }

    @Transactional
    fun update(memberId: Long, request: UpdateParentRequest) {
        val member = memberRepository.findByIdWithParent(memberId) ?: throw NotFoundMemberException()
        val parent = member.parent ?: throw UnRegisteredParentException()
        parent.change(request.toEntity())
    }
}
