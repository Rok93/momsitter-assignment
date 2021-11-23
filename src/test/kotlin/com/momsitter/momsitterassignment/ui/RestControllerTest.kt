package com.momsitter.momsitterassignment.ui

import com.fasterxml.jackson.databind.ObjectMapper
import com.momsitter.momsitterassignment.config.AuthenticationArgumentResolver
import com.momsitter.momsitterassignment.config.AuthenticationInterceptor
import com.momsitter.momsitterassignment.domain.Member
import com.ninjasquad.springmockk.MockkBean
import com.support.SliceTest
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter

@SliceTest
abstract class RestControllerTest {
    @MockkBean
    lateinit var authenticationArgumentResolver: AuthenticationArgumentResolver

    @MockkBean
    lateinit var authenticationInterceptor: AuthenticationInterceptor

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var mockMvc: MockMvc

    @BeforeEach
    internal fun setUp(
        webApplicationContext: WebApplicationContext,
        restDocumentationContextProvider: RestDocumentationContextProvider
    ) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilter<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
            .apply<DefaultMockMvcBuilder>(
                MockMvcRestDocumentation.documentationConfiguration(
                    restDocumentationContextProvider
                )
            )
            .build()
    }

    fun validInterceptorAndArgumentResolverMocking(member: Member) {
        every { authenticationInterceptor.preHandle(any(), any(), any()) } returns true
        every { authenticationInterceptor.afterCompletion(any(), any(), any(), any()) } just Runs
        every { authenticationInterceptor.postHandle(any(), any(), any(), any()) } just Runs
        every { authenticationArgumentResolver.supportsParameter(any()) } returns true
        every { authenticationArgumentResolver.resolveArgument(any(), any(), any(), any()) } returns LoginMember(member)
    }
}
