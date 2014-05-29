resolvers += MavenRepository("jogamp", "http://jogamp.org/deployment/maven")

val os = sys.props("os.name") match {
  case "Linux" => "linux"
  case "Mac OS X" => "macosx"
  case os if os.startsWith("Windows") => "windows"
  case os => sys.error("Cannot obtain lib for OS: " + os)
}
val arch = if (os == "macosx") "universal" else sys.props("os.arch") match {
  case "amd64" => "amd64"
  case "i386" => "i586"
  case "x86" => "i586"
  case arch => sys.error("Cannot obtain lib for arch: " + arch)
}
val vers = "2.1.0"
val sp = "natives-" + os + "-" + arch

libraryDependencies ++= Seq("org.jogamp.jogl" % "jogl-all" % vers classifier sp,
"org.jogamp.jogl" % "jogl-all" % vers,
"org.jogamp.gluegen" % "gluegen-rt" % vers classifier sp,
"org.jogamp.gluegen" % "gluegen-rt" % vers)
