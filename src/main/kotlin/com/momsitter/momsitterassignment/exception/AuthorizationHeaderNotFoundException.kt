package com.momsitter.momsitterassignment.exception

class AuthorizationHeaderNotFoundException(
    message: String = "Authorization 헤더를 찾을 수가 없습니다."
) : BaseException(message)
