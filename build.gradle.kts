import java.text.SimpleDateFormat
import java.util.*

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
    id("org.flywaydb.flyway") version "10.10.0"
    id("org.openapi.generator") version "5.3.0"
}

group = "com.example"
 val dateFormat = SimpleDateFormat("yyyyMMdd-HHmmss")
 val formattedDate = dateFormat.format(Date())
 version = "0.0.1-$formattedDate"

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
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test:6.3.0")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.1.5")
    implementation("org.slf4j:slf4j-api:2.0.13")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.flywaydb:flyway-core:9.16.3")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
    implementation("org.springframework.boot:spring-boot-starter-mail:3.2.5")
    implementation ("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
    implementation("io.swagger.codegen.v3:swagger-codegen-generators:1.0.47") {
        exclude(group = "org.slf4j", module = "slf4j-simple")
    }
    // most of the dependencies above below are important for swagger codegen
    implementation("io.gsonfire:gson-fire:1.9.0")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("org.threeten:threetenbp:1.6.8")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.12")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.12")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    implementation("org.mapstruct:mapstruct:1.6.0.Beta1")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.0.Beta1")
    compileOnly("javax.servlet:servlet-api:3.0-alpha-1")
    implementation("org.openapitools:jackson-databind-nullable:0.2.6")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.bootBuildImage {
    builder.set("paketobuildpacks/builder-jammy-base:latest")
}

 openApiGenerate {
     generatorName.set("spring")
     inputSpec.set("/Users/wojtur/IdeaProjects/WorkoutTracker/src/main/resources/contract/contract.yml")
     outputDir.set("/Users/wojtur/IdeaProjects/WorkoutTracker/build/generated-sources/swagger")
     configOptions.set(mapOf(
             "generateModelTests" to "false",
             "generateApiTests" to "false",
             "interfaceOnly" to "true",
             "useTags" to "true",
     ))
     apiPackage.set("org.openapitools.api")
     modelPackage.set("org.openapitools.model")
 }

 sourceSets {
     main {
         java {
             srcDir("/Users/wojtur/IdeaProjects/WorkoutTracker/build/generated-sources/swagger/src/main")
         }
     }
     test {
         java {
             srcDir("/Users/wojtur/IdeaProjects/WorkoutTracker/build/generated-sources/swagger/src/test")
         }
     }
 }
