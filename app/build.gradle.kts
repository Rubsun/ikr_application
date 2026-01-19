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

    packaging {
        resources {
            pickFirsts += "META-INF/versions/9/OSGI-INF/MANIFEST.MF"
            merges += "META-INF/services/com.example.injector.AbstractInitializer"
        }
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
    }
}

dependencies {
    // core
    implementation(libs.bundles.ui.core)
    implementation(project(":libs:injector"))
    implementation(project(":libs:arch"))


    // libs
    implementation(project(":libs:rubsun-chart:data"))
    implementation(project(":libs:rubsun-network:data"))
    implementation(project(":libs:rubsun-storage:data"))
    implementation(project(":libs:vtyapkova-network:data"))
    implementation(project(":libs:fomin-network:data"))
    implementation(project(":libs:primitivestorage:data"))
    implementation(project(":libs:imageloader:data"))
    implementation(project(":libs:jikan:data"))
    implementation(project(":libs:egorik4-network:data"))
    implementation(project(":libs:n0tsszzz-network:data"))
    implementation(project(":libs:alexcode69-network:data"))
    implementation(project(":libs:artemkaa-network:data"))
    implementation(project(":libs:antohaot-network:data"))
    implementation(project(":libs:drain678-network:data"))
    implementation(project(":libs:chart:data"))
    implementation(project(":libs:capybara-network:data"))
    implementation(project(":libs:spl3g-network:data"))
    implementation(project(":libs:lyrics:impl"))
    implementation(project(":libs:momuswinner-network:data"))
    implementation(project(":libs:momuswinner-chart:data"))
    implementation(project(":libs:dimmension-imageloader:data"))
    implementation(project(":libs:dimmension-network:data"))
    implementation(project(":libs:argun-network:data"))
    implementation(project(":libs:grigoran-network:data"))
    implementation(project(":libs:nastyazz:data"))
    implementation(project(":libs:michaelnoskov-chart:data"))
    implementation(project(":libs:n0tsszzz-network:data"))
    implementation(project(":libs:michaelnoskov-network:data"))
    implementation(project(":libs:catLover:data"))
    implementation(project(":libs:roomstorage:data"))
    implementation(project(":libs:telegin-network:data"))
    implementation(project(":libs:telegin-storage:data"))

    debugImplementation(project(":libs:logger:timber"))
    releaseImplementation(project(":libs:logger:stub"))


    // features
    implementation(project(":features:screens:impl"))
    implementation(project(":features:quovadis:impl"))
    implementation(project(":features:nastyazz:impel"))
    implementation(project(":features:dimmension:impl"))
    implementation(project(":features:grigoran:impl"))
    implementation(project(":features:stupishin:impl"))
    implementation(project(":features:n0tsszzz:impl"))
    implementation(project(":features:artemkaa:impl"))
    implementation(project(":features:antohaot:impl"))
    implementation(project(":features:zagora:impl"))
    implementation(project(":features:drain678:impl"))
    implementation(project(":features:denisova:impl"))
    implementation(project(":features:dyatlova:impl"))
    implementation(project(":features:MomusWinner:impl"))
    implementation(project(":features:michaelnoskov:impl"))
    implementation(project(":features:egorik4:impl"))
    implementation(project(":features:alexcode69:impl"))
    implementation(project(":features:demyanenko:impl"))
    implementation(project(":features:rubsun:impl"))
    implementation(project(":features:tire:impl"))
    implementation(project(":features:eremin:impl"))
    implementation(project(":features:akiko23:impl"))
    implementation(project(":features:kristevt:impl"))
    implementation(project(":features:vtyapkova:impl"))
    implementation(project(":features:spl3g:impl"))
    implementation(project(":features:argun:impl"))
    implementation(project(":features:fomin:impl"))
    implementation(project(":features:telegin:impl"))

// very strange module:)
//    implementation(project(":features:rin2396:impl"))
}
