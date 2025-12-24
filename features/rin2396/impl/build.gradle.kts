plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.rin2396.impl"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    api(project(":features:rin2396:api"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.viewmodel)
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization.converter)
    implementation(libs.timber)
    implementation(project(":libs:injector"))
    testImplementation(libs.junit)
}
