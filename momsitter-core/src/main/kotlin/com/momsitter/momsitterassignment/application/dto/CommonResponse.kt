package com.momsitter.momsitterassignment.application.dto

class CommonResponse<T>(
    val message: String? = "",
    val body: T? = null
) {
    companion object {
        fun error(message: String?): CommonResponse<Unit> = CommonResponse(message = message)

        fun <T> success(body: T?): CommonResponse<T> = CommonResponse(body = body)
    }
}
