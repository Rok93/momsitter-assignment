//val jar: Jar by tasks
//val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks
//
//bootJar.enabled = false
//jar.enabled = true

plugins {
    id("org.asciidoctor.convert") version "2.4.0"
//    id("java-library")
//    id("java-test-fixtures")
}

dependencies {
    implementation(project(":momsitter-core"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.springfox:springfox-boot-starter:3.0.0")
//    testImplementation(testFixtures(project(":momsitter-core"))) todo: TextFixture 개선작업 필요!
}


tasks.jar {
    enabled = false
}

tasks.bootJar {
    enabled = true
    archiveFileName.set("momsitter-backend-api.jar")
    mainClass.set("momsitter.ApiApplicationKt")
}