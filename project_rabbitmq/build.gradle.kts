plugins {
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.serialization") version "2.0.0"
}

version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.rabbitmq:amqp-client:5.17.1")
    implementation("org.slf4j:slf4j-log4j12:1.7.29")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
}


kotlin {
    jvmToolchain(21)
}

tasks.register<JavaExec>("technic") {
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("doctor.DoctorKt")

    val argsProperty = project.findProperty("args") as? String
    if (argsProperty != null) {
        args = argsProperty.split(",").map { it.trim() }
    }
}
