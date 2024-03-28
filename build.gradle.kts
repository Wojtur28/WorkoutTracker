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
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.flywaydb:flyway-core:9.16.3")
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.2.0")
    implementation ("org.springframework.boot:spring-boot-starter-security")
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
    testImplementation("junit:junit:4.12")
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
     inputSpec.set("A:\\Projekty\\WorkoutTracker\\src\\main\\resources\\contract\\contract.yml")
     outputDir.set("A:\\Projekty\\WorkoutTracker\\build\\generated-sources\\swagger")
     configOptions.set(mapOf(
             "generateModelTests" to "false",
             "generateApiTests" to "false",
             "interfaceOnly" to "true",
             "useTags" to "true",
     ))
 }

 sourceSets {
     main {
         java {
             srcDir("A:\\Projekty\\WorkoutTracker\\build\\generated-sources\\swagger\\src\\main")
         }
     }
     test {
         java {
             srcDir("A:\\Projekty\\WorkoutTracker\\build\\generated-sources\\swagger\\src\\test")
         }
     }
 }
