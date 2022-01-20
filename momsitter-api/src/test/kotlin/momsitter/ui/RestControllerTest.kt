package momsitter.ui

import com.fasterxml.jackson.databind.ObjectMapper
import com.momsitter.momsitterassignment.application.AuthenticationService
import com.momsitter.momsitterassignment.application.dto.LoginMember
import com.momsitter.momsitterassignment.domain.member.Member
import com.ninjasquad.springmockk.MockkBean
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import momsitter.config.AuthenticationArgumentResolver
import momsitter.config.AuthenticationInterceptor
import momsitter.support.SliceTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc

@SliceTest
abstract class RestControllerTest {
    @MockkBean
    lateinit var authenticationArgumentResolver: AuthenticationArgumentResolver

    @MockkBean
    lateinit var authenticationInterceptor: AuthenticationInterceptor

    @MockkBean
    lateinit var authenticationService: AuthenticationService

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var mockMvc: MockMvc

    fun validInterceptorAndArgumentResolverMocking(member: Member) {
        every { authenticationInterceptor.preHandle(any(), any(), any()) } returns true
        every { authenticationInterceptor.afterCompletion(any(), any(), any(), any()) } just Runs
        every { authenticationInterceptor.postHandle(any(), any(), any(), any()) } just Runs
        every { authenticationArgumentResolver.supportsParameter(any()) } returns true
        every { authenticationArgumentResolver.resolveArgument(any(), any(), any(), any()) } returns LoginMember(member)
    }
}
