import java.util.Properties

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

val storeKeyProperties = Properties()
val storeFile = File("dataStore.properties")
if (storeFile.exists()) {
    storeFile.inputStream().use { inputStream ->
        storeKeyProperties.load(inputStream)
    }
} else {
    // Handle the case where the file doesn't exist (e.g., throw an error, log a warning)
    println("Warning: local.properties file not found.")
}

val propKeyProperties = Properties()
val propertiesFile = File("biometric.properties")
if (propertiesFile.exists()) {
    propertiesFile.inputStream().use { inputStream ->
        propKeyProperties.load(inputStream)
    }
} else {
    // Handle the case where the file doesn't exist (e.g., throw an error, log a warning)
    println("Warning: local.properties file not found.")
}

android {
    namespace = "com.complexparking.utils"
    compileSdk = 36

    defaultConfig {
        minSdk = 31
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    flavorDimensions("mode")

    productFlavors {
        create("development") {
            dimension = "mode"
            buildConfigField("String", "URL_TU_RECIBO_WEB_BASE_URL", "\"https://web.stage.turecibo.com\"")
            buildConfigField("String", "API_BASE_URL", "\"https://api.stage.turecibo.com/\"")
            buildConfigField("String", "BIOMETRIC_KEY", "\"${propKeyProperties.getProperty("property")}\"")
            buildConfigField("String", "DATA_STORE_KEY", "\"${storeKeyProperties.getProperty("property")}\"")
        }
        create("qa") {
            dimension = "mode"
            buildConfigField("String", "URL_TU_RECIBO_WEB_BASE_URL", "\"https://web.stage.turecibo.com\"")
            buildConfigField("String", "API_BASE_URL", "\"https://api.stage.turecibo.com/\"")
            buildConfigField("String", "BIOMETRIC_KEY", "\"${propKeyProperties.getProperty("property")}\"")
            buildConfigField("String", "DATA_STORE_KEY", "\"${storeKeyProperties.getProperty("property")}\"")
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
        buildConfig = true
    }

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.serializable)
    implementation(libs.org.apache.poi)
    implementation(libs.org.apache.poi.oxml)

    //compose
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))

    //datastore
    implementation(libs.androidx.datastore.preference)

    //Zxing
    implementation(libs.google.zxing)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}