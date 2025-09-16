import sbt._

object Version {
  final val playJson = "3.0.5"
  final val playSpecs = "3.0.6"
  final val scalaz = "7.2.36"
  final val specs2 = "5.5.8"
  final val guava = "19.0"
  final val galimatias = "0.2.1"
  final val nashorn = "15.4"
}

object Library {
  final val guava = "com.google.guava" % "guava" % Version.guava
  final val scalaz = "org.scalaz" %% "scalaz-core" % Version.scalaz
  final val playJson = "org.playframework" %% "play-json" % Version.playJson
  final val playTest = "org.playframework" %% "play-specs2" % Version.playSpecs % "test"
  final val playServer = "org.playframework" %% "play-server" % Version.playSpecs % "test"
  final val playPekko = "org.playframework" %% "play-pekko-http-server" % Version.playSpecs % "test"
  final val specs2 = "org.specs2" %% "specs2-core" % Version.specs2 % "test"
  final val galimatias = "io.mola.galimatias" % "galimatias" % Version.galimatias
  final val nashorn = "org.openjdk.nashorn" % "nashorn-core" % Version.nashorn
}

object LibDependencies {
  import Library._

  val core = List(
    galimatias,
    guava,
    playJson,
    playTest,
    playServer,
    playPekko,
    scalaz,
    specs2,
    nashorn
  )
}
