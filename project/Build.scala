import sbt.Keys._
import sbt._


object DaggerFrogBuild extends Build {
  lazy val root     = Project("root", file(".")) aggregate(supply)
  lazy val supply   = Project("supply", file("supply"))
}
