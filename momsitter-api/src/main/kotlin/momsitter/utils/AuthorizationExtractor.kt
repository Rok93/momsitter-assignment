package momsitter.utils

import com.momsitter.momsitterassignment.exception.AuthorizationHeaderNotFoundException
import org.springframework.http.HttpHeaders
import javax.servlet.http.HttpServletRequest

private const val BEARER = "Bearer"

class AuthorizationExtractor {
    companion object {
        fun extract(httpServletRequest: HttpServletRequest): String {
            val headers = httpServletRequest.getHeaders(HttpHeaders.AUTHORIZATION)
            while (headers.hasMoreElements()) {
                val value = headers.nextElement()
                if (value.startsWith(BEARER)) {
                    return value.substring(BEARER.length).trim()
                }
            }

            throw AuthorizationHeaderNotFoundException()
        }
    }
}
