package com.momsitter.momsitterassignment.config

import com.momsitter.momsitterassignment.application.AuthenticationService
import com.momsitter.momsitterassignment.utils.AuthorizationExtractor
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import java.lang.Exception
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationInterceptor(
    private val authenticationService: AuthenticationService
) : HandlerInterceptor{

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        println("member interceptor 동작!")
        if (HttpMethod.OPTIONS.matches(request.method)) {
            return true
        }

        val token = AuthorizationExtractor.extract(request)
        authenticationService.validateToken(token)
        return true
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        super.afterCompletion(request, response, handler, ex)
        println("member interceptor 종료!")
    }
}
