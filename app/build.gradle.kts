plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id ("kotlin-kapt")
    id ("kotlin-android")
}

android {
    namespace = "com.kn.findingthefalcon"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.kn.findingthefalcon"
        minSdk = 24
        targetSdk = 33
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
        dataBinding = true
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
    implementation(project(path= "::core:commons"))
    implementation(project(path= "::core:data"))
    implementation(project(path= ":core:domain"))
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    val hiltVersion="2.46.1"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    kapt ("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.4.2")

    val lifecycleKtx="2.4.0"
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleKtx")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleKtx")

    implementation("androidx.activity:activity-ktx:1.5.1")
}