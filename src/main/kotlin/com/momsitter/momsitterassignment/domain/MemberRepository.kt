package com.momsitter.momsitterassignment.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

fun MemberRepository.getById(id: Long): Member =
    findByIdOrNull(id) ?: throw NoSuchElementException("존재하지 않는 멤버입니다. id = $id")

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByAccountId(accountId: String): Member?
}
