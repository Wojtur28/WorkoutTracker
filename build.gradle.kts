 buildscript {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("gradle.plugin.org.flywaydb:gradle-plugin-publishing:10.0.1")
    }
}

plugins {
    java
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
}

apply(plugin = "org.flywaydb.flyway")

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_20
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-validation:3.1.5")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.flywaydb:flyway-core:9.16.3")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.2.0")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
    implementation("org.mapstruct:mapstruct:1.6.0.Beta1")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.0.Beta1")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.bootBuildImage {
    builder.set("paketobuildpacks/builder-jammy-base:latest")
}
