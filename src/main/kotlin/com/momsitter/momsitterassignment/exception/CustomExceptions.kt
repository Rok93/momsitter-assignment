package com.momsitter.momsitterassignment.exception

import com.momsitter.momsitterassignment.domain.member.Role

open class BaseException(message: String) : RuntimeException(message)

open class UnAuthorizationException(message: String) : BaseException(message)

class NotRoleAuthorizationException(role: Role, message: String = "${role.title}회원이 아닙니다.") : BaseException(message)

class NotValidPasswordException(message: String = "비밀번호가 일치하지 않습니다.") : BaseException(message)

class AlreadyRegisteredException(role: Role) : BaseException("이미 ${role}로 등록되었습니다.")

class AlreadySignupMemberException(
    accountId: String,
    message: String = "$accountId 이미 등록된 회원입니다."
) : BaseException(message)

class NotFoundMemberException(message: String = "존재하지 않는 회원입니다.") : BaseException(message)

class UnRegisteredParentException(message: String = "부모회원으로 등록되지 않은 회원입니다.") : UnAuthorizationException(message)

class UnRegisteredSitterException(message: String = "시터회원으로 등록되지 않은 회원입니다.") : UnAuthorizationException(message)

class DuplicatedChildException(message: String = "이미 등록된 아기입니다.") : BaseException(message)

class AuthorizationHeaderNotFoundException(
    message: String = "Authorization 헤더를 찾을 수가 없습니다."
) : UnAuthorizationException(message)
