plugins {
    id("org.asciidoctor.convert") version "2.4.0"
}

dependencies {
    implementation(project(":momsitter-core"))
    implementation(project(":momsitter-common"))
    testImplementation(testFixtures(project(":momsitter-core")))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    compileOnly("io.jsonwebtoken:jjwt-api:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")

    testImplementation("com.ninja-squad:springmockk:2.0.3")
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}

val jar: Jar by tasks
val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks

tasks.bootJar {
    enabled = true
    archiveFileName.set("momsitter-api.jar")
    mainClass.set("com.momsitter.ApiApplicationKt")
}

jar.enabled = false