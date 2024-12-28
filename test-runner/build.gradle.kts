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

  sourceSets {
    commonMain.dependencies {
      api(project(":solutions"))
      api(project(":runner"))

      implementation(libs.kotlin.test)
    }
  }
}
