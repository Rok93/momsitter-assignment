package com.momsitter.momsitterassignment.ui

import com.momsitter.momsitterassignment.application.SitterService
import com.momsitter.momsitterassignment.fixture.createMember
import com.momsitter.momsitterassignment.ui.dto.RegisterSitterRequest
import com.momsitter.momsitterassignment.ui.dto.RegisterSitterResponse
import com.ninjasquad.springmockk.MockkBean
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.headers.HeaderDocumentation.*
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
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
            RestDocumentationRequestBuilders.post("/api/sitters")
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValueAsString)
        )
            .andExpect(status().isCreated)
            .andExpect(header().exists("location"))
            .andExpect(header().stringValues("Location", "/api/parents/${response.id}"))
            .andDo(
                document(
                    "member-register parent ",
                    requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("인가 헤더 키")
                    ),
                    requestFields(
                        fieldWithPath("minAge").description("케어 가능한 최소 연령정보"),
                        fieldWithPath("maxAge").description("케어 가능한 최대 연령정보"),
                        fieldWithPath("bio").description("시터 자기소개"),
                    ),
                    responseHeaders(
                        headerWithName("Location").description("생선된 부모회원을 조회할 수 있는 URI 주소")
                    )
                )
            )
    }
}
