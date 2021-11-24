package com.momsitter.momsitterassignment.application

import com.momsitter.momsitterassignment.domain.MemberRepository
import com.momsitter.momsitterassignment.domain.SitterRepository
import com.momsitter.momsitterassignment.domain.getByMemberId
import com.momsitter.momsitterassignment.ui.dto.RegisterSitterRequest
import com.momsitter.momsitterassignment.ui.dto.RegisterSitterResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SitterService(
    private val memberRepository: MemberRepository,
    private val sitterRepository: SitterRepository
) {
    @Transactional
    fun register(id: Long, request: RegisterSitterRequest): RegisterSitterResponse {
        val sitter = sitterRepository.save(request.toEntity())
        memberRepository.getByMemberId(id).apply {
            registerSitter(sitter)
        }

        return RegisterSitterResponse(sitter.id)
    }
}
