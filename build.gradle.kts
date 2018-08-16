val MAIN_CLASS_NAME = "samples.HelloWorldKt"

plugins {
    application
    kotlin("jvm") version "1.2.41"
}

application {
    mainClassName  = MAIN_CLASS_NAME
}

dependencies {
    compile(kotlin("stdlib"))
}

repositories {
    mavenCentral()
}

val fatJar = task("fatJar", type = Jar::class) {
  baseName = "${project.name}-fat"
  manifest {
    attributes["Main-Class"] = MAIN_CLASS_NAME
  }
  from(configurations.runtime.map { if (it.isDirectory) it else zipTree(it) })
  with(tasks["jar"] as CopySpec)
}

tasks {
  "build" { dependsOn(fatJar) }
}
