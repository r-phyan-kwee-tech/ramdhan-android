// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
        maven("https://maven.fabric.io/public")

    }
    dependencies {
        classpath(Libs.gradleAndroid)
        classpath(Libs.kotlinGradle)
        classpath("org.jetbrains.kotlin:kotlin-android-extensions:${Versions.kotlinVersion}")
        classpath(Libs.licenseTools)
        classpath(Libs.googleService)
        classpath(Libs.fabric)
        //classpath("com.android.tools.build:gradle:4.2.1")

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
