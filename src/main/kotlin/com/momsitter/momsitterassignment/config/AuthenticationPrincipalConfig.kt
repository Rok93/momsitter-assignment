package com.momsitter.momsitterassignment.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class AuthenticationPrincipalConfig(
    private val authenticationInterceptor: AuthenticationInterceptor,
    private val authenticationArgumentResolver: AuthenticationArgumentResolver,
    private val sitterAuthenticationInterceptor: SitterAuthenticationInterceptor,
    private val parentAuthenticationInterceptor: ParentAuthenticationInterceptor
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        //("로그인을 강제할 곳과 아닌 곳을 분리하기!")
        registry.addInterceptor(authenticationInterceptor)
            .excludePathPatterns("/**", "/api/login")
            .addPathPatterns("/api/**")

        registry.addInterceptor(parentAuthenticationInterceptor)
            .addPathPatterns("/api/parents/**")

        registry.addInterceptor(sitterAuthenticationInterceptor)
            .addPathPatterns("api/sitters/**")
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(authenticationArgumentResolver)
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedMethods("*")
            .allowedOriginPatterns("*")
    }
}
