plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.capybara.network.data"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
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
    implementation(project(":libs:network:api"))
    implementation(project(":libs:capybara-network:api"))
    implementation(project(":libs:injector"))

    implementation(libs.koin.android)
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json)
}
