//val jar: Jar by tasks
//val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks
//
//bootJar.enabled = false
//jar.enabled = true

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
//    kapt("org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.2.Final")

//    testImplementation("org.springframework.boot:spring-boot-starter-test")


}