plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("com.cookpad.android.licensetools")
    id("io.fabric")
    id("com.google.gms.google-services")
}

android {
    compileSdkVersion(Versions.targetSdkVersion)
    defaultConfig {
        applicationId = Versions.appId
        minSdkVersion(Versions.minSdkVersion)
        versionCode = Versions.versionCode
        versionName = Versions.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
        vectorDrawables.useSupportLibrary = true
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFile(getDefaultProguardFile("proguard-android.txt"))
        }
    }
    buildFeatures {
        dataBinding = true
    }

    dependencies {
        implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
        implementation(Libs.kotlinLib)
        implementation(Libs.appCompat)
        implementation(Libs.material)
        implementation(Libs.viewPagerIndicator)

        implementation(Libs.lifeCycle)
        implementation(Libs.liveDataRxSupport)

        implementation(Libs.room)
        kapt(Libs.roomProcessor)
        implementation(Libs.roomRxSupport)
        testImplementation(Libs.roomTesting)

        implementation(Libs.rxjava)
        implementation(Libs.rxAndroid)

        implementation(Libs.retrofit)
        implementation(Libs.retrofitConverterGson)
        implementation(Libs.retrofitRxAdapter)
        implementation(Libs.okhttpLogging)

        implementation(Libs.daggerAndroid)
        implementation(Libs.daggerAndroidSupport)
        kapt(Libs.daggerProcessor)
        kapt(Libs.daggerAndroidProcessor)

        testImplementation(Libs.junit)
        testImplementation(Libs.mockWebServer)
        testImplementation(Libs.mockitoKotlin1)
        testImplementation(Libs.archCoreTesting)
        testImplementation(Libs.testRunner)
        testImplementation(Libs.espresso)

        implementation(Libs.constraintLayout)
        implementation(Libs.timber)
        implementation(Libs.firebaseCore)
        implementation(Libs.crashlytic)

        implementation(Libs.rabbit) {
            exclude(group = "org.json", module = "json")
        }
    }
}