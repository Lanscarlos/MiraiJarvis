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
    // taboolib
    implementation("io.izzel.taboolib:common:6.0.10-98")
    implementation("io.izzel.taboolib:common-5:6.0.10-110")
    implementation("io.izzel.taboolib:module-configuration-shaded:6.0.10-110")

    // batik
    implementation("org.apache.xmlgraphics:batik-codec:1.14")
    implementation("org.apache.xmlgraphics:batik-svg-dom:1.14")
    implementation("org.apache.xmlgraphics:batik-svggen:1.14")
    implementation("org.apache.xmlgraphics:batik-transcoder:1.14")

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // mirai core
    implementation("net.mamoe:mirai-core:2.12.3")

    // kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.31")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}