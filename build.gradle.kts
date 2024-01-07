plugins {
    id("java")
}

group = "dev.bodid"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "dev.bodid.Main"
    }
}

tasks.test {
    useJUnitPlatform()
}