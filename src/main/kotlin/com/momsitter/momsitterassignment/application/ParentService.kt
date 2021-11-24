package com.momsitter.momsitterassignment.application

import com.momsitter.momsitterassignment.domain.member.MemberRepository
import com.momsitter.momsitterassignment.domain.parent.ParentRepository
import com.momsitter.momsitterassignment.domain.member.getByMemberId
import com.momsitter.momsitterassignment.exception.NotFoundMemberException
import com.momsitter.momsitterassignment.exception.UnRegisteredParentException
import com.momsitter.momsitterassignment.ui.dto.RegisterParentRequest
import com.momsitter.momsitterassignment.ui.dto.RegisterParentResponse
import com.momsitter.momsitterassignment.ui.dto.UpdateParentRequest
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
