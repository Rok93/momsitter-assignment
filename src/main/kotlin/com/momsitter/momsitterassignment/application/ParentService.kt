package com.momsitter.momsitterassignment.application

import com.momsitter.momsitterassignment.domain.MemberRepository
import com.momsitter.momsitterassignment.domain.ParentRepository
import com.momsitter.momsitterassignment.domain.getByMemberId
import com.momsitter.momsitterassignment.ui.dto.RegisterParentRequest
import com.momsitter.momsitterassignment.ui.dto.RegisterParentResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ParentService(
    private val memberRepository: MemberRepository,
    private val parentRepository: ParentRepository
) {
    @Transactional
    fun register(id: Long, request: RegisterParentRequest): RegisterParentResponse {
        val parent = parentRepository.save(request.toEntity())
        memberRepository.getByMemberId(id).apply {
            registerParent(parent)
        }

        return RegisterParentResponse(parent.id)
    }
}
