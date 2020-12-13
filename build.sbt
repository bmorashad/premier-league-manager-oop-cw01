name := """premier-league"""
organization := "com.rashad"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.3"

libraryDependencies += guice

unmanagedSources / excludeFilter := "*Test*"
