package com.momsitter.momsitterassignment.domain

import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Lob

@Embeddable
data class SitterInformation(
    @Column(nullable = false)
    val carefulAgeTerm: String, // 케어 가능한 연령정보  Term? 객체 만들기

    @Lob
    @Column(nullable = false)
    val bio: String
)
