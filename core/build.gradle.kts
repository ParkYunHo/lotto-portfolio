plugins {
    id("com.google.cloud.tools.jib") version "3.3.2"
}

dependencies {
    runtimeOnly("com.mysql:mysql-connector-j")

    // QueryDSL
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
}

kotlin.sourceSets.main {
    kotlin.srcDir("$buildDir/generated/source/kapt/main")
}