plugins {
    alias(libs.plugins.android.application)
}

android {

    buildFeatures {
        buildConfig = true
        viewBinding = true

    }

    namespace = "com.hammerbyte.sahas"
    compileSdk = 34

    signingConfigs {
        create("release") {
            keyAlias = "sahas"
            storePassword = "Bhavin1203#"
            keyPassword = "Bhavin1203#"
            storeFile = file("./Sahas.jks")
        }
    }


    defaultConfig {
        applicationId = "com.hammerbyte.sahas"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug{
            isMinifyEnabled = false
            isDebuggable = true
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}