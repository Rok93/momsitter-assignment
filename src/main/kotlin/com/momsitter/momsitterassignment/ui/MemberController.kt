package com.momsitter.momsitterassignment.ui

import com.momsitter.momsitterassignment.application.MemberService
import com.momsitter.momsitterassignment.ui.dto.MemberResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/members")
class MemberController(
    private val memberService: MemberService
) {
    @GetMapping("/me")
    fun memberDetail(loginMember: LoginMember): ResponseEntity<MemberResponse> {
        val memberResponse = memberService.findMemberById(loginMember.id)
        return ResponseEntity.ok(memberResponse)
    }

}
