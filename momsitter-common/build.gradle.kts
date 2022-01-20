val jar: Jar by tasks
val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks

bootJar.enabled = false
jar.enabled = true

//dependencies {
//    api("org.jetbrains.kotlin:kotlin-stdlib")
//    api("org.springframework.boot:spring-boot-starter")
//    api("org.springframework.boot:spring-boot-starter-web")
//    api("com.fasterxml.jackson.module:jackson-module-kotlin")
//
//    testImplementation("org.springframework.boot:spring-boot-starter-test")
//}