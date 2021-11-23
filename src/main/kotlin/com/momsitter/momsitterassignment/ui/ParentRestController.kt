package com.momsitter.momsitterassignment.ui

import com.momsitter.momsitterassignment.application.ParentService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/parents")
class ParentRestController(
    private val parentService: ParentService
) {
    @PostMapping
    fun a() { //todo: 확장성을 생각해보자!

    }

}
