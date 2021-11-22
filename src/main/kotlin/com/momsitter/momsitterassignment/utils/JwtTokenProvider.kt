package com.momsitter.momsitterassignment.utils

import com.momsitter.momsitterassignment.domain.Member
import com.momsitter.momsitterassignment.domain.Role
import com.momsitter.momsitterassignment.ui.LoginMember
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

const val ONE_HOUR: Long = 1_000L * 60 * 60

@Component
class JwtTokenProvider(
    private val singleKey: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256),
    private val expirationInMilliseconds: Long = ONE_HOUR,
) {
    fun createToken(member: Member): String {
        val now = Date()
        val expiration = Date(now.time + expirationInMilliseconds)
        return Jwts.builder()
            .claim("id", member.id)
            .claim("name", member.name)
            .claim("roles", member.roles)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(singleKey)
            .compact()
    }

    fun isValidToken(token: String): Boolean {
        return try {
            getClaimsJws(token)
            true
        } catch (e: JwtException) {
            false
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    fun findMemberFromToken(token: String): LoginMember {
        val body = getClaimsJws(token).body
        val id = body["id"] as Number
        val name = body["name"] as String
        val roles = parsingRoles(body)
        return LoginMember(id.toLong(), name, roles.toSet())
    }

    private fun parsingRoles(body: Claims): List<Role> { // 별도로 roles를 파싱하는 로직을 없애고 싶음
        val roles = body["roles"] as List<String>
        return roles.map { Role.valueOfKey(it) }
    }

    private fun getClaimsJws(token: String) = Jwts.parserBuilder()
        .setSigningKey(singleKey.encoded)
        .build()
        .parseClaimsJws(token)
}
