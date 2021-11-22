package com.momsitter.momsitterassignment.config

import com.momsitter.momsitterassignment.application.AuthenticationService
import com.momsitter.momsitterassignment.ui.LoginMember
import com.momsitter.momsitterassignment.utils.AuthorizationExtractor
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import javax.servlet.http.HttpServletRequest

@Component
class AuthenticationArgumentResolver(
    private val authenticationService: AuthenticationService
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.parameterType == LoginMember::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): LoginMember {
        val request = webRequest.nativeRequest as HttpServletRequest
        val token = AuthorizationExtractor.extract(request)
        return authenticationService.createLoginMemberByToken(token)
    }
}
