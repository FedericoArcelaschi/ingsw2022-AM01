plugins {
    id("org.sonarqube") version "7.3.1.8318"
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

allprojects {
    group = "it.polimi.ingsw"
    version = "3.0"
}
