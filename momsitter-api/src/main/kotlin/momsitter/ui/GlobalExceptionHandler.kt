package momsitter.ui

import com.momsitter.momsitterassignment.exception.BaseException
import com.momsitter.momsitterassignment.exception.UnAuthorizationException
import com.momsitter.momsitterassignment.application.dto.CommonResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(BaseException::class)
    fun handleBadRequestException(exception: RuntimeException): ResponseEntity<CommonResponse<Unit>> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(CommonResponse.error(exception.message))
    }

    @ExceptionHandler(UnAuthorizationException::class)
    fun handleAuthorizationException(exception: RuntimeException): ResponseEntity<CommonResponse<Unit>> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(CommonResponse.error(exception.message))
    }
}
