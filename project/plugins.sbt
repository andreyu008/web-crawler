logLevel := util.Level.Warn
//апдейт библиотек до свежих версий
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.6.1")
//сборка fat jar
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "1.1.0")
//форматирование
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.6")
//style линтер

addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "1.0.0")
//shit линтер
addSbtPlugin("com.sksamuel.scapegoat" %% "sbt-scapegoat" % "1.1.1")
//покрытие
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.9.3")
//scalac recommended options
addSbtPlugin("io.github.davidgregory084" % "sbt-tpolecat" % "0.1.20")
