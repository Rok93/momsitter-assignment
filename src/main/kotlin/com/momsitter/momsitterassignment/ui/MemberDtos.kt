package com.momsitter.momsitterassignment.ui

import com.momsitter.momsitterassignment.domain.member.Member
import com.momsitter.momsitterassignment.domain.member.Role

data class LoginMember(
    val id: Long,
    val name: String,
    val roles: Set<Role>
) {
    constructor(member: Member) : this(
        member.id,
        member.name,
        member.roles.toSet()
    )
}
