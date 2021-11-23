package com.momsitter.momsitterassignment.ui

import com.momsitter.momsitterassignment.application.AuthenticationService
import com.momsitter.momsitterassignment.application.MemberService
import com.momsitter.momsitterassignment.ui.dto.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/members")
class MemberRestController(
    private val authenticationService: AuthenticationService,
    private val memberService: MemberService
) {
    @PostMapping("/signup")
    fun signup(@RequestBody @Valid request: RegisterMemberRequest): ResponseEntity<ApiResponse<TokenResponse>> {
        val token = authenticationService.generateTokenByRegister(request)
        return ResponseEntity.ok().body(ApiResponse.success(token))
    }

    @PostMapping("/login")
    fun generateToken(@RequestBody @Valid request: LoginRequest): ResponseEntity<ApiResponse<TokenResponse>> {
        val tokenResponse = authenticationService.login(request.accountId, request.password);
        return ResponseEntity.ok(ApiResponse.success(tokenResponse))
    }

    @GetMapping("/me")
    fun memberDetail(loginMember: LoginMember): ResponseEntity<MemberResponse> {
        val memberResponse = memberService.findMemberById(loginMember.id)
        return ResponseEntity.ok(memberResponse)
    }

    @PutMapping("/me")
    fun update(loginMember: LoginMember, @RequestBody request: UpdateMemberRequest): ResponseEntity<Any> {
        memberService.update(loginMember.id, request)
        return ResponseEntity.noContent().build()
    }
}
