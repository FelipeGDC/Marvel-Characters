import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.safeargs)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.fgdc.marvelcharacters"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.fgdc.marvelcharacters"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val propertiesFile = project.rootProject.file("apikey.properties")
        val apikeyProperties = Properties()
        apikeyProperties.load(FileInputStream(propertiesFile))
        val apiBaseUrl: String = apikeyProperties.getProperty("MARVEL_API_BASE_URL") ?: ""
        val apiPublicKey: String = apikeyProperties.getProperty("MARVEL_API_PUBLIC_KEY") ?: ""
        val apiTs: String = apikeyProperties.getProperty("MARVEL_API_TS") ?: ""
        val apiHash: String = apikeyProperties.getProperty("MARVEL_API_HASH") ?: ""

        buildConfigField("String", "MARVEL_API_BASE_URL", apiBaseUrl)
        buildConfigField("String", "MARVEL_API_PUBLIC_KEY", apiPublicKey)
        buildConfigField("String", "MARVEL_API_TS", apiTs)
        buildConfigField("String", "MARVEL_API_HASH", apiHash)
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
            excludes.add("META-INF/DEPENDENCIES}")
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.coil)
    implementation(libs.coil.svg)
    implementation(libs.coil.network)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.core)
    implementation(libs.lottie)
    implementation(libs.moshi)
    ksp(libs.moshi.kotlin.codegen)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)

    // Testing
    testImplementation(libs.androidx.arch.core)
    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.mockk)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    /*    implementation kotlin.stdlib
        implementation android.androidxCore
        implementation android.lifecycleViewmodel
        implementation android.lifecycleLivedata
        implementation android.lifecycleRuntime
        implementation android.appcompat
        implementation android.material
        implementation android.constraintLayout
        implementation android.recyclerView

        implementation kotlin.coroutinesCore
        implementation kotlin.coroutines

        //Retrofit
        implementation network.retrofit
        implementation network.retrofitConverter
        implementation network.okhttpInterceptor

        //Moshi
        implementation network.moshi
        kapt network.moshiCodegen

        //Dagger Hilt
        implementation libs.daggerHilt
        kapt libs.daggerHiltCompiler

        //Navigation
        implementation android.navigationUi
        implementation android.navigationFragment

        //Coil
        implementation libs.coil
        implementation libs.coilSvg

        //Lottie
        implementation libs.lottie

        //Unit Testing
        androidTestImplementation testAndroid.espressoCore
        kaptAndroidTest test.daggerHiltCompiler
        testImplementation test.daggerHiltTesting
        testImplementation test.archCore
        testImplementation test.coroutinesTest
        testImplementation test.mockk
        testImplementation test.junit
        testImplementation test.kluent
        testImplementation test.turbine
        testImplementation testAndroid.espresso
        testImplementation testAndroid.robolectric
        androidTestImplementation testAndroid.extJunit
        androidTestImplementation test.coroutinesTest*/
}