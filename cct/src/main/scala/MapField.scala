import com.badlogic.gdx.graphics.Color

import scala.util.Random

object FieldType {
  val Water: Byte = 1
  val Sand:  Byte = 2
  val Dirt:  Byte = 3
  val Grass: Byte = 4
  val Stone: Byte = 5
  val Wood:  Byte = 6

  private val boxColor: Map[Byte, Color] = Map(
    Grass → Array(19, 136, 8, 255),
    Water → Array(0, 127, 255, 100),
    Stone → Array(132, 132, 130, 255),
    Wood  → Array(205, 127, 50, 255),
    Sand  → Array(236, 213, 64, 255),
    Dirt  → Array(101, 67, 33, 255)
  ).map(el ⇒ el._1 → el._2.map(_.toFloat / 255))
  .map{case (b, el) => b -> new Color(el(0), el(1), el(2), el(3))}

  def getColor(i: Byte) = {
    require(i > 0)
    boxColor(i)
  }
}

case object MapField {
  private val rnd = new Random()
  def random = MapField((rnd.nextInt(6) + 1).toByte)
}

case class MapField(fieldType: Byte) {
  def getColor = FieldType.getColor(fieldType)
}
