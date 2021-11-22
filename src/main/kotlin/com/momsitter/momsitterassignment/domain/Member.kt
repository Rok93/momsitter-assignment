package com.momsitter.momsitterassignment.domain

import com.fasterxml.jackson.annotation.JsonFormat
import com.momsitter.momsitterassignment.exception.NotValidPasswordException
import support.BaseEntity
import java.time.LocalDate
import javax.persistence.*

@Entity
class Member(
    @Column(nullable = false)
    val name: String,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    @Column(nullable = false)
    val birth: LocalDate,

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    val gender: Gender,   // Member에게 권한을 주면 더 쉽지 않을까...?

    @Column(unique = true, nullable = false)
    val accountId: String,

    @Column(nullable = false, length = 20)
    val password: String,

    @Column(nullable = false)
    val email: String,

    id: Long = 0L
) : BaseEntity(id) {
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(value = EnumType.STRING)
    val roles: MutableSet<Role> = mutableSetOf()

    fun addRole(role: Role) {
        check(!roles.contains(role)) { "이미 ${role}로 등록되었습니다." }
        roles.add(role)
    }

    fun isParent(): Boolean = roles.contains(Role.PARENT)

    fun isSitter(): Boolean = roles.contains(Role.SITTER)

    fun validatePassword(password: String) {
        if (this.password == password) {
            throw NotValidPasswordException()
        }
    }
}
