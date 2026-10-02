import com.vanniktech.maven.publish.Checksum

plugins {
  id("com.vanniktech.maven.publish")
}

mavenPublishing {
  checksums(Checksum.MD5, Checksum.SHA1)
  excludeSignatureChecksums(true)
  publishToMavenCentral(automaticRelease = true)

  signAllPublications()

  pom {
    val githubUrl = "https://github.com/avan1235/advent-of-code-kotlin"

    name.set("Advent of Code in Kotlin")
    description.set("Advent of Code Library")
    inceptionYear.set("2024")
    url.set(githubUrl)

    licenses {
      license {
        name.set("MIT")
        url.set("https://opensource.org/licenses/MIT")
        distribution.set("https://opensource.org/licenses/MIT")
      }
    }
    developers {
      developer {
        id.set("avan1235")
        name.set("Maciej Procyk")
        email.set("maciej@procyk.in")
        url.set("https://procyk.in")
      }
    }
    issueManagement {
      system.set("GitHub")
      url.set("$githubUrl/issues")
    }
    scm {
      url.set(githubUrl)
      connection.set("scm:git:git://github.com/avan1235/advent-of-code-kotlin.git")
      developerConnection.set("scm:git:ssh://git@github.com/avan1235/advent-of-code-kotlin.git")
    }
  }
}
