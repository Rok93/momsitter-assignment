package com.momsitter.ui

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthRestController {
    @GetMapping("/health")
    fun hello() = ResponseEntity.ok("Health Good!")
}