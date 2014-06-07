package com.supply.game.chunk

import scala.util.Random
import com.supply.game.render.ChunkRenderer

object BoxType {
  val Empty: Byte = 0
  val Water: Byte = 1
  val Sand:  Byte = 2
  val Dirt:  Byte = 3
  val Grass: Byte = 4
  val Stone: Byte = 5
  val Wood:  Byte = 6

  private val rnd = new Random()

  private val boxColor: Map[Byte, Array[Float]] = Map(
    Grass → Array(19, 136, 8, 255),
    Water → Array(0, 127, 255, 100),
    Stone → Array(132, 132, 130, 255),
    Wood  → Array(205, 127, 50, 255),
    Sand  → Array(236, 213, 64, 255),
    Dirt  → Array(101, 67, 33, 255)
  ).map(el ⇒ el._1 → el._2.map(_.toFloat / 255))

  def getColor(i: Byte) = {
    require(i > 0)
    boxColor(i)
  }

  def isValidType(i: Byte) = {
    i >= 0 && i <= Wood
  }

  def isTransparent(i: Byte) = {
//    i == Water ||
      i == Empty
  }

  def getRandomBoxType() = {
    rnd.nextInt(Wood + 1).toByte
  }

  def getRandomNonEmptyType(): Byte = {
    rnd.nextInt(Wood) + 1
  }.toByte
}

object Chunk {
  def create(width: Int, height: Int, depth: Int) =
    new Chunk(Array.fill(width, height, depth)(0.toByte))

  def nearBoxes(x: Int, y: Int, z: Int, data: Array[Array[Array[Byte]]]) = {
    val width = data.size
    val height = data(0).size
    val depth = data(0)(0).size
    require(x < width)
    require(y < height)
    require(z < depth)
    require(data(x)(y)(z) != BoxType.Empty)

    Array((x - 1, y, z), (x + 1, y, z), (x, y - 1, z), (x, y + 1, z), (x, y, z - 1), (x, y, z + 1)).map {
      case(nx, ny, nz) if nx >= 0 && nx < width && ny >= 0 && ny < height && nz >= 0 && nz < depth ⇒
        !BoxType.isTransparent(data(nx)(ny)(nz))
      case (nx, ny, nz) ⇒ false
    }
  }
}


class Chunk(data:Array[Array[Array[Byte]]]) {
  val width = data.length
  val height = data(0).length
  val depth = data(0)(0).length
  var changed = true
  var renderer = new ChunkRenderer(this, 1)

  var renderedTrianglesCount = 0

  def countTriangles(x: Int, y: Int, z: Int) = {
    if (!BoxType.isTransparent(data(x)(y)(z)))
      Chunk.nearBoxes(x, y, z, data).count(el ⇒ !el) * 2
    else
      0
  }


  private def renderBox(x: Int, y: Int, z: Int) = {
    data(x)(y)(z) != 0 && Chunk.nearBoxes(x, y, z, data).exists(el ⇒ !el)
  }

  def renderedTrianglesCalculate = {
    var i = 0
    for (x ← 0 until width;
         y ← 0 until height;
         z ← 0 until depth) {
         i += countTriangles(x, y, z)
       }
    i
  }

  /**
   * Place box to chunk
   * @param x x coord
   * @param y y coord
   * @param z z coord
   * @param boxType type of chunk from ChunkType
   */
  def addBox(x: Int, y: Int, z: Int, boxType: Byte) {
    require(BoxType.isValidType(boxType))
    data(x)(y)(z) = boxType
  }

  def prepareBoxes() {
    renderedTrianglesCount = renderedTrianglesCalculate
    renderer = new ChunkRenderer(this, renderedTrianglesCount)
    for (x ← 0 until width;
         y ← 0 until height;
         z ← 0 until depth) {
      if (renderBox(x, y, z)) {
        val newX = (x % width - width / 2) * 2f
        val newY = (y % height - height / 2) * 2f
        val newZ = (z % depth - depth / 2f) * 2f
        renderer.addBox(newX, newY, newZ, BoxType.getColor(data(x)(y)(z)), Chunk.nearBoxes(x, y, z, data))
      }
    }
    renderer.finish()
    changed = false
  }
}
