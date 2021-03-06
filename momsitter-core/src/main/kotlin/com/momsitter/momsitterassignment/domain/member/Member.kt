package com.momsitter.momsitterassignment.domain.member

import com.fasterxml.jackson.annotation.JsonFormat
import com.momsitter.momsitterassignment.domain.*
import com.momsitter.momsitterassignment.domain.parent.Parent
import com.momsitter.momsitterassignment.domain.sitter.Sitter
import com.momsitter.momsitterassignment.exceptions.AlreadyRegisteredException
import com.momsitter.momsitterassignment.exceptions.NotValidPasswordException
import support.BaseEntity
import java.time.LocalDate
import javax.persistence.*

@Entity
class Member(
    @Column(nullable = false)
    val name: String,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    @Column(nullable = false)
    var birth: LocalDate,

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    var gender: Gender,

    @Column(unique = true, nullable = false)
    val accountId: String,

    @AttributeOverride(name = "value", column = Column(name = "password", nullable = false))
    @Embedded
    var password: Password,

    @Column(nullable = false)
    var email: String,

    id: Long = 0L
) : BaseEntity(id) {
    constructor(
        name: String,
        birth: LocalDate,
        gender: Gender,
        accountId: String,
        password: String,
        email: String
    ) : this(name, birth, gender, accountId, Password(password), email)

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(value = EnumType.STRING)
    val roles: MutableSet<Role> = mutableSetOf()

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    var parent: Parent? = null

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sitter_id")
    var sitter: Sitter? = null

    fun registerParent(parent: Parent) {
        addRole(Role.PARENT)
        this.parent = parent
    }

    fun registerSitter(sitter: Sitter) {
        addRole(Role.SITTER)
        this.sitter = sitter
    }

    private fun addRole(role: Role) {
        if (roles.contains(role)) {
            throw AlreadyRegisteredException(role)
        }
        roles.add(role)
    }

    fun isParent(): Boolean = roles.contains(Role.PARENT)

    fun isSitter(): Boolean = roles.contains(Role.SITTER)

    fun validatePassword(password: String) {
        if (this.password != Password(password)) {
            throw NotValidPasswordException()
        }
    }

    fun update(birth: LocalDate, password: String, email: String, gender: Gender) {
        this.birth = birth
        this.password = Password(password)
        this.email = email
        this.gender = gender
    }
}
