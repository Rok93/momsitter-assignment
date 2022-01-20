package com.momsitter.momsitterassignment.application

import com.momsitter.momsitterassignment.application.dto.RegisterSitterRequest
import com.momsitter.momsitterassignment.application.dto.RegisterSitterResponse
import com.momsitter.momsitterassignment.application.dto.UpdateSitterRequest
import com.momsitter.momsitterassignment.domain.member.MemberRepository
import com.momsitter.momsitterassignment.domain.member.getByMemberId
import com.momsitter.momsitterassignment.domain.sitter.SitterRepository
import com.momsitter.momsitterassignment.exception.NotFoundMemberException
import com.momsitter.momsitterassignment.exception.UnRegisteredSitterException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SitterService(
    private val memberRepository: MemberRepository,
    private val sitterRepository: SitterRepository
) {
    @Transactional
    fun register(memberId: Long, request: RegisterSitterRequest): RegisterSitterResponse {
        val sitter = sitterRepository.save(request.toEntity())
        memberRepository.getByMemberId(memberId).apply {
            registerSitter(sitter)
        }

        return RegisterSitterResponse(sitter.id)
    }

    @Transactional
    fun update(memberId: Long, request: UpdateSitterRequest) {
        val member = memberRepository.findByIdWithSitter(memberId) ?: throw NotFoundMemberException()
        val sitter = member.sitter ?: throw UnRegisteredSitterException()
        sitter.change(request.toEntity())
    }
}
