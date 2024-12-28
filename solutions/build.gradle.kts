@file:OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)

plugins {
  alias(libs.plugins.kotlinMultiplatform)
  id("convention.publication")
}

kotlin {
  jvm()
  listOf(
    macosArm64(),
    macosX64(),
    linuxArm64(),
    linuxX64(),
    mingwX64(),
  )
  wasmJs {
    browser()
  }

  sourceSets {
    commonMain.dependencies {
      api(libs.kotlinx.coroutines.core)
      api(libs.kotlinx.datetime)
      api(libs.kotlinx.io.core)
      api(libs.kotlin.bignum)
    }
  }
}
