import sbt.File

lazy val serviceName = sys.env.get("SRV_NAME") match {
  case Some(sName) => sName
  case _ => "web-crawler"
}

Global / onChangedBuildSource := ReloadOnSourceChanges
Global / useSuperShell := false
Global / maxErrors := 4

organization := "com.vers.web-crawler"
name := "web-crawler"

version := "0.1"

scalaVersion := "2.13.8"

ThisBuild / scalacOptions += "-Wconf:any:wv"

lazy val root = (project in file("."))
  .settings(
    resolvers ++= Seq(
      ("Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots")
        .withAllowInsecureProtocol(true)
    ),
    developers := List(
      Developer("1", "Andrey Vershinin", "Andreyu008@gmail.com", null)
    )
  )

val betterFilesVersion = "3.8.0"
val circeVersion = "0.13.0"
val http4sVersion = "0.21.23"
val log4catsVersion = "1.0.1"
val catsVersion = "2.6.0"
val enumeratumCirceVersion = "1.5.22"

libraryDependencies ++= Seq(
  //typeLevel
  "org.typelevel"  %% "cats-core"          % catsVersion,
  "dev.profunktor" %% "redis4cats-effects" % "0.13.1",
  //circe
  "io.circe"     %% "circe-generic"        % circeVersion,
  "io.circe"     %% "circe-literal"        % circeVersion,
  "io.circe"     %% "circe-generic-extras" % circeVersion,
  "io.circe"     %% "circe-parser"         % circeVersion,
  "io.circe"     %% "circe-config"         % "0.7.0",
  "com.beachape" %% "enumeratum-circe"     % enumeratumCirceVersion,
  //http4s
  "org.http4s"                   %% "http4s-core"         % http4sVersion,
  "org.http4s"                   %% "http4s-blaze-server" % http4sVersion,
  "org.http4s"                   %% "http4s-circe"        % http4sVersion,
  "org.http4s"                   %% "http4s-dsl"          % http4sVersion,
  "org.http4s"                   %% "http4s-client"       % http4sVersion,
  "com.softwaremill.sttp.client" %% "http4s-backend"      % "2.2.3",
  //log
  "ch.qos.logback"       % "logback-classic"          % "1.2.3",
  "org.codehaus.janino"  % "janino"                   % "2.7.5",
  "net.logstash.logback" % "logstash-logback-encoder" % "5.0",
  "io.chrisdavenport"    %% "log4cats-slf4j"          % log4catsVersion,
  "org.scalatest" % "scalatest_2.13" % "3.1.1" % "test",
)

assembly / mainClass := Some("com.vers.web-crawler.Crawler")
assembly / assemblyOutputPath := new File(s"out/$serviceName.jar")
assembly / test := {}

assembly / assemblyMergeStrategy := {
  case PathList("META-INF", "maven", "org.webjars", "swagger-ui", "pom.properties") =>
    MergeStrategy.singleOrError
  case PathList(p @ _*) if p.last == "module-info.class" =>
    MergeStrategy.discard
  case "scala-collection-compat.properties" => MergeStrategy.last
  case PathList("scala", "collection", "compat", "immutable", p)
    if p.startsWith("package") =>
    MergeStrategy.first
  case x =>
    val oldStrategy = (assembly / assemblyMergeStrategy).value
    oldStrategy(x)
}

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.13.2" cross CrossVersion.full)
addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")

//scalastyle
scalastyleFailOnError := true
scalastyleFailOnWarning := false
scalastyleConfigUrlCacheFile := "../scalastyle-config.xml"
//scapegoat
scapegoatVersion in ThisBuild := "1.4.12"
scapegoatConsoleOutput in ThisBuild := false
scapegoatRunAlways in ThisBuild := false
scapegoatDisabledInspections := Seq("VariableShadowing",
  "MaxParameters",
  "PartialFunctionInsteadOfMatch",
  "LooksLikeInterpolatedString",
  "CollectionNamingConfusion",
  "NullParameter")

//scoverage
ThisBuild / coverageMinimum := 2.50 //TODO изменить
ThisBuild / coverageFailOnMinimum := true

// Filter out compiler flags to make the repl experience functional...
val badConsoleFlags = Seq("-Xfatal-warnings", "-Ywarn-unused:imports")
Compile / console / scalacOptions ~= (_.filterNot(badConsoleFlags.contains(_)))


Compile / run / fork := true
//TODO envVars для тестов
