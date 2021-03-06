package com.momsitter.momsitterassignment

import com.momsitter.momsitterassignment.domain.Age
import com.momsitter.momsitterassignment.domain.Gender
import com.momsitter.momsitterassignment.domain.member.Member
import com.momsitter.momsitterassignment.domain.member.MemberRepository
import com.momsitter.momsitterassignment.domain.parent.Child
import com.momsitter.momsitterassignment.domain.parent.Parent
import com.momsitter.momsitterassignment.domain.parent.ParentRepository
import com.momsitter.momsitterassignment.domain.sitter.CareAgeGroup
import com.momsitter.momsitterassignment.domain.sitter.Sitter
import com.momsitter.momsitterassignment.domain.sitter.SitterRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.time.LocalDate

@Profile("!test")
@Component
class DataLoader(
    private val memberRepository: MemberRepository,
    private val parentRepository: ParentRepository,
    private val sitterRepository: SitterRepository
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val member1 = createMember("샘해밍턴", 1977, 7, 31, Gender.MALE, "test1", "Password123$", "test1@naver.com")
        val parent1 = createSavedParent(
            "맞벌이 부부입니다. 24시간 상시 거주하면서 애기들 돌봐줄 수 있는 맘시터를 원합니다.",
            listOf(
                createChild(6, 2016, 7, 12, Gender.MALE, "윌리엄 해밍턴"),
                createChild(5, 2017, 11, 8, Gender.MALE, "벤틀리 해밍턴")
            )
        ).also {
            member1.registerParent(it)
        }
        memberRepository.save(member1)

        val member2 = createMember("추성훈", 1975, 7, 29, Gender.MALE, "test2", "Password123$", "test2@naver.com")
        val parent2 = createSavedParent(
            requestInformation = "아내가 모델이라 다이어트식 밖에 못합니다... 우리 사랑이한테 맛잇는 요리 해주실 수 있는 맘시터 구합니다.",
            listOf(
                createChild(11, 2011, 10, 24, Gender.FEMALE, "추사랑")
            )
        ).also { member2.registerParent(it) }
        memberRepository.save(member2)

        val member3 = createMember("강형욱", 1985, 5, 27, Gender.MALE, "test3", "Password123$", "test3@naver.com")
        val sitter1 = createSavedSitter(1, 10, "세상에 나쁜 개가 없듯이 세상에 나쁜 애기는 없다!").also {
            member3.registerSitter(it)
        }
        memberRepository.save(member3)

        val member4 = createMember("오은영", 1965, 9, 9, Gender.FEMALE, "test4", "Password123$", "test4@naver.com")
        val sitter2 = createSavedSitter(1, 10, "문제 아이는 없다, 양육에 문제가 있을 뿐").also {
            member4.registerSitter(it)
        }
        val parent3 = createSavedParent(
            requestInformation = "나름 전문가지만... 제 아이 육아는 역시나 어렵네요. 도움주실분 구합니다.",
            listOf(
                createChild(7, 2015, 10, 2, Gender.FEMALE, "이선빈")
            )
        ).also {
            member4.registerParent(it)
        }
        memberRepository.save(member4)
    }

    private fun createSavedSitter(minAge: Int, maxAge: Int, bio: String): Sitter =
        sitterRepository.save(Sitter(CareAgeGroup(minAge, maxAge), bio))

    private fun createSavedParent(requestInformation: String, children: List<Child>): Parent {
        return parentRepository.save(
            Parent(
                requestInformation = requestInformation,
            ).apply {
                children.forEach { addChild(it) }
            })
    }

    fun createMember(
        name: String,
        year: Int,
        month: Int,
        dayOfMonth: Int,
        gender: Gender,
        accountId: String,
        password: String,
        email: String
    ) = Member(name, LocalDate.of(year, month, dayOfMonth), gender, accountId, password, email)

    private fun createChild(
        age: Int,
        year: Int,
        month: Int,
        dayOfMonth: Int,
        gender: Gender,
        name: String,
    ) = Child(Age(age), LocalDate.of(year, month, dayOfMonth), gender, name)
}
