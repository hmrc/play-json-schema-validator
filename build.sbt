import xerial.sbt.Sonatype.sonatypeCentralHost

val projectName = "play-json-schema-validator"
val ghProject = "arturopala/" + projectName
val ghUrl = url("https://github.com/" + ghProject)

ThisBuild / sonatypeCredentialHost := sonatypeCentralHost

val commonSettings = Seq(
  organization := "io.github.arturopala",
  licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
  Keys.fork in Test := false,
  Keys.parallelExecution in Test := false,
  version := "1.1.0",
  organization := "io.github.arturopala",
  name := projectName,
  scalaVersion := "3.3.4",
  useGpg := true,
  description := "JSON Schema Validation with Play JSON",
  organizationName := "",
  versionScheme := Some("early-semver"),
  homepage := Some(ghUrl),
  startYear := Some(2016),
  scmInfo := Some(
    ScmInfo(ghUrl, "git@github.com:" + ghProject + ".git")
  ),
  developers := List(
    Developer("edgarmueller", "Edgar Müller", "@edgarmueller", url("https://github.com/edgarmueller"))
  )
)

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

val buildSettings = Defaults.coreDefaultSettings ++ commonSettings

val testSettings = unmanagedJars in Test ++= Seq(
  baseDirectory.value / "src/test/resources/simple-schema.jar",
  baseDirectory.value / "src/test/resources/simple-schema-issue-65.jar",
  baseDirectory.value / "src/test/resources/issue-65.jar"
)

lazy val schemaProject = Project(projectName, file("."))
  .settings(buildSettings)
  .settings(testSettings)
  .settings(
    retrieveManaged := true,
    libraryDependencies ++= Dependencies.core,
    testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")
  )

publishTo := sonatypePublishToBundle.value

publishMavenStyle := true
publishArtifact in Test := false
pomIncludeRepository := { _ => false }
