package com.momsitter.momsitterassignment.domain.member

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.findByIdOrNull

fun MemberRepository.getByMemberId(id: Long): Member =
    findByIdOrNull(id) ?: throw NoSuchElementException("존재하지 않는 멤버입니다. id = $id")

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByAccountId(accountId: String): Member?

    fun existsByAccountId(accountId: String): Boolean

    @Query("select m from Member m join fetch m.roles join fetch m.parent")
    fun findByIdWithParent(id: Long): Member?

    @Query("select m from Member m join fetch m.roles join fetch m.sitter")
    fun findByIdWithSitter(id: Long): Member?
}
