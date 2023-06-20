plugins {
    id("com.google.cloud.tools.jib") version "3.3.2"
}

dependencies {
    implementation(project(":core"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.0.3")
}
