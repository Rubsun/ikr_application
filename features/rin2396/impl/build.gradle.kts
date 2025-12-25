plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.rin2396.impl"
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
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.recyclerview)
    
    // DataStore для персистентного хранилища
    implementation(libs.androidx.datastore)
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    
    // Coil для загрузки картинок
    // use local coil module from workspace to avoid external artifact issues
    implementation(project(":libs:coil:data"))
    
    // Koin для DI
    implementation(libs.bundles.koin)
    
    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    
    // Сериализация
    implementation(libs.kotlinx.serialization.json)
    
    // API модуль
    api(project(":features:rin2396:api"))
    
    // Network библиотека
    implementation(project(":libs:rin2396-network:data"))
    
    // Injector для инициализации модуля
    implementation(project(":libs:injector"))
    
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}