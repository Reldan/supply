resolvers += MavenRepository("jogamp", "http://jogamp.org/deployment/maven")

libraryDependencies += "org.jogamp.jogl" % "jogl-all" % "2.1.0" classifier "natives-macosx-universal"

libraryDependencies += "org.jogamp.gluegen" % "gluegen-rt" % "2.1.0" classifier "natives-macosx-universal"

//libraryDependencies ++= {
//  val os = sys.props("os.name") match {
//    case "Linux" => "linux"
//    case "Mac OS X" => "macosx"
//    case os if os.startsWith("Windows") => "windows"
//    case os => sys.error("Cannot obtain lib for OS: " + os)
//  }
//  val arch = if (os == "macosx") "universal" else sys.props("os.arch") match {
//    case "amd64" => "amd64"
//    case "i386" => "i586"
//    case "x86" => "i586"
//    case arch => sys.error("Cannot obtain lib for arch: " + arch)
//  }
//  val vers = "2.1.0"
//  val suff = "-natives-" + os + "-" + arch
//  val jogampurl = "http://jogamp.org/deployment/maven/org/jogamp/"
//  val joglurl = jogampurl + "jogl/jogl-all/" + vers + "/" + "jogl-all-" + vers + suff + ".jar"
//  val jogl = "org.jogamp.jogl" % ("jogl-all" + suff) % (vers) from joglurl
//  val glueurl = jogampurl + "gluegen/gluegen-rt/" + vers + "/" + "gluegen-rt-" + vers + suff + ".jar"
//  val glue = "org.jogamp.gluegen" % ("gluegen-rt" + suff) % (vers) from glueurl
//  Seq(jogl, glue)
//}
