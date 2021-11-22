package com.momsitter.momsitterassignment.ui.dto

import com.momsitter.momsitterassignment.domain.Gender
import com.momsitter.momsitterassignment.domain.Member
import com.momsitter.momsitterassignment.domain.Role
import java.time.LocalDate

data class MemberResponse(
    val name: String,
    val birth: LocalDate,
    val gender: Gender,
    val accountId: String,
    val email: String,
    val role: Set<Role>,
    val id: Long
) {
    constructor(member: Member) : this(
        member.name,
        member.birth,
        member.gender,
        member.accountId,
        member.email,
        member.roles,
        member.id
    )
}
