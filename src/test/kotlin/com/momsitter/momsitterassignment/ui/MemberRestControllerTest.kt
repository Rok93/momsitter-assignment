package com.momsitter.momsitterassignment.ui

import com.momsitter.config.RestDocsConfiguration
import com.momsitter.momsitterassignment.application.AuthenticationService
import com.momsitter.momsitterassignment.application.MemberService
import com.momsitter.momsitterassignment.ui.dto.ApiResponse
import com.momsitter.momsitterassignment.ui.dto.LoginRequest
import com.momsitter.momsitterassignment.ui.dto.TokenResponse
import com.momsitter.momsitterassignment.utils.JwtTokenProvider
import com.ninjasquad.springmockk.MockkBean
import com.support.TestEnvironment
import io.mockk.every
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.operation.preprocess.Preprocessors.*
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.post

@Import(RestDocsConfiguration::class)
@WebMvcTest(
    controllers = [MemberRestController::class],
//    includeFilters = [
//        ComponentScan.Filter(type = FilterType.REGEX)
//    ]
)
@ExtendWith(RestDocumentationExtension::class)
@TestEnvironment
internal class MemberRestControllerTest : RestControllerTest() {
    @MockkBean
    private lateinit var authenticationService: AuthenticationService

    @MockkBean
    private lateinit var memberService: MemberService

    @MockkBean
    private lateinit var jwtTokenProvider: JwtTokenProvider

//    @Autowired
//    private lateinit var mockMvc: MockMvc
//
//    @Autowired
//    private lateinit var objectMapper: ObjectMapper

    @DisplayName("로그인을 요청하여 토큰을 발급받는다 - 성공")
    @Test
    fun test() {
        //given
        val request = LoginRequest("test1", "Password123$")

        val response = TokenResponse("test")
        every { authenticationService.login(any(), any()) } returns response

        //when //then
        mockMvc.post("/api/members/login") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isOk() }
            content { json(objectMapper.writeValueAsString(ApiResponse.success(response))) }
        }.andDo {
            document(
                "member-login",
//                preprocessResponse(prettyPrint()),
//                preprocessRequest(prettyPrint())
                requestFields(
                    fieldWithPath("name").description("이름"),
                    fieldWithPath("name").description("팀명"),
                    fieldWithPath("maker").description("브랜드")
                ),
                responseFields(
                    fieldWithPath("id").description("키"),
                    fieldWithPath("name").description("팀명"),
                    fieldWithPath("maker").description("브랜드"),
                    fieldWithPath("year").description("연식"),
                    fieldWithPath("state").description("상태"),
                    fieldWithPath("createdAt").description("생성일시"),
                    fieldWithPath("updatedAt").description("수정일시"),
                    fieldWithPath("_links.self.href").description("조회링크")
                )
            )
        }
    }
}
