package com.momsitter.momsitterassignment.application

import com.momsitter.momsitterassignment.domain.ParentRepository
import com.momsitter.momsitterassignment.ui.dto.RegisterParentRequest
import com.momsitter.momsitterassignment.ui.dto.RegisterParentResponse
import org.springframework.stereotype.Service

@Service
class ParentService(
    private val parentRepository: ParentRepository
) {
    fun register(id: Long, request: RegisterParentRequest): RegisterParentResponse {
        val parent = parentRepository.save(request.toEntity())
        return RegisterParentResponse(parent)
    }
}
