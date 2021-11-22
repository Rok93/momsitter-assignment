package com.momsitter.momsitterassignment.application

import com.momsitter.momsitterassignment.domain.MemberRepository
import com.momsitter.momsitterassignment.domain.Role
import com.momsitter.momsitterassignment.exception.NotRoleAuthorizationException
import com.momsitter.momsitterassignment.ui.LoginMember
import com.momsitter.momsitterassignment.ui.dto.TokenResponse
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
            ?: throw NoSuchElementException("존재하지 않는 회원입니다. accountId = $accountId")

        findMember.validatePassword(password)

        return TokenResponse(jwtTokenProvider.createToken(findMember))
    }

    fun createLoginMemberByToken(token: String): LoginMember {
        return jwtTokenProvider.findMemberFromToken(token);
    }

    fun validateRoleByToken(token: String, role: Role) {
        val loginMember = jwtTokenProvider.findMemberFromToken(token)
        check(loginMember.roles.contains(role)) { NotRoleAuthorizationException() }
    }
}
