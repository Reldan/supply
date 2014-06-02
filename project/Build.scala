import sbt.Keys._
import sbt._


object DaggerFrogBuild extends Build {
  lazy val root     = Project("root", file(".")) aggregate(supply, noise)
  lazy val supply   = Project("supply", file("supply")) dependsOn (noise)
  lazy val noise    = RootProject(uri("git://github.com/Reldan/Joise.git"))
}
