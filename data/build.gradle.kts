/*
 * © 2025 Hugo Polo - Github: https://github.com/LunaryLayn
 */

import org.gradle.declarative.dsl.schema.FqName.Empty.packageName
import org.gradle.kotlin.dsl.apollo

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android) // 🔥 Plugin de Hilt
    kotlin("kapt")
    alias(libs.plugins.apollo)
}

android {
    namespace = "com.hugopolog.data"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    apollo {
        service("service") {
            packageName.set("com.hugopolog.data.graphql")
            introspection {
                endpointUrl.set("graphql.pokeapi.co/v1beta2")
                schemaFile.set(file("src/main/graphql/com/hugopolog/data/schema.json"))
            }
        }
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.logging)
    implementation(libs.paging.runtime)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.apollo.runtime)
}