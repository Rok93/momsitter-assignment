package com.momsitter.ui

import com.momsitter.momsitterassignment.application.ParentService
import com.momsitter.momsitterassignment.application.dto.LoginMember
import com.momsitter.momsitterassignment.application.dto.RegisterParentRequest
import com.momsitter.momsitterassignment.application.dto.UpdateParentRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/parents")
class ParentRestController(
    private val parentService: ParentService
) {
    @PostMapping
    @Operation(summary = "부모회원으로 등록하는 기능", description = "로그인한 멤버가 부모회원으로 등록하는 기능")
    @ApiResponses(ApiResponse(responseCode = "201", description = "CREATED"))
    fun registerParent(loginMember: LoginMember, @RequestBody request: RegisterParentRequest): ResponseEntity<Unit> {
        val response = parentService.register(loginMember.id, request)
        return ResponseEntity.created(URI.create("/api/parents/${response.id}")).build()
    }

    @PutMapping
    @Operation(summary = "부모회원 정보수정 기능", description = "로그인한 부모회원이 자신의 부모회원 정보를 수정하는 기능")
    @ApiResponses(ApiResponse(responseCode = "204", description = "NO_CONTENT"))
    fun updateParentInfo(loginMember: LoginMember, @RequestBody request: UpdateParentRequest): ResponseEntity<Unit> {
        parentService.update(loginMember.id, request)
        return ResponseEntity.noContent().build()
    }
}
