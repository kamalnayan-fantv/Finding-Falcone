plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id ("kotlin-kapt")
}

android {
    namespace = "com.kn.data"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        buildConfigField("String", "BASE_URL", "\"https://findfalcone.geektrust.com/\"")
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
    android.buildFeatures.buildConfig= true
}

dependencies {
    implementation(project(path= ":core:commons"))
    implementation(project(path= ":core:domain"))
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.8.0")

    val hiltVersion="2.46.1"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    kapt ("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.4.2")

}