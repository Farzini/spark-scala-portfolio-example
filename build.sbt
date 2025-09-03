ThisBuild / scalaVersion := "2.12.18"
ThisBuild / version := "0.1.0"
ThisBuild / organization := "com.example"

lazy val sparkVersion = "3.5.1"

lazy val root = (project in file("."))
  .settings(
    name := "spark-scala-portfolio-example",
    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
      "org.apache.spark" %% "spark-sql"  % sparkVersion % "provided",
      "org.scalatest"    %% "scalatest"  % "3.2.19" % Test
    ),
    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", xs @ _*) => MergeStrategy.discard
      case x => MergeStrategy.first
    }
  )

// Enable packaging a fat JAR (for cluster submission)
import sbtassembly.AssemblyPlugin.autoImport._

libraryDependencies += "com.github.scopt" %% "scopt" % "4.1.0"
