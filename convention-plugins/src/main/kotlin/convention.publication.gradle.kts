plugins {
  id("maven-publish") apply true
  id("signing") apply true
}

ext["signing.keyId"] = System.getenv("SIGNING_KEY_ID")
ext["signing.password"] = System.getenv("SIGNING_PASSWORD")
ext["signing.key"] = System.getenv("SIGNING_KEY")
ext["ossrhUsername"] = System.getenv("OSSRH_USERNAME")
ext["ossrhPassword"] = System.getenv("OSSRH_PASSWORD")

operator fun ExtraPropertiesExtension.invoke(name: String) = ext[name]?.toString()

afterEvaluate {
  publishing {
    repositories {
      maven {
        name = "sonatype"
        setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
        credentials {
          username = ext("ossrhUsername")
          password = ext("ossrhPassword")
        }
      }
      mavenLocal()
    }

    publications.withType<MavenPublication> {
      val publication = this
      val javadocJar = tasks.register("${publication.name}JavadocJar", Jar::class) {
        archiveClassifier = "javadoc"
        archiveBaseName = "${archiveBaseName.get()}-${publication.name}"
      }
      artifact(javadocJar)
      pom {
        val githubUrl = "https://github.com/avan1235/advent-of-code-kotlin"

        name = "Advent of Code in Kotlin"
        description = "Advent of Code Library"
        url = githubUrl

        licenses {
          license {
            name = "MIT"
            url = "https://opensource.org/licenses/MIT"
          }
        }
        developers {
          developer {
            id = "avan1235"
            name = "Maciej Procyk"
            email = "maciej@procyk.in"
            url = "https://procyk.in"
          }
        }
        issueManagement {
          system = "GitHub"
          url = "$githubUrl/issues"
        }
        scm {
          url = githubUrl
        }
      }
    }
  }

  signing {
    useInMemoryPgpKeys(
      ext("signing.keyId"),
      ext("signing.key"),
      ext("signing.password"),
    )
    sign(publishing.publications)
  }
}
