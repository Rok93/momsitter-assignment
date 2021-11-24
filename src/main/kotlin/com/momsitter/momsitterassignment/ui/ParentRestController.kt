package com.momsitter.momsitterassignment.ui

import com.momsitter.momsitterassignment.application.ParentService
import com.momsitter.momsitterassignment.ui.dto.RegisterParentRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/parents")
class ParentRestController(
    private val parentService: ParentService
) {
    @PostMapping
    fun register(loginMember: LoginMember, @RequestBody request: RegisterParentRequest): ResponseEntity<Unit> {
        val response = parentService.register(loginMember.id, request)
        return ResponseEntity.created(URI.create("/api/parents/${response.id}")).build()
    }
}
