import Settings._

lazy val commonSettings = Seq(
  name := "ScalaTemplate",
  version := "0.1",
  scalaVersion := "2.12.11"
)

lazy val apiServer = (project in file("apiServer"))
  .enablePlugins(JavaAppPackaging, AshScriptPlugin, DockerPlugin)
  .settings(commonSettings)
  .settings(commonDependenciesSettings)
  .settings(dockerSettings)

lazy val root =
  (project in file("."))
    .aggregate(apiServer)
    .settings(
      commands += Command.command("fullCompile") { st =>
        println("==+ clean +==")
        val st1 = Command.process("clean", st)
        println("==+ compile +==")
        val st2 = Command.process("compile", st1)
        println("==+ docker:stage +==")
        val st3 = Command.process("docker:stage", st2)
        st3
      }
    )
