plugins {
    id("chatik.spring-boot-app")
    id("org.springframework.boot")
}

group = "com.droid"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(projects.user)
    implementation(projects.chat)
    implementation(projects.notification)
    implementation(projects.common)
}

tasks.withType<Test> {
    useJUnitPlatform()
}