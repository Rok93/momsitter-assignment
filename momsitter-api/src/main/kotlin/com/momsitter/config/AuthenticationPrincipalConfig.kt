package com.momsitter.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class AuthenticationPrincipalConfig(
    private val authenticationInterceptor: AuthenticationInterceptor,
    private val authenticationArgumentResolver: AuthenticationArgumentResolver
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authenticationInterceptor)
            .excludePathPatterns("/api/members/login", "/api/members/signup")
            .excludePathPatterns("/health")
            .addPathPatterns("/api/members/**", "/api/parents", "/api/sitters")
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
