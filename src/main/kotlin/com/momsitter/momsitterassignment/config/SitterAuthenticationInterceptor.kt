package com.momsitter.momsitterassignment.config

import com.momsitter.momsitterassignment.application.AuthenticationService
import com.momsitter.momsitterassignment.domain.Role
import com.momsitter.momsitterassignment.utils.AuthorizationExtractor
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class SitterAuthenticationInterceptor(
    private val authenticationService: AuthenticationService
) : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (HttpMethod.OPTIONS.matches(request.method)) {
            return true
        }

        val token = AuthorizationExtractor.extract(request)
        authenticationService.validateRoleByToken(token, Role.SITTER)
        return true
    }
}
