import com.lucidchart.sbt.scalafmt.ScalafmtCorePlugin.autoImport._
import com.typesafe.sbt.SbtNativePackager.autoImport.{maintainer, packageName, executableScriptName}
import com.typesafe.sbt.packager.archetypes.scripts.BashStartScriptPlugin.autoImport.bashScriptDefines
import com.typesafe.sbt.packager.docker.DockerPlugin.autoImport._
import sbt.Keys._
import sbt._

object Settings {

  val sdk8 = "adoptopenjdk/openjdk8:x86_64-ubuntu-jdk8u212-b03-slim"

  lazy val commonDependenciesSettings = Seq(
    libraryDependencies ++= Seq(
      Circe.core,
      Circe.generic,
      Circe.parser,
      Circe.shapes,
      Akka.http,
      Akka.stream,
      Akka.persistence,
      Akka.`persistence-query`,
      Akka.cluster,
      Akka.clusterTools,
      Akka.clusterSharding,
      Akka.slf4j,
      Akka.contrib,
      Akka.`akka-http-crice`,
      Akka.cors,
      Logback.classic,
      LogstashLogbackEncoder.encoder,
      Config.core,
      Monix.version
    ),
    scalafmtOnCompile in Compile := true,
    scalafmtTestOnCompile in Compile := true
  )

  lazy val dockerSettings = Seq(
    fork := true,
    dockerBaseImage := sdk8,
    packageName in Docker := "scala-server",
    executableScriptName := "scala-server",
    version := "latest",
    dockerUpdateLatest := true,
    maintainer in Docker := "BambooTuna <bambootuna@gmail.com>",
    dockerUsername := Some("bambootuna"),
    mainClass in (Compile, bashScriptDefines) := Some(s"com.github.BambooTuna.${name.value}.apiServer.Main"),
    dockerExposedPorts := Seq(18080)
  )

}
