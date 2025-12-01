[![Platforms](https://img.shields.io/badge/web-WebAssembly-blue)](https://github.com/avan1235/advent-of-code-kotlin/releases/latest)
[![Platforms](https://img.shields.io/badge/desktop-Windows%20%7C%20macOS%20%7C%20Linux-blue)](https://github.com/avan1235/advent-of-code-kotlin/releases/latest)

[![License: MIT](https://img.shields.io/badge/License-MIT-orange.svg)](./LICENSE.md)
[![Maven Central Version](https://img.shields.io/maven-central/v/in.procyk.adventofcode/solutions?label=Maven%20Central&color=orange)](https://central.sonatype.com/namespace/in.procyk.adventofcode)

[![GitHub Repo stars](https://img.shields.io/github/stars/avan1235/advent-of-code-kotlin?style=social)](https://github.com/avan1235/advent-of-code-kotlin/stargazers)
[![Fork Advent of Code in Kotlin](https://img.shields.io/github/forks/avan1235/advent-of-code-kotlin?logo=github&style=social)](https://github.com/avan1235/advent-of-code-kotlin/fork)

# 🎄🎁🎅 Advent of Code in Kotlin 🎅🎁🎄

Kotlin Multiplatform utility library for bringing solutions for Advent of Code
and sharing them as an interactive solver.

## Project Contents

### Kotlin Libraries

#### [solutions](./solutions/src/commonMain/kotlin/in/procyk/adventofcode/solutions)

`implementation("in.procyk.adventofcode:solutions:1.1.0")`

- [AdventDay](./solutions/src/commonMain/kotlin/in/procyk/adventofcode/solutions/AdventDay.kt) is a single day solution
- [Advent](./solutions/src/commonMain/kotlin/in/procyk/adventofcode/solutions/Advent.kt) is a collection of solutions
  from a single year
- utility functions and classes that are helpful so far in solving the tasks

#### [runner](./runner/src/commonMain/kotlin/in/procyk/adventofcode/runner)

`implementation("in.procyk.adventofcode:runner:1.1.0")`

- [FileAdventInputReader](./runner/src/commonMain/kotlin/in/procyk/adventofcode/runner/FileAdventInputReader.kt) is an
  instance of `AdventDay.InputReader` that allows to read inputs from system file
- [Advent.solve](./runner/src/commonMain/kotlin/in/procyk/adventofcode/runner/Advent.kt) is a utility function to solve
  single day from current year if executed during the Advent of Code time, while it runs all days on other days. It's
  intended to run current day when prototyping the solution for current day.

#### [test-runner](./test-runner/src/commonMain/kotlin/in/procyk/adventofcode/runner)

`implementation("in.procyk.adventofcode:test-runner:1.1.0")`

- [AdventTest](./test-runner/src/commonMain/kotlin/in/procyk/adventofcode/runner/AdventTest.kt) has easy assertions of
  solutions from [AdventDay](./solutions/src/commonMain/kotlin/in/procyk/adventofcode/solutions/AdventDay.kt)

#### [solver](./solver/src/commonMain/kotlin/in/procyk/adventofcode/solver)

`implementation("in.procyk.adventofcode:solver:1.1.0")`

- [AdventSolver](./solver/src/commonMain/kotlin/in/procyk/adventofcode/solver/AdventSolver.kt) implements UI for
  providing solutions and log outputs for Advent of Code based
  on [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform)
- [adventWebSolver](./solver/src/wasmJsMain/kotlin/in/procyk/adventofcode/solver/AdventWebSolver.kt) is a web entrypoint
  for UI
- [adventJvmSolver](./solver/src/jvmMain/kotlin/in/procyk/adventofcode/solver/AdventJvmSolver.kt) is a desktop
  entrypoint for UI

### Publishing Utilities

#### [convention-plugins](./convention-plugins/src/main/kotlin)

- [convention.publication.gradle.kts](./convention-plugins/src/main/kotlin/convention.publication.gradle.kts) enables
  easy configuration of publication for each project where applied

#### [GitHub Actions](./.github/workflows)

- [release.yml](./.github/workflows/release.yml) implements a GitHub Action that publishes Kotlin Multiplatform
  libraries to [Maven Central](https://central.sonatype.com/) when git tag in format `v*.*.*` with a version matching
  version configured in [build.gradle.kts](./build.gradle.kts) is pushed. Requires following secrets to be configured:
  - `OSSRH_USERNAME`: a `username` generated from [Maven Central Panel](https://s01.oss.sonatype.org/#profile;User%20Token)
  - `OSSRH_PASSWORD`: a `password` generated from [Maven Central Panel](https://s01.oss.sonatype.org/#profile;User%20Token)
  - `SIGNING_KEY_ID`: last 8 bytes of signing key ID that can be checked with `gpg --list-secret-keys --keyid-format SHORT`
  - `SIGNING_PASSWORD`: password for signing key
  - `SIGNING_KEY`: signing key in a format extracted with `gpg --armor --export-secret-key 'example@gmail.com' | grep -v '\-\-' | grep -v '=.' | tr -d '\n'`

## Version Catalog

```toml
[versions]
procyk-adventofcode = "1.1.0"

[libraries]
procyk-adventofcode-runner = { module = "in.procyk.adventofcode:runner", version.ref = "procyk-adventofcode" }
procyk-adventofcode-solutions = { module = "in.procyk.adventofcode:solutions", version.ref = "procyk-adventofcode" }
procyk-adventofcode-solver = { module = "in.procyk.adventofcode:solver", version.ref = "procyk-adventofcode" }
procyk-adventofcode-test-runner = { module = "in.procyk.adventofcode:test-runner", version.ref = "procyk-adventofcode" }
```
