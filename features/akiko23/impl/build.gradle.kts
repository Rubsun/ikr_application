plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.akiko23.impl"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.bundles.ui.core)

    api(project(":features:akiko23:api"))
    implementation(project(":libs:injector"))
    implementation(project(":libs:coil:api"))
    implementation(project(":libs:coil:data"))
    implementation(project(":libs:akiko23-network:api"))
    implementation(project(":libs:akiko23-network:data"))
    implementation(project(":libs:primitivestorage:api"))
    implementation(project(":libs:primitivestorage:data"))
    implementation(project(":libs:arch"))
}

