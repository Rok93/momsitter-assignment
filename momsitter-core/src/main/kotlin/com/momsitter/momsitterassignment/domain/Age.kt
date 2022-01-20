package com.momsitter.momsitterassignment.domain


import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import javax.persistence.Access
import javax.persistence.AccessType
import javax.persistence.Column
import javax.persistence.Embeddable

private const val MIN_AGE = 1

private class AgeDeserializer : JsonDeserializer<Age>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Age = Age(p.intValue)
}

private class AgeSerializer : JsonSerializer<Age>() {
    override fun serialize(age: Age, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeString(age.value.toString())
    }
}

@JsonSerialize(using = AgeSerializer::class)
@JsonDeserialize(using = AgeDeserializer::class)
@Embeddable
@Access(AccessType.FIELD)
data class Age(
    @Column(nullable = false)
    val value: Int
) {
    init {
        check(value >= MIN_AGE) { "나이는 1이상의 숫자만 가능합니다." }
    }

    operator fun Age.inc(): Age = Age(this.value + 1) // 1년마다 스케줄링으로 아이 나이 한살씩 올려주기!

    operator fun compareTo(other: Age): Int = value.compareTo(other.value)

    operator fun rangeTo(other: Age): IntRange = value..other.value
}
