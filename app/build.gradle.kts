import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.apollo)

    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp )
    alias(libs.plugins.android.hilt)


    id("kotlin-parcelize")

}

android {
    namespace = "com.example.apollo_launches"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.apollo_launches"
        minSdk = 33
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt") ,
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11) //
    }
}

apollo {
    service("apollo_service") {
        packageName.set("com.example.apollo_launches.graphql")
        schemaFile.set(file("src/main/graphql/schema.graphqls"))
        introspection {
            endpointUrl.set(
                "https://apollo-fullstack-tutorial.herokuapp.com/graphql"
            )
        }
    }
}


dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    //apollo
    implementation(libs.apollo.runtime)

    //dagger
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    //navigation
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization)

    implementation(libs.lifecycle.viewmodel)

    implementation(libs.accompanist.swipe.refresh)
    implementation(libs.coil)

    //implementation(libs.kotlin.metadata.jvm)
    ksp("org.jetbrains.kotlin:kotlin-metadata-jvm:2.3.0-Beta1")
}
