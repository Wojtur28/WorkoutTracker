import java.text.SimpleDateFormat
import java.util.*

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("io.swagger.codegen.v3:swagger-codegen-maven-plugin:3.0.16")
    }
}

plugins {
    java
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
    id("org.flywaydb.flyway") version "10.10.0"
    id("org.hidetake.swagger.generator") version "2.19.2"
}

group = "com.example"
val dateFormat = SimpleDateFormat("yyyyMMdd-HHmmss")
val formattedDate = dateFormat.format(Date())
version = "0.0.1-$formattedDate"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
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
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.apache.logging.log4j:log4j-to-slf4j:2.23.1")
    implementation("org.apache.logging.log4j:log4j-api:2.23.1")
    testImplementation("org.springframework.security:spring-security-test:6.3.0")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.1.5")
    implementation("org.slf4j:slf4j-api:2.0.13")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.flywaydb:flyway-core:9.16.3")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
    implementation("org.springframework.boot:spring-boot-starter-mail:3.2.5")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
    implementation("org.springframework.boot:spring-boot-starter-logging")
    swaggerCodegen("io.swagger.codegen.v3:swagger-codegen-cli:3.0.47")
    implementation("org.springframework.boot:spring-boot-starter-actuator:3.3.0")
    // dependencies for swagger-codegen
    implementation("io.gsonfire:gson-fire:1.9.0")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("org.threeten:threetenbp:1.6.8")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.12")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.12")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    implementation("org.mapstruct:mapstruct:1.6.0.Beta1")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.0.Beta1")
    compileOnly("javax.servlet:servlet-api:3.0-alpha-1")
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("net.logstash.logback:logstash-logback-encoder:7.2")
    implementation("javax.servlet:javax.servlet-api:4.0.1")
}

tasks.withType<JavaCompile> {
    options.release.set(21)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.bootBuildImage {
    builder.set("paketobuildpacks/builder-jammy-base:latest")
}

val apiPackage = "com.example.api"
val modelPackage = "com.example.model"
val swaggerFile = file("src/main/resources/contract/contract.yml")

swaggerSources {
    create("workoutTrackerApi") {
        setInputFile(swaggerFile)
        code.language = "spring"
        code.outputDir = file("$buildDir/generated-sources")
        code.components = listOf("apis", "models")
        code.additionalProperties = mapOf(
            "apiPackage" to apiPackage,
            "modelPackage" to modelPackage,
            "java8" to "true",
            "interfaceOnly" to "true",
            "useTags" to "true"
        )
    }
}

sourceSets {
    main {
        java {
            srcDir("$buildDir/generated-sources/src/main/java")
        }
    }
}

tasks.named("compileJava") {
    dependsOn(tasks.named("generateSwaggerCodeWorkoutTrackerApi"))
}
