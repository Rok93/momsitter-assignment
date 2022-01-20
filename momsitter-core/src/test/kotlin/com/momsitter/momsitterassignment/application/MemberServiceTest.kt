package com.momsitter.momsitterassignment.application

import com.support.IntegrationTest
import org.springframework.beans.factory.annotation.Autowired
import javax.persistence.EntityManager

@IntegrationTest
class MemberServiceTest {
    @Autowired
    private lateinit var entityManager: EntityManager

    @Autowired
    private lateinit var memberService: MemberService
}
