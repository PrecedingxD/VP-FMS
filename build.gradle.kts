import com.github.jengelman.gradle.plugins.shadow.ShadowPlugin
import net.kyori.indra.IndraPlugin

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.8.20"
    id("net.kyori.indra") version "3.0.1"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "me.glaremasters"
version = "1.0-SNAPSHOT"

apply {
    plugin<ShadowPlugin>()
    plugin<IndraPlugin>()
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.aikar.co/content/groups/aikar/")
    maven("https://repo.glaremasters.me/repository/public/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.21.7-R0.1-SNAPSHOT")

    implementation("com.github.kittinunf.fuel:fuel:2.3.1")
    implementation("com.github.kittinunf.fuel:fuel-gson:2.3.1")
    implementation("co.aikar:acf-paper:0.5.1-SNAPSHOT")
    implementation("ch.jalu:configme:1.3.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
}

tasks {
    build {
        dependsOn("shadowJar")
    }

    indra {
        javaVersions {
            target(8)
        }

        github("darbyjack", "VP-FMS")
    }

    shadowJar {
        minimize()
        relocate("co.aikar.commands", "me.glaremasters.vpfms.libs.acf")
        relocate("co.aikar.locales", "me.glaremasters.vpfms.libs.locales")
        relocate("ch.jalu.configme", "me.glaremasters.vpfms.libs.configme")
        relocate("kotlin", "me.glaremasters.vpfms.libs.kotlin")
    }

    compileKotlin {
        kotlinOptions.javaParameters = true
        kotlinOptions.jvmTarget = "1.8"
    }

    compileJava {
        options.compilerArgs = listOf("-parameters")
    }

    processResources {
        expand("version" to rootProject.version)
    }
}
