package com.momsitter.momsitterassignment.ui

import com.momsitter.momsitterassignment.application.SitterService
import com.momsitter.momsitterassignment.ui.dto.RegisterSitterRequest
import com.momsitter.momsitterassignment.ui.dto.UpdateSitterRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/sitters")
class SitterRestController(
    private val sitterService: SitterService
) {
    @PostMapping
    @Operation(summary = "시터회원으로 등록하는 기능", description = "로그인한 멤버가 시터회원으로 등록하는 기능")
    @ApiResponses(ApiResponse(responseCode = "201", description = "CREATED"))
    fun register(loginMember: LoginMember, @RequestBody request: RegisterSitterRequest): ResponseEntity<Unit> {
        val response = sitterService.register(loginMember.id, request)
        return ResponseEntity.created(URI.create("/api/parents/${response.id}")).build()
    }

    @PutMapping
    @Operation(summary = "시터회원 정보수정 기능", description = "로그인한 시터회원이 자신의 부모회원 정보를 수정하는 기능")
    @ApiResponses(ApiResponse(responseCode = "204", description = "NO_CONTENT"))
    fun updateParentInfo(loginMember: LoginMember, @RequestBody request: UpdateSitterRequest): ResponseEntity<Unit> {
        sitterService.update(loginMember.id, request)
        return ResponseEntity.noContent().build()
    }
}
