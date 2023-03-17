pluginManagement {
  plugins {
    id("com.gradle.enterprise") version "3.12.4"
    id("com.github.ben-manes.versions") version "0.46.0"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("nl.littlerobots.version-catalog-update") version "0.8.0"
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.7"
  }
  repositories {
    gradlePluginPortal()
  }
}

plugins {
  id("com.gradle.enterprise")
}

gradleEnterprise {
  buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
  }
}

dependencyResolutionManagement {
  repositories {
    mavenCentral()
  }
}

rootProject.name = "fakesmtp"
