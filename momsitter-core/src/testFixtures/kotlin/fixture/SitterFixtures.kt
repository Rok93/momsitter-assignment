package fixture

import com.momsitter.momsitterassignment.domain.sitter.CareAgeGroup
import com.momsitter.momsitterassignment.domain.sitter.Sitter

fun createSitter(
    minAge: Int = 1,
    maxAge: Int = 8,
    bio: String = "제 아이처럼 돌보겠습니다.",
    id: Long = 0L
): Sitter = Sitter(CareAgeGroup(minAge, maxAge), bio, id)
