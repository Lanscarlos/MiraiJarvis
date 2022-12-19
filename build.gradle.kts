plugins {
    java
    val kotlinVersion = "1.5.31"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.12.3"
}

group = "top.lanscarlos.jarvis"
version = "1.0.0"

repositories {
    maven { url = uri("https://repo.tabooproject.org/repository/releases/") }
    maven("https://maven.aliyun.com/repository/public")
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("io.izzel.taboolib:common:6.0.10-31")
    implementation("io.izzel.taboolib:common-5:6.0.10-31")
    implementation("io.izzel.taboolib:module-configuration-shaded:6.0.10-31")

    implementation("net.mamoe:mirai-core:2.12.3")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.31")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}