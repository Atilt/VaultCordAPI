plugins {
    `java-library`
    `maven-publish`
}

group = "me.atilt.vaultcord"
version = "1.0.0"

java {
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    /*
     As Spigot-API depends on the BungeeCord ChatComponent-API,
    we need to add the Sonatype OSS repository, as Gradle,
    in comparison to maven, doesn't want to understand the ~/.m2
    directory unless added using mavenLocal(). Maven usually just gets
    it from there, as most people have run the BuildTools at least once.
    This is therefore not needed if you're using the full Spigot/CraftBukkit,
    or if you're using the Bukkit API.
    */
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.21.8-R0.1-SNAPSHOT")
    testImplementation("junit:junit:4.13.1")
}

publishing {
    println(findProperty("vaultcord_user") as String)
    println(findProperty("vaultcord_pass") as String)
    publications {
        create<MavenPublication>("gpr") {
            from(components["java"])
            artifactId = "vaultcord-api"
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Atilt/VaultCordAPI")
            credentials {
                username = findProperty("vaultcord_user") as String? ?: ""
                password = findProperty("vaultcord_pass") as String? ?: ""
            }
        }
    }
}