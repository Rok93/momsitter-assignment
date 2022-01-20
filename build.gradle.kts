import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"

    val kotlinVersion = "1.5.31"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("kapt") version kotlinVersion
}

buildscript {
    repositories {
        mavenCentral()
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.jar {
    enabled = true
}

tasks.bootJar {
    enabled = false
}

allprojects {
    group = "com.momsitter"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin("kotlin")
        plugin("kotlin-jpa")
        plugin("kotlin-kapt")
        plugin("kotlin-spring")
        plugin("io.spring.dependency-management")
        plugin("org.springframework.boot")
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//        implementation("org.springframework.boot:spring-boot-starter-web")
//        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("mysql:mysql-connector-java:8.0.22")
        runtimeOnly("com.h2database:h2")
        compileOnly("io.jsonwebtoken:jjwt-api:0.11.2")
        runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
        runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")
        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
            exclude(group = "org.mockito")
        }
        testImplementation("com.ninja-squad:springmockk:2.0.3")
        implementation("io.springfox:springfox-boot-starter:3.0.0")
        testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.test {
        useJUnitPlatform()
    }

    //todo: 아래의 로직을 이 하나의 코드로 변경할 수 있는지 테스트해보기!! 각 모듈들이 jar 파일로 빌드파일을 만드는지 확인하면 됌!!
    val jar: Jar by tasks
    val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks

    bootJar.enabled = false
    jar.enabled = true
}
