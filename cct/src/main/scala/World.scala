import scala.util.Random

case class World(widthFields: Int, heightFields: Int) {
  val rnd = new Random()
  val fields = Array.fill(widthFields, heightFields)(MapField.random)
}
