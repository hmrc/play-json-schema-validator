val projectName = "play-json-schema-validator"

val testSettings = unmanagedJars in Test ++= Seq(
  baseDirectory.value / "src/test/resources/simple-schema.jar",
  baseDirectory.value / "src/test/resources/simple-schema-issue-65.jar",
  baseDirectory.value / "src/test/resources/issue-65.jar"
)

lazy val schemaProject = Project(projectName, file("."))
  .enablePlugins(SbtGitVersioning)
  .settings(testSettings)
  .settings(
    scalaVersion := "3.3.6",
    libraryDependencies ++= LibDependencies.core,
    description := "JSON Schema Validation with Play JSON",
    isPublicArtefact := true,
    majorVersion := 0,
    Keys.fork in Test := false,
    Keys.parallelExecution in Test := false,
    retrieveManaged := true,
    testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")
  )
