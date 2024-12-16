import java.util.Properties


plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.rk.terminal"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.rk.terminal"
        minSdk = 24
        targetSdk = 36
        versionCode = 218
        versionName = "0.218.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            val isGITHUB_ACTION = System.getenv("GITHUB_ACTIONS") == "true"

            val propertiesFilePath = if (isGITHUB_ACTION) {
                "/tmp/signing.properties"
            } else {
                "/home/rohit/signing.properties"
            }

            val propertiesFile = File(propertiesFilePath)
            if (propertiesFile.exists()) {
                val properties = Properties()
                properties.load(propertiesFile.inputStream())
                keyAlias = properties["keyAlias"] as String?
                keyPassword = properties["keyPassword"] as String?
                storeFile = if (isGITHUB_ACTION) {
                    File("/tmp/xed.keystore")
                } else {
                    (properties["storeFile"] as String?)?.let { File(it) }
                }

                storePassword = properties["storePassword"] as String?
            } else {
                println("Signing properties file not found at $propertiesFilePath")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }



    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.terminal.view)
    implementation(libs.terminal.emulator)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.utilcode)
}