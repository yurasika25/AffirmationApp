import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization) }

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            freeCompilerArgs += listOf("-Xbinary=bundleId=com.affirmation.app")
        }
    }
    
    sourceSets {
        
        androidMain.dependencies {
            implementation("io.ktor:ktor-client-android:2.3.4")

            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation("io.ktor:ktor-client-okhttp:2.3.12")
        }


        commonMain.dependencies {

            implementation("cafe.adriel.voyager:voyager-navigator:1.0.1")
            implementation("cafe.adriel.voyager:voyager-transitions:1.0.0")

            implementation("io.ktor:ktor-client-logging:2.3.5")
            implementation(libs.kotlinx.serialization.json)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            // Lifecycle
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)


            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(compose.material3)
            implementation("cafe.adriel.voyager:voyager-navigator:1.1.0-beta02")
            implementation("cafe.adriel.voyager:voyager-transitions:1.1.0-beta02")
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0")

            implementation("io.ktor:ktor-client-core:2.3.12")
            implementation("io.ktor:ktor-client-content-negotiation:2.3.12")
            implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.12")


        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        iosMain.dependencies {
            implementation("io.ktor:ktor-client-darwin:2.3.12")
        }
    }
}

android {
    namespace = "com.affirmation.app"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.affirmation.app"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

val frameworkName = "ComposeApp"
val xcFrameworkOutput = buildDir.resolve("XCFrameworks/$frameworkName.xcframework")

tasks.register("assembleXCFramework") {
    group = "build"
    description = "Builds XCFramework for use in Xcode"

    dependsOn(
        "linkDebugFrameworkIosArm64",
        "linkDebugFrameworkIosX64",
        "linkDebugFrameworkIosSimulatorArm64"
    )

    doLast {
        val arm64Path = buildDir.resolve("bin/iosArm64/debugFramework/$frameworkName.framework")
        val x64Path = buildDir.resolve("bin/iosX64/debugFramework/$frameworkName.framework")
        val simArm64Path = buildDir.resolve("bin/iosSimulatorArm64/debugFramework/$frameworkName.framework")

        if (xcFrameworkOutput.exists()) {
            xcFrameworkOutput.deleteRecursively()
        }

        exec {
            commandLine(
                "xcodebuild", "-create-xcframework",
                "-framework", arm64Path.absolutePath,
                "-framework", x64Path.absolutePath,
                "-framework", simArm64Path.absolutePath,
                "-output", xcFrameworkOutput.absolutePath
            )
        }

        println("XCFramework created at: $xcFrameworkOutput")
    }
}

