plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

afterEvaluate {
    publishing {
        publications {
            // 创建 Maven 发布配置
            create<MavenPublication>("mavenJava") {
                // 指定要发布的组件
                from(components["release"])

                // 添加库的元数据
                groupId = "com.github.itsjameswang"
                artifactId = "android-demo-lib"
                version = "1.1"
            }
        }
        repositories {               // << --- ADD This
            mavenLocal()
        }

        // 配置 Maven 存储库
//        repositories {
//            maven {
//
//                // 指定 Maven 仓库 URL，下面以 GitHub Packages 为例
//                url = uri("https://maven.pkg.github.com/itsjameswang/LibraryTesting")
//
//                // 配置访问凭证
//                credentials {
//                    username = "itsjameswang" // GitHub 用户名或环境变量
//                    password = "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"   // GitHub 令牌或环境变量
//                }
//            }
//        }
    }
}

android {
    namespace = "com.itsjames.android_demo_lib"
    compileSdk = 33

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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
//    implementation("com.google.android.material:material:1.11.0")
    implementation("com.google.android.material:material:1.5.0-alpha03")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("com.android.volley:volley:1.2.1")
}