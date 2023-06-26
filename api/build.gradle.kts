plugins {
    id("com.google.cloud.tools.jib") version "3.3.2"
}

dependencies {
    implementation(project(":core"))

    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.0.3")

    implementation("jakarta.servlet:jakarta.servlet-api:6.0.0")

    // Cucumber
    testImplementation("io.cucumber:cucumber-java:7.12.1")
    testImplementation("io.cucumber:cucumber-spring:7.12.1")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:7.12.1")
    testImplementation("org.junit.platform:junit-platform-suite-api")

    testImplementation("io.projectreactor:reactor-test")
}
