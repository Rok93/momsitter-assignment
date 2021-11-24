package com.momsitter.momsitterassignment.ui

import com.momsitter.momsitterassignment.application.SitterService
import com.momsitter.momsitterassignment.ui.dto.RegisterSitterRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/sitters")
class SitterRestController(
    private val sitterService: SitterService
) {
    @PostMapping
    fun register(loginMember: LoginMember, @RequestBody request: RegisterSitterRequest): ResponseEntity<Unit> {
        val response = sitterService.register(loginMember.id, request)
        return ResponseEntity.created(URI.create("/api/parents/${response.id}")).build()
    }
}
