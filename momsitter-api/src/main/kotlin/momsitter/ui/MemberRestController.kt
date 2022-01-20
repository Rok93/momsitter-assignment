package momsitter.ui

import com.momsitter.momsitterassignment.application.AuthenticationService
import com.momsitter.momsitterassignment.application.MemberService
import com.momsitter.momsitterassignment.application.dto.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/members")
class MemberRestController(
    private val authenticationService: AuthenticationService,
    private val memberService: MemberService
) {
    @PostMapping("/signup")
    @Operation(summary = "회원 가입", description = "새로운 Member를 등록하는 기능")
    @ApiResponses(ApiResponse(responseCode = "200", description = "OK"))
    fun signup(@RequestBody @Valid request: SignupMemberRequest): ResponseEntity<CommonResponse<TokenResponse>> {
        val token = authenticationService.generateTokenBySignup(request)
        return ResponseEntity.ok().body(CommonResponse.success(token))
    }

    @PostMapping("/login")
    @Operation(summary = "로그인 기능", description = "로그인하여 토큰을 발급하는 기능")
    @ApiResponses(ApiResponse(responseCode = "200", description = "OK"))
    fun generateToken(@RequestBody @Valid request: LoginRequest): ResponseEntity<CommonResponse<TokenResponse>> {
        val tokenResponse = authenticationService.login(request.accountId, request.password);
        return ResponseEntity.ok(CommonResponse.success(tokenResponse))
    }

    @GetMapping("/me")
    @Operation(summary = "로그인 회원 상세정보 조회", description = "로그인한 회원의 상세정보 조회")
    @ApiResponses(ApiResponse(responseCode = "200", description = "OK"))
    fun memberDetail(loginMember: LoginMember): ResponseEntity<CommonResponse<MemberResponse>> {
        val memberResponse = memberService.findMemberById(loginMember.id)
        return ResponseEntity.ok(CommonResponse.success(memberResponse))
    }

    @PutMapping("/me")
    @Operation(summary = "로그인 회원 정보 수정", description = "로그인한 회원의 정보 수정")
    @ApiResponses(ApiResponse(responseCode = "204", description = "NO_CONTENT"))
    fun update(loginMember: LoginMember, @RequestBody request: UpdateMemberRequest): ResponseEntity<Unit> {
        memberService.update(loginMember.id, request)
        return ResponseEntity.noContent().build()
    }
}
