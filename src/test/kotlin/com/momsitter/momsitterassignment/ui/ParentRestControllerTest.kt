package com.momsitter.momsitterassignment.ui

import com.momsitter.momsitterassignment.application.ParentService
import com.momsitter.momsitterassignment.fixture.createChildData
import com.momsitter.momsitterassignment.fixture.createMember
import com.momsitter.momsitterassignment.ui.dto.RegisterParentRequest
import com.momsitter.momsitterassignment.ui.dto.RegisterParentResponse
import com.ninjasquad.springmockk.MockkBean
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.io.FileDescriptor

@WebMvcTest(controllers = [ParentRestController::class])
internal class ParentRestControllerTest : RestControllerTest() {
    @MockkBean
    private lateinit var parentService: ParentService

    @DisplayName("회원이 부모 회원으로 등록한다")
    @Test
    fun testLoginRequest() {
        //given
        val member = createMember(id = 1L)
        val token = "loginToken"
        val request = RegisterParentRequest("요청 정보", listOf(createChildData()))
        val response = RegisterParentResponse(1L)

        validInterceptorAndArgumentResolverMocking(member)
        every { authenticationService.validateRoleByToken(token, any()) } just Runs
        every { parentService.register(member.id, request) } returns response

        //when //then
        val writeValueAsString = objectMapper.writeValueAsString(request)

        mockMvc.perform(
            post("/api/parents")
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
                    requestFields(
                        fieldWithPath("requestInformation").description("요청 사항"),
                        fieldWithPath("children[].age").description("아기 나이"),
                        fieldWithPath("children[].birth").description("아기 생년월일"),
                        fieldWithPath("children[].gender").description("아기 성별"),
                        fieldWithPath("children[].name").description("아기 이름"),
                        fieldWithPath("children[].note").description("아기 특이사항 (ex. 아토피 등의 질병 여부)"),
                    ),
                    responseHeaders(
                        headerWithName("Location").description("생선된 부모회원을 조회할 수 있는 URI 주소")
                    )
                )
            )
    }
}
