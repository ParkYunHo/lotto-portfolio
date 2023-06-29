plugins {
    id("com.google.cloud.tools.jib") version "3.3.2"
}

dependencies {
    implementation(project(":core"))

    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.0.3")
    implementation("com.google.code.gson:gson:2.10.1")

    testImplementation("org.springframework.batch:spring-batch-test")

    runtimeOnly("com.h2database:h2")
}
