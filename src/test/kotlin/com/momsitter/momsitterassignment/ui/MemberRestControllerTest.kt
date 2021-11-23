package com.momsitter.momsitterassignment.ui

import com.momsitter.momsitterassignment.application.AuthenticationService
import com.momsitter.momsitterassignment.application.MemberService
import com.momsitter.momsitterassignment.domain.Gender
import com.momsitter.momsitterassignment.fixture.createMember
import com.momsitter.momsitterassignment.ui.dto.*
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDate

@WebMvcTest(controllers = [MemberRestController::class])
internal class MemberRestControllerTest : RestControllerTest() {
    @MockkBean
    private lateinit var authenticationService: AuthenticationService

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
            .andExpect(content().json(objectMapper.writeValueAsString(ApiResponse.success(response))))
            .andDo(
                document(
                    "member-login",
                    requestFields(
                        fieldWithPath("accountId").description("회원 아이디"),
                        fieldWithPath("password").description("회원 패스워드"),
                    ),
                    responseFields(
                        fieldWithPath("message").description("예외 발생시에 전달되는 메시지"),
                        fieldWithPath("body.token").description("로그인 성공시 발급되는 토큰")
                    )
                )
            )
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
            .andExpect(content().json(objectMapper.writeValueAsString(ApiResponse.success(response))))
            .andDo(
                document(
                    "member-signup",
                    requestFields(
                        fieldWithPath("name").description("회원 이름"),
                        fieldWithPath("birth").description("회원 생년월일"),
                        fieldWithPath("gender").description("회원 성별"),
                        fieldWithPath("accountId").description("회원 아이디"),
                        fieldWithPath("password").description("등록할 비밀번호"),
                        fieldWithPath("confirmPassword").description("입력 비밀번호 확인"),
                        fieldWithPath("email").description("회원 이메일 정보"),
                    ),
                    responseFields(
                        fieldWithPath("message").description("예외 발생시에 전달되는 메시지"),
                        fieldWithPath("body.token").description("로그인 성공시 발급되는 토큰")
                    )
                )
            )
    }

    @DisplayName("로그인한 회원의 상세정보를 조회한다")
    @Test
    internal fun testMemberDetail() {
        //given
        val token = "loginToken"
        val member = createMember(id = 1L)
        val response = MemberResponse(member)
        validInterceptorAndArgumentResolverMocking(member)
        every { memberService.findMemberById(member.id) } returns response

        //when //then
        mockMvc.perform(
            get("/api/members/me")
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
        )
            .andExpect(status().isOk)
            .andExpect(content().json(objectMapper.writeValueAsString(ApiResponse.success(response))))
            .andDo(
                document(
                    "detail login member information",
                    requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("인가 헤더 키")
                    ),
                    responseFields(
                        fieldWithPath("message").description("예외 발생시에 전달되는 메시지"),
                        fieldWithPath("body.id").description("회원번호"),
                        fieldWithPath("body.name").description("회원 이름"),
                        fieldWithPath("body.birth").description("회원 생년월일"),
                        fieldWithPath("body.gender").description("회원 성별"),
                        fieldWithPath("body.accountId").description("회원 아이디"),
                        fieldWithPath("body.email").description("회원 이메일"),
                        fieldWithPath("body.role").description("회원이 가진 권한들"),
                    )
                )
            )
    }
}