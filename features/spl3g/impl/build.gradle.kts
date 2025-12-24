plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.spl3g.impl"
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
    implementation(libs.androidx.core.ktx)

    implementation(libs.bundles.ui.core)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.constraintlayout)
	api(libs.kotlinx.coroutines.core)

    implementation(libs.coil)
    implementation(libs.coil.network.okhttp)

    implementation(libs.bundles.koin)
    implementation(libs.bundles.network)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    api(project(":features:spl3g:api"))
    implementation(project(":libs:injector"))
    implementation(project(":libs:primitivestorage:api"))
	implementation(project(":libs:spl3g-network:api"))
}