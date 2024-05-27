// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.3" apply false
}
buildscript {
    repositories {
        google()  // Certifique-se de ter o repositório do Google
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.0.2")  // Ou a versão mais recente do Gradle
        classpath("com.google.gms:google-services:4.3.15")  // Adicione o plugin do Google Services
    }
}

allprojects {
    repositories {
        google()  // Certifique-se de ter o repositório do Google
        mavenCentral()
    }
}


