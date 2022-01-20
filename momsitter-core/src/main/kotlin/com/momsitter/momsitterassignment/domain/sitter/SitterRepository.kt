package com.momsitter.momsitterassignment.domain.sitter

import org.springframework.data.jpa.repository.JpaRepository

interface SitterRepository : JpaRepository<Sitter, Long> {
}
