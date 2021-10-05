plugins {
    id("com.android.application")
    kotlin("kapt")
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
    id(Libs.licenseTools) version Versions.licenseToolsVersion
    kotlin("android")
}

android {
    compileSdk = Versions.targetSdkVersion
    defaultConfig {
        applicationId = Versions.appId
        minSdk = Versions.minSdkVersion
        versionCode = Versions.versionCode
        versionName = Versions.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
        multiDexEnabled = true
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFile(getDefaultProguardFile("proguard-android.txt"))
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libs.kotlinLib)
    implementation(Libs.kotlinCoroutine)
    testImplementation(Libs.kotlinCoroutineTest)
    implementation(Libs.appCompat)
    implementation(Libs.fragmentKtx)
    implementation(Libs.material)
    implementation(Libs.viewPager2)

    implementation(Libs.viewModel)
    implementation(Libs.liveData)

    implementation(Libs.room)
    kapt(Libs.roomProcessor)
    testImplementation(Libs.roomTesting)

    implementation(Libs.retrofit)
    implementation(Libs.retrofitConverterGson)
    implementation(Libs.okhttpLogging)

    implementation(Libs.daggerAndroid)
    implementation(Libs.daggerAndroidSupport)
    kapt(Libs.daggerProcessor)
    kapt(Libs.daggerAndroidProcessor)

    testImplementation(Libs.junit)
    testImplementation(Libs.mockWebServer)
    testImplementation(Libs.mockitoKotlin1)
    testImplementation(Libs.mockitoKotlin)
    testImplementation(Libs.archCoreTesting)
    testImplementation(Libs.testRunner)
    testImplementation(Libs.espresso)

    implementation(Libs.constraintLayout)
    implementation(Libs.timber)
    implementation(Libs.firebaseCore)

    implementation(Libs.rabbit) {
        exclude(group = "org.json", module = "json")
    }

    implementation(Libs.multiDex)
}
