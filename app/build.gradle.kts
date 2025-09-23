plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.jetbrainsKotlinSerialization)
    alias(libs.plugins.kapt)
}

android {
    namespace = "com.complexparking"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.complexparking"
        minSdk = 31
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    flavorDimensions("mode")

    productFlavors {
        create("development") {
            dimension = "mode"
            buildConfigField("String", "URL_TU_RECIBO_WEB_BASE_URL", "\"https://web.stage.turecibo.com\"")
            buildConfigField("String", "API_BASE_URL", "\"https://api.stage.turecibo.com/\"")
        }
        create("qa") {
            dimension = "mode"
            buildConfigField("String", "URL_TU_RECIBO_WEB_BASE_URL", "\"https://web.stage.turecibo.com\"")
            buildConfigField("String", "API_BASE_URL", "\"https://api.stage.turecibo.com/\"")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar"))))
    implementation(fileTree(mapOf("dir" to "libs/", "include" to listOf("*.aar", "*.jar"))))

    //Modules
    api(project(":utils"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation)
    implementation(libs.androidx.constraintLayout)
    implementation(libs.androidx.serializable)
    implementation(libs.androidx.koin)
    implementation(libs.androidx.koin.workmanager)
    implementation(libs.androidx.koin.navigation)
    implementation(libs.google.permission)
   /* implementation(libs.org.apache.poi)
    implementation(libs.org.apache.poi.oxml)*/

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Room
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)
}