plugins {
  alias(libs.plugins.composeMultiplatform) apply false
  alias(libs.plugins.composeCompiler) apply false
  alias(libs.plugins.kotlinMultiplatform) apply false
  id("convention.publication") apply false
}

allprojects {
  group = "in.procyk.adventofcode"
  version = "1.0.1"
}
