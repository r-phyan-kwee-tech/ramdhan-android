// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
        maven("https://maven.fabric.io/public")

    }
    dependencies {
        classpath(Libs.gradleAndroid)
        classpath(Libs.kotlin)
        classpath("org.jetbrains.kotlin:kotlin-android-extensions:${Versions.kotlinVersion}")
        classpath(Libs.licenseTools)
        classpath(Libs.googleService)
        classpath(Libs.fabric)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven("https://maven.google.com/")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
