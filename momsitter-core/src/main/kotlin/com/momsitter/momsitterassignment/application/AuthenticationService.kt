package com.momsitter.momsitterassignment.application

import com.momsitter.momsitterassignment.application.dto.LoginMember
import com.momsitter.momsitterassignment.application.dto.SignupMemberRequest
import com.momsitter.momsitterassignment.application.dto.TokenResponse
import com.momsitter.momsitterassignment.domain.member.MemberRepository
import com.momsitter.momsitterassignment.domain.member.Role
import com.momsitter.momsitterassignment.exception.AlreadySignupMemberException
import com.momsitter.momsitterassignment.exception.NotFoundMemberException
import com.momsitter.momsitterassignment.exception.NotRoleAuthorizationException
import com.momsitter.momsitterassignment.utils.JwtTokenProvider
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val memberRepository: MemberRepository,
    private val jwtTokenProvider: JwtTokenProvider
) {
    fun validateToken(token: String) {
        jwtTokenProvider.isValidToken(token)
    }

    fun login(accountId: String, password: String): TokenResponse {
        val findMember = memberRepository.findByAccountId(accountId)
            ?: throw NotFoundMemberException("존재하지 않는 회원입니다. accountId = $accountId")

        findMember.validatePassword(password)

        return TokenResponse(jwtTokenProvider.createToken(findMember))
    }

    fun createLoginMemberByToken(token: String): LoginMember {
        return jwtTokenProvider.findMemberFromToken(token);
    }

    fun validateRoleByToken(token: String, role: Role) {
        val loginMember = jwtTokenProvider.findMemberFromToken(token)
        if (!loginMember.roles.contains(role)) {
            throw NotRoleAuthorizationException(role)
        }
    }

    fun generateTokenBySignup(request: SignupMemberRequest): TokenResponse {
        require(request.password == request.confirmPassword) { "입력한 비밀번호가 서로 일치하지 않습니다." }
        if (!memberRepository.existsByAccountId(request.accountId)) {
            throw AlreadySignupMemberException(request.accountId)
        }

        val member = memberRepository.save(request.toEntity())
        return TokenResponse(jwtTokenProvider.createToken(member))
    }
}
