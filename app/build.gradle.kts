plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.ikr_application"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.ikr_application"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
    }
}

dependencies {
    implementation(platform(libs.koin.bom))
    implementation(libs.androidx.core.ktx)

    implementation(libs.bundles.ui.core)
    implementation(libs.bundles.network)

    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.constraintlayout)

    implementation(libs.coil)
    implementation(libs.coil.network.okhttp)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit.kotlinx.serialization.converter)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.timber)
    implementation(libs.mpandroidchart)
    implementation(libs.joda.time)
    implementation(project(":features:nastyazz:impel"))
    implementation(project(":features:nfirex:impl"))
    implementation(project(":features:grigoran:impl"))
    implementation(project(":features:stupishin:impl"))
    implementation(project(":features:n0tsszzz:impl"))
    implementation(project(":features:artemkaa:impl"))
    implementation(project(":features:antohaot:impl"))
	implementation(project(":features:zagora:impl"))
    implementation(project(":features:drain678:impl"))
    implementation(project(":features:denisova:impl"))
    implementation(project(":features:dyatlova:api"))
    implementation(project(":features:dyatlova:impl"))
    implementation(project(":features:MomusWinner:impl"))
    implementation(project(":features:egorik4:impl"))
    implementation(project(":features:alexcode69:impl"))
    implementation(project(":features:rubsun:impl"))
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(project(":libs:injector"))
    implementation(project(":libs:primitivestorage:data"))
}
