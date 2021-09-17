plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdkVersion(Versions.targetSdkVersion.toString())

    defaultConfig {
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.targetSdkVersion)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
        }
    }
}

dependencies {
    implementation(Libs.coreKtx)
    implementation(Libs.appCompat)
    implementation(Libs.material)
    testImplementation(Libs.junit)
}
