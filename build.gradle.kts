// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.fabric.io/public")

    }
    dependencies {
        classpath(Libs.gradleAndroid)
        classpath(Libs.kotlinGradle)
        classpath(Libs.googleService)
        classpath(Libs.crashlyticGradle)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.google.com/")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
