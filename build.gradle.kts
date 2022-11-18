import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.6.21"
    id("com.github.johnrengelman.shadow") version "7.0.0"

}

group = "cat.kiwi.minecraft"
version = "1.0-SNAPSHOT"


repositories {
    mavenCentral()
    maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
    maven(url = "https://repo.extendedclip.com/content/repositories/placeholderapi")
    maven(url = "https://repo.codemc.org/repository/maven-public/")
    maven(url = "https://jitpack.io")
}

dependencies {
    // kotlin libs
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // minecraft apis
    implementation("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT")
    implementation("com.github.Agenda-Minecraft:MEtcd:2.0.7")
    testImplementation(kotlin("test"))

    // uhc-reloaded
    implementation(files("libs/UHCReloaded-1.19.09.jar"))

    testImplementation(kotlin("test"))
}

tasks {
    named<ShadowJar>("shadowJar") {
        mergeServiceFiles()
        archiveBaseName.set("UHCPatch")
        dependencies {
            exclude(dependency("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT"))
            exclude(dependency("com.github.Agenda-Minecraft:MEtcd:2.0.7"))
            exclude(dependency("de.tr7zw:item-nbt-api-plugin:2.10.0"))
            exclude(dependency(files("libs/UHCReloaded-1.19.09.jar")))
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}