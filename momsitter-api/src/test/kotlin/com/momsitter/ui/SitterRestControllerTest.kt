package com.momsitter.ui

import com.momsitter.momsitterassignment.application.SitterService
import com.momsitter.momsitterassignment.application.dto.RegisterSitterRequest
import com.momsitter.momsitterassignment.application.dto.RegisterSitterResponse
import com.momsitter.momsitterassignment.application.dto.UpdateSitterRequest
import com.momsitter.momsitterassignment.exceptions.AuthorizationHeaderNotFoundException
import com.momsitter.momsitterassignment.exceptions.UnRegisteredSitterException
import com.ninjasquad.springmockk.MockkBean
import fixture.createMember
import fixture.createSitter
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [SitterRestController::class])
internal class SitterRestControllerTest : RestControllerTest() {
    @MockkBean
    private lateinit var sitterService: SitterService

    @DisplayName("회원이 시터 회원으로 등록한다")
    @Test
    fun testRegisterSitterMember() {
        //given
        val member = createMember(id = 1L)
        val token = "loginToken"
        val request = RegisterSitterRequest(1, 10, "성심성의껏 열심히하겠습니다.")
        val response = RegisterSitterResponse(1L)

        validInterceptorAndArgumentResolverMocking(member)
        every { authenticationService.validateRoleByToken(token, any()) } just Runs
        every { sitterService.register(member.id, request) } returns response

        //when //then
        val writeValueAsString = objectMapper.writeValueAsString(request)

        mockMvc.perform(
            post("/api/sitters")
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValueAsString)
        )
            .andExpect(status().isCreated)
            .andExpect(header().exists("location"))
            .andExpect(header().stringValues("Location", "/api/parents/${response.id}"))
    }

    @DisplayName("회원이 자신의 시터정보를 수정한다")
    @Test
    fun testUpdateSitterInfo() {
        //given
        val member = createMember(id = 1L).apply {
            registerSitter(createSitter(id = 1L))
        }
        val token = "loginToken"
        val request = UpdateSitterRequest(1, 5, "애기들이랑 잘 놀아줍니다!")

        every { authenticationService.validateRoleByToken(token, any()) } just Runs
        every { sitterService.update(member.id, request) } just Runs

        validInterceptorAndArgumentResolverMocking(member)

        //when //then
        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/sitters")
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isNoContent)
    }

    @DisplayName("시터회원이 아닌 회원이 자신의 시터정보를 수정하려고하면 예외가 발생한다")
    @Test
    fun testUpdateParentInfoIfLoginUserNotRegisteredSitterMember() {
        //given
        val notSitterMember = createMember(id = 1L)
        val token = "loginToken"
        val request = UpdateSitterRequest(1, 5, "애기들이랑 잘 놀아줍니다!")

        every { authenticationService.validateRoleByToken(token, any()) } just Runs
        every { sitterService.update(notSitterMember.id, request) } throws UnRegisteredSitterException()

        validInterceptorAndArgumentResolverMocking(notSitterMember)

        //when //then
        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/sitters")
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isUnauthorized)
    }

    @DisplayName("로그인하지 않은 사람이 자신의 시터정보를 수정하려고하면 예외가 발생한다")
    @Test
    fun testUpdateSitterInfoIfNotLoginMember() {
        //given
        val request = UpdateSitterRequest(1, 5, "애기들이랑 잘 놀아줍니다!")

        every {
            authenticationArgumentResolver.resolveArgument(
                any(),
                any(),
                any(),
                any()
            )
        } throws AuthorizationHeaderNotFoundException()
        every { authenticationInterceptor.preHandle(any(), any(), any()) } returns true
        every { authenticationInterceptor.afterCompletion(any(), any(), any(), any()) } just Runs
        every { authenticationService.validateRoleByToken(any(), any()) } throws AuthorizationHeaderNotFoundException()

        //when //then
        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/sitters")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isUnauthorized)
    }
}
