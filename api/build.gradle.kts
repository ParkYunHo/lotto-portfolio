import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    id("com.google.cloud.tools.jib") version "3.3.2"    // JIB 설정
    id("groovy")                                        // Spock Groovy문법 설정
    id("org.asciidoctor.jvm.convert") version "3.3.2"   // Spring Rest Docs 설정
}

val asciidoctorExt: Configuration by configurations.creating

dependencies {
    implementation(project(":core"))

    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("jakarta.servlet:jakarta.servlet-api:6.0.0")

    // Coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")

    // Caffeine Cache
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("com.github.ben-manes.caffeine:caffeine")

    // OpenAPI (Swagger)
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.0.2")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    testImplementation("org.spockframework:spock-core")
    testImplementation("org.spockframework:spock-core")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.spockframework:spock-core")
    testImplementation("org.spockframework:spock-core")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // Spock
    testImplementation("org.spockframework:spock-core:2.3-groovy-4.0")
    testImplementation("org.spockframework:spock-spring:2.3-groovy-4.0")
    testImplementation("org.apache.groovy:groovy:4.0.12")

    // spring restdocs
    testImplementation("org.springframework.restdocs:spring-restdocs-webtestclient")
    asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")

    // Cucumber
    testImplementation("io.cucumber:cucumber-java:7.12.1")
    testImplementation("io.cucumber:cucumber-spring:7.12.1")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:7.12.1")
    testImplementation("org.junit.platform:junit-platform-suite-api")

    testImplementation("io.projectreactor:reactor-test")
}

// Spring Rest Docs 설정
tasks {
    val snippetsDir = file("build/generated-snippets")

    test {
        outputs.dir(snippetsDir)
        useJUnitPlatform()

        testLogging {
            showStandardStreams = true
            showCauses = true
            showExceptions = true
            showStackTraces = true
            exceptionFormat = TestExceptionFormat.FULL
        }

        options {
            systemProperty("cucumber.features", "src/test/resources")
            systemProperty("cucumber.glue", "cucumber.feature")
        }
    }

    asciidoctor {
        dependsOn(test)

        inputs.dir(snippetsDir)
        configurations(asciidoctorExt.name)

        baseDirFollowsSourceFile()

        doFirst {
            delete("src/main/resources/static/docs")
        }

        doLast {
            copy {
                from(file("build/docs/asciidoc"))
                into(file("src/main/resources/static/docs"))
            }
        }
    }

    build {
        dependsOn(asciidoctor)
    }

    jar {
        enabled = false
        dependsOn(asciidoctor)
    }

    bootJar {
        enabled = true
        dependsOn(asciidoctor)

        copy {
            from(file("${asciidoctor.get().outputDir}/html5"))
            into(file("static/docs"))
        }
    }
}


// JIB 설정
val tagName = project.properties["tagName"] ?: ""
val regex = Regex("^v")

jib {
    from {
        image = "eclipse-temurin:17"
    }
    to {
        image = "johnpark0921/lotto-portfolio:$tagName"
        if(regex.containsMatchIn(tagName as String)) {
            tags = setOf("latest")
        }
    }
    container {
        labels.set(
            mapOf(
                "maintainer" to "yoonho <qkrdbsgh0921@gmail.com>"
            )
        )
        creationTime.set("USE_CURRENT_TIMESTAMP")
        setFormat("OCI")
        environment = mapOf(
            "TZ" to "Asia/Seoul"
        )
        jvmFlags = listOf(
            "-Dsun.net.inetaddr.ttl=0",     // DNS cache TTL
            "-XX:+PrintCommandLineFlags",   // Print JVM Flags
            "-Dspring.profiles.active=prod"     // profile 설정
        )
    }
}