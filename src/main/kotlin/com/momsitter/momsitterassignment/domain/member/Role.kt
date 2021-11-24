package com.momsitter.momsitterassignment.domain.member

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonValue

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class Role(@JsonValue val key: String, val title: String) {
    PARENT("ROLE_PARENT", "부모"),
    SITTER("ROLE_SITTER", "시터");

    companion object {
        fun valueOfKey(key: String): Role {
            return values().first { it.key == key }
        }
    }
}
