plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.project"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.project"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}


dependencies {
    implementation ("com.github.dimorinny:show-case-card-view:0.0.4")
    implementation ("de.hdodenhof:circleimageview:2.2.0")

    implementation ("com.lorentzos.swipecards:library:1.0.9")
    //fire base
    implementation ("com.google.firebase:firebase-auth:17.0.0")
    implementation ("com.google.firebase:firebase-database:18.0.0")
    implementation ("com.google.firebase:firebase-storage:17.0.0")
    implementation ("com.google.firebase:firebase-messaging:19.0.0")

    implementation ("com.onesignal:OneSignal:3.10.1")

    implementation ("com.github.BakerJQ:Android-InfiniteCards:1.0.5")

    implementation ("com.github.bumptech.glide:glide:3.7.0")
    implementation ("com.github.ittianyu:BottomNavigationViewEx:1.2.4")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
