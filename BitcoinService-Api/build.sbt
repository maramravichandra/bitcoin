val ScalatraVersion = "2.6.3"

organization := "infosys"

name := "MyScalatraWebApp"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.8"

resolvers += Classpaths.typesafeReleases

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % ScalatraVersion,
  "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
  "ch.qos.logback" % "logback-classic" % "1.2.3" % "runtime",
  "org.eclipse.jetty" % "jetty-webapp" % "9.4.9.v20180320" % "container",
  "org.scalatra" %% "scalatra-json" % "2.3.2",
  "org.json4s"   %% "json4s-jackson" % "3.2.11",
  "org.json" % "json" % "20090211",
  "org.scala-lang" % "scala-library" % "2.11.8",
  "com.google.code.gson" % "gson" % "2.8.5",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided"
)

enablePlugins(SbtTwirl)
enablePlugins(ScalatraPlugin)
