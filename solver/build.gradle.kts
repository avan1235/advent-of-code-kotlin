@file:OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)

plugins {
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.composeMultiplatform)
  alias(libs.plugins.composeCompiler)
  id("convention.publication")
}

kotlin {
  jvm()
  wasmJs {
    browser()
  }

  sourceSets {
    commonMain.dependencies {
      api(project(":solutions"))

      implementation(libs.kotlinx.coroutines.core)
      implementation(libs.kotlinx.datetime)

      implementation(compose.foundation)
      implementation(compose.material3)
      implementation(compose.materialIconsExtended)
      implementation(compose.components.resources)
    }

    webMain.dependencies {
      api(libs.kotlinx.browser)
    }
  }
}
