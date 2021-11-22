package com.momsitter.momsitterassignment.domain

import org.springframework.data.jpa.repository.JpaRepository

interface SitterRepository : JpaRepository<Sitter, Long> {
}
