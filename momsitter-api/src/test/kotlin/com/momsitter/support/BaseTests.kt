package com.momsitter.support

import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@AutoConfigureRestDocs
annotation class SliceTest