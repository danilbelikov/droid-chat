plugins {
    id("chatik.spring-boot-app")
}

group = "com.droid"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(projects.user)
    implementation(projects.chat)
    implementation(projects.notification)
    implementation(projects.common)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.kotlin.reflect)

    implementation(libs.spring.boot.starter.data.jpa)
    runtimeOnly(libs.postgresql)
}

tasks.withType<Test> {
    useJUnitPlatform()
}