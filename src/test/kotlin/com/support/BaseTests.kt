package com.support

import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.test.context.TestConstructor

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension::class)
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
annotation class SliceTest

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@ExtendWith(MockKExtension::class)
annotation class UnitTest

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@DataJpaTest
annotation class RepositoryTest

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest
annotation class IntegrationTest
