package com.momsitter.momsitterassignment.domain

import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Lob

@Embeddable
data class ParentInformation(
    @Lob
    @Column(nullable = false)
    val childrenInfo: String, // [아이의 정보 = Children]를 포함해야할지도... 모른다... 👀

    @Lob
    @Column(nullable = false)
    val requestContent: String
)
