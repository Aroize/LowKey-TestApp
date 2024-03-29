@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

android {
    namespace = "ru.aroize.pexels"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.aroize.pexels"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        all {
            buildConfigField("String","PEXELS_API_KEY", "\"5Dh0GjDybo93D89Jhjb322YHWdWy1WV89Gavw10Atw4gcoFJrJqjaLkB\"")
//          buildConfigField("String","PEXELS_API_KEY", "\"${System.getenv()["PEXELS_API_KEY"]}\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(":core:arch"))
    implementation(project(":core:db"))
    implementation(project(":core:network"))

    implementation(project(":feed"))

    implementation(project(":details:api"))
    implementation(project(":details:impl"))

    implementation(libs.retrofit)

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}