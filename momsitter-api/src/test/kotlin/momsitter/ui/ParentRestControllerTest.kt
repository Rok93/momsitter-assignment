package momsitter.ui

import com.momsitter.momsitterassignment.application.ParentService
import com.momsitter.momsitterassignment.application.dto.RegisterParentRequest
import com.momsitter.momsitterassignment.application.dto.RegisterParentResponse
import com.momsitter.momsitterassignment.application.dto.UpdateParentRequest
import com.momsitter.momsitterassignment.exception.UnRegisteredParentException
import com.momsitter.momsitterassignment.fixture.createChildData
import com.momsitter.momsitterassignment.fixture.createMember
import com.momsitter.momsitterassignment.fixture.createParent
import com.ninjasquad.springmockk.MockkBean
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [ParentRestController::class])
internal class ParentRestControllerTest : RestControllerTest() {
    @MockkBean
    private lateinit var parentService: ParentService

    @DisplayName("회원이 부모 회원으로 등록한다")
    @Test
    fun testRegisterParentMember() {
        //given
        val member = createMember(id = 1L)
        val token = "loginToken"
        val request = RegisterParentRequest("요청 정보", listOf(createChildData()))
        val response = RegisterParentResponse(1L)

        validInterceptorAndArgumentResolverMocking(member)
        every { authenticationService.validateRoleByToken(token, any()) } just Runs
        every { parentService.register(member.id, request) } returns response

        //when //then
        mockMvc.perform(
            post("/api/parents")
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isCreated)
            .andExpect(header().exists("location"))
            .andExpect(header().stringValues("Location", "/api/parents/${response.id}"))
    }

    @DisplayName("회원이 자신의 부모정보를 수정한다")
    @Test
    fun testUpdateParentInfo() {
        //given
        val member = createMember(id = 1L).apply {
            createParent(id = 1L)
        }
        val token = "loginToken"
        val request = UpdateParentRequest("잘 부탁드립니다", listOf(createChildData()))

        every { authenticationService.validateRoleByToken(token, any()) } just Runs
        every { parentService.update(member.id, request) } just Runs

        validInterceptorAndArgumentResolverMocking(member)

        //when //then
        mockMvc.perform(
            put("/api/parents")
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isNoContent)
    }

    @DisplayName("부모회원이 아닌 회원이 자신의 부모정보를 수정하려고하면 예외가 발생한다")
    @Test
    fun testUpdateParentInfoIfLoginUserNotRegisteredParentMember() {
        //given
        val notParentMember = createMember(id = 1L)
        val token = "loginToken"
        val request = UpdateParentRequest("잘 부탁드립니다", listOf(createChildData()))

        every { authenticationService.validateRoleByToken(token, any()) } just Runs
        every { parentService.update(notParentMember.id, request) } throws UnRegisteredParentException()

        validInterceptorAndArgumentResolverMocking(notParentMember)

        //when //then
        mockMvc.perform(
            put("/api/parents")
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isUnauthorized)
    }
}
