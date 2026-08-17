plugins {
    java
    application
    jacoco
    alias(libs.plugins.javafx)
    alias(libs.plugins.shadow)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

application {
    mainClass.set("it.polimi.ingsw.startUp.Main")
}

javafx {
    version = "18.0.1"
    modules = listOf("javafx.controls", "javafx.fxml", "javafx.graphics")
}

dependencies {
    implementation(project(":shared"))

    implementation(libs.log4j.core)
    implementation(libs.log4j.api)
    implementation(libs.log4j.legacy)
    compileOnly(libs.jetbrains.annotations)
    testCompileOnly(libs.jetbrains.annotations)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.engine)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
}

tasks.shadowJar {
    archiveClassifier.set("all")
    manifest {
        attributes["Main-Class"] = "it.polimi.ingsw.startUp.Main"
        attributes["Created-By"] = "AM01 - Giovanni Arriciati, Federico Arcelaschi, Lorenzo Aicardi"
    }
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "it.polimi.ingsw.startUp.Main"
    }
}
