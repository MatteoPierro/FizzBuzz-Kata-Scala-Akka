import sbt.Keys._

name := "fizzBuzz"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.scala-lang.modules" %% "scala-async" % "0.9.5"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.4"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.0" % "test"
libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.3.4" % "test"
libraryDependencies += "org.mockito" % "mockito-core" % "1.9.5" % "test"