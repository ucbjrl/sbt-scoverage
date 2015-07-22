import com.typesafe.sbt.SbtPgp._
import sbt.ScriptedPlugin._
import sbt._
import sbt.Keys._
import bintray.Keys._
import bintray._
import sbtrelease.ReleasePlugin._

object Build extends Build {

  lazy val commonSettings = Seq(
    version in ThisBuild := "1.3.0",
    organization in ThisBuild := "org.scoverage"
  )

  lazy val root = (project in file("."))
    .settings(commonSettings: _*)
    .settings(ScriptedPlugin.scriptedSettings: _*)
    .settings(releaseSettings: _*)
    .settings(bintrayPublishSettings: _*)
    .settings(
      name := "sbt-scoverage",
      description := "sbt plugin for scoverage code coverage",
      sbtPlugin := true,
      libraryDependencies += "org.scoverage" %% "scalac-scoverage-plugin" % "1.1.1",
      scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-encoding", "utf8"),
      licenses +=("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
      scriptedLaunchOpts ++= Seq(
        "-Xmx1024M", "-XX:MaxPermSize=256M",
        "-Dplugin.version=" + version.value
      ),
      ReleaseKeys.publishArtifactsAction := PgpKeys.publishSigned.value,
      publishMavenStyle := false,
      publishArtifact in Test := false,
      repository in bintray := "sbt-plugins",
      bintrayOrganization in bintray := None
    )
}