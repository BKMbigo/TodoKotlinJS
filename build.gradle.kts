import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackCssMode

plugins {
    kotlin("js") version "1.8.10"
    kotlin("plugin.serialization") version "1.8.10"
}

repositories {
    mavenCentral()
}

kotlin {
    js(IR) {
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                    mode.set(KotlinWebpackCssMode.INLINE)
                }
            }
            testTask {
                testLogging {
                    showExceptions = true
                    exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
                    showCauses = true
                    showStackTraces = true
                }
            }
        }
        binaries.executable()
    }
}
dependencies {
    implementation(enforcedPlatform("org.jetbrains.kotlin-wrappers:kotlin-wrappers-bom:1.0.0-pre.354"))
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom-legacy")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-styled")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.6.4")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

    implementation("dev.gitlive:firebase-firestore:1.6.2")

    implementation(npm("react-icons", "4.7.1"))
    //implementation(npm("firebase", "9.17.1"))
    implementation(npm("sass", "^1.29.0"))
    implementation(npm("sass-loader", "^10.1.0"))
}
