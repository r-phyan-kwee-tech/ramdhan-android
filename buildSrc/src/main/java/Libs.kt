object Libs {
    const val gradleAndroid = "com.android.tools.build:gradle:${Versions.gradleAndroidVersion}"
    const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
    const val kotlinLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"
    const val kotlinCoroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9"
    const val kotlinCoroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.0"
    const val licenseTools = "com.cookpad.android.licensetools:license-tools-plugin:${Versions.licenseToolsVersion}"
    const val fabric = "io.fabric.tools:gradle:${Versions.fabricVersion}"
    const val googleService = "com.google.gms:google-services:${Versions.googleServiceVersion}"

    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"
    const val material = "com.google.android.material:material:${Versions.materialVersion}"
    const val viewPager2 = "androidx.viewpager2:viewpager2:${Versions.viewPager2Version}"

    const val viewPagerIndicator = "fr.avianey.com.viewpagerindicator:library:${Versions.viewPagerIndicatorVersion}"

    //lifecycle
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleVersion}"
    const val liveDataRxSupport = "androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.lifecycleVersion}"

    //room
    const val room = "androidx.room:room-ktx:${Versions.roomVersion}"
    const val roomProcessor = "androidx.room:room-compiler:${Versions.roomVersion}"
    const val roomRxSupport = "androidx.room:room-rxjava2:${Versions.roomVersion}"
    const val roomTesting = "androidx.room:room-testing:${Versions.roomVersion}"

    //rxjava
    const val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxJavaVersion}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroidVersion}"

    //retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    const val retrofitRxAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofitVersion}"
    const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}"
    const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpLoggerVersion}"

    //dagger
    const val daggerProcessor = "com.google.dagger:dagger-compiler:${Versions.daggerVersion}"
    const val daggerAndroid = "com.google.dagger:dagger-android:${Versions.daggerVersion}"
    const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${Versions.daggerVersion}"
    const val daggerAndroidProcessor = "com.google.dagger:dagger-android-processor:${Versions.daggerVersion}"

    //test
    const val junit = "junit:junit:${Versions.junitVersion}"
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.mockWebServerVersion}"

    //todo migrate to 2
    const val mockitoKotlin1 = "com.nhaarman:mockito-kotlin:${Versions.mockitoKotlin1Version}"
    const val mockitoKotlin = "org.mockito.kotlin:mockito-kotlin:${Versions.mockitoKotlinVersion}"
    const val archCoreTesting = "androidx.arch.core:core-testing:${Versions.archCoreTestingVersion}"
    const val testRunner = "androidx.test:runner:${Versions.testRunnerVersion}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoVersion}"

    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timberVersion}"

    //firebase
    const val firebaseCore = "com.google.firebase:firebase-core:${Versions.firebaseCoreVersion}"

    //crashLy
    const val crashlytic = "com.crashlytics.sdk.android:crashlytics:${Versions.crashlyticsVersion}"

    const val rabbit = "org.rabbit-converter.rabbit:rabbit:${Versions.rabbitVersion}"

    const val multiDex = "androidx.multidex:multidex:${Versions.multiDexVersion}"
}
