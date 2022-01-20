package com.momsitter.momsitterassignment.domain.parent

import com.momsitter.momsitterassignment.exception.DuplicatedChildException
import com.momsitter.momsitterassignment.fixture.createChild
import com.momsitter.momsitterassignment.fixture.createParent
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class ParentTest {
    @DisplayName("부모회원이 아이를 등록하는 기능")
    @Test
    fun testAddChild() {
        //given
        val parent = createParent(children = mutableListOf(createChild(age = 3, name = "김일")))

        //when
        parent.addChild(createChild(age = 5, name = "김이"))

        //then
        assertThat(parent.children).hasSize(2)
    }

    @DisplayName("부모회원이 동일한 아이를 등록하면 예외가 발생한다")
    @Test
    fun testAddChildIfAddDuplicatedChild() {
        //given
        val parent = createParent(children = mutableListOf(createChild(age = 3, name = "김일")))

        //when //then
        assertThrows<DuplicatedChildException> { parent.addChild(createChild(age = 3, name = "김일")) }
    }
}
