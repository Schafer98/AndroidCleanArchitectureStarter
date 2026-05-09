// =============================================================================
// APP MODULE BUILD FILE
// -----------------------------------------------------------------------------
// Applies plugins and declares every library the app code needs.
// Note the two DI plugins: `dagger.hilt` turns Hilt on, `hilt.ksp` (which is
// really just the KSP plugin) gives it a compile-time code generator.
// =============================================================================
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.dagger.hilt)   // turns Hilt DI on
    alias(libs.plugins.hilt.ksp)      // actually the KSP plugin (named "hilt-ksp" in the toml)
}

android {
    namespace = "com.dev.androidcleanarchitecturestarter"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.dev.androidcleanarchitecturestarter"
        minSdk = 26
        targetSdk = 35
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    // --- Compose / AndroidX core ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Needed for collectAsStateWithLifecycle() in Composables.
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")

    // --- Navigation & ViewModel in Compose ---
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // --- Hilt (DI) ---
    implementation(libs.hilt.android)
    // CRITICAL: hilt-compiler has to be wired in via `ksp(...)`, not
    // `implementation(...)`. Otherwise Hilt generates no code and DI fails
    // at runtime with "missing binding" errors.
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // --- Retrofit + OkHttp ---
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    // --- Tests ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
}
