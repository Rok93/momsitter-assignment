package com.momsitter.ui

import com.momsitter.common.dto.CommonResponse
import com.momsitter.momsitterassignment.application.MemberService
import com.momsitter.momsitterassignment.application.dto.*
import com.momsitter.momsitterassignment.domain.Gender
import com.ninjasquad.springmockk.MockkBean
import fixture.createMember
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate

@WebMvcTest(controllers = [MemberRestController::class])
internal class MemberRestControllerTest : RestControllerTest() {

    @MockkBean
    private lateinit var memberService: MemberService

    @DisplayName("로그인을 요청하여 토큰을 발급받는다 - 성공")
    @Test
    fun testLoginRequest() {
        //given
        val request = LoginRequest("test1", "Password123$")

        val response = TokenResponse("test")
        every { authenticationService.login(request.accountId, request.password) } returns response

        //when //then
        mockMvc.perform(
            post("/api/members/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(content().json(objectMapper.writeValueAsString(CommonResponse.success(response))))
    }

    @DisplayName("회원가입 요청을 하여 토큰을 발급받는다 - 성공")
    @Test
    fun testSignupRequest() {
        //given
        val request = SignupMemberRequest(
            "김경록", LocalDate.of(1993, 9, 5), Gender.MALE, "rok936",
            "password1@", "password1@", "printf1004@naver.com"
        )

        val response = TokenResponse("test")
        every { authenticationService.generateTokenBySignup(request) } returns response

        //when //then
        mockMvc.perform(
            post("/api/members/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(content().json(objectMapper.writeValueAsString(CommonResponse.success(response))))
    }

    @DisplayName("로그인한 회원의 상세정보를 조회한다")
    @Test
    internal fun testMemberDetail() {
        //given
        val token = "loginToken"
        val member = createMember(id = 1L) //todo: TestFixture 조합들은 TestModule을 만드는게 좋을까...?
        val response = MemberResponse(member)
        validInterceptorAndArgumentResolverMocking(member)
        every { memberService.findMemberById(member.id) } returns response

        //when //then
        mockMvc.perform(
            get("/api/members/me")
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
        )
            .andExpect(status().isOk)
            .andExpect(content().json(objectMapper.writeValueAsString(CommonResponse.success(response))))
    }

    @DisplayName("로그인한 회원의 정보를 수정한다")
    @Test
    internal fun testUpdate() {
        //given
        val token = "loginToken"
        val member = createMember(id = 1L)
        val request = UpdateMemberRequest(
            LocalDate.of(1990, 1, 1), Gender.MALE,
            "newPassword!", "newPassword!", "changed@email.com"
        )

        validInterceptorAndArgumentResolverMocking(member)
        every { memberService.update(member.id, request) } just Runs

        //when //then
        mockMvc.perform(
            put("/api/members/me")
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isNoContent)
    }
}
