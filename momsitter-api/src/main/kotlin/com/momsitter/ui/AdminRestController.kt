package com.momsitter.ui

import com.momsitter.common.dto.CommonResponse
import com.momsitter.momsitterassignment.application.MemberService
import com.momsitter.momsitterassignment.application.dto.MemberResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin")
class AdminRestController(
    private val memberService: MemberService
) {
    @GetMapping("/members")
    @Operation(summary = "모든 회원 정보를 조회", description = "모든 회원 정보 조회")
    @ApiResponses(ApiResponse(responseCode = "200", description = "OK"))
    fun findAll(): ResponseEntity<CommonResponse<List<MemberResponse>>> { //todo: 테스트 코드 작성
        val responses = memberService.findAll()
        return ResponseEntity.ok(CommonResponse.success(responses))
    }
}