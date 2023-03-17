import org.jetbrains.gradle.ext.ActionDelegationConfig.TestRunner
import org.jetbrains.gradle.ext.Gradle
import org.jetbrains.gradle.ext.delegateActions
import org.jetbrains.gradle.ext.runConfigurations
import org.jetbrains.gradle.ext.settings

plugins {
  id("application")
  id("com.github.ben-manes.versions")
  id("com.github.johnrengelman.shadow")
  id("nl.littlerobots.version-catalog-update")
  id("org.jetbrains.gradle.plugin.idea-ext")
}

group = "com.nilhcem.fakesmtp"

fun isNonStable(version: String): Boolean {
  val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
  val regex = "^[0-9,.v-]+(-r)?$".toRegex()
  val isStable = stableKeyword || regex.matches(version)
  return isStable.not()
}

tasks.dependencyUpdates {
  checkForGradleUpdate = true
  rejectVersionIf {
    isNonStable(candidate.version)
  }
}

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(11))
  }
}

idea {
  project {
    settings {
      runConfigurations {
        create<Gradle>("clean") { taskNames = listOf("clean") }
        create<Gradle>("run") { taskNames = listOf("run") }
      }
      delegateActions {
        delegateBuildRunToGradle = true
        testRunner = TestRunner.PLATFORM
      }
    }
  }
}

tasks.withType<JavaCompile>().configureEach {
  options.compilerArgs.add("-parameters")
  options.release.set(11)
}

tasks.test {
  useJUnitPlatform()
  maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
  reports {
    html.required.set(false)
    junitXml.required.set(false)
  }
}

dependencies {
  implementation(libs.annotations)
  implementation(libs.apple.java.ext)
  implementation(libs.commons.cli)
  implementation(libs.commons.io)
  implementation(libs.flatlaf)
  implementation(libs.jsystem.theme.detector)
  implementation(libs.logback.classic)
  implementation(libs.miglayout.swing)
  implementation(libs.slf4j.api)
  implementation(libs.subethasmtp)

  testImplementation(libs.assertj.core)
  testImplementation(libs.commons.email)
  testImplementation(libs.jupiter.api)
  testRuntimeOnly(libs.jupiter.engine)
}

application {
  mainClass.set("com.nilhcem.fakesmtp.FakeSMTP")
}
