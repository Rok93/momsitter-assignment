package com.momsitter.momsitterassignment.ui

import com.momsitter.momsitterassignment.application.ParentService
import com.momsitter.momsitterassignment.ui.dto.RegisterParentRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/parents")
class ParentRestController(
    private val parentService: ParentService
) {
    @PostMapping
    fun register(loginMember: LoginMember, @RequestBody request: RegisterParentRequest) { //todo: 확장성을 생각해보자!
        parentService.register(loginMember.id, request)
    }
}
