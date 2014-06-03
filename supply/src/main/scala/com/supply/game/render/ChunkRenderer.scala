package com.supply.game.render

import java.nio.{ByteOrder, ByteBuffer}



// size of triangles = renderedBoxesCount * 2 (2 triangles per square) * 6 (squares) * (3 point) * (4 size of float)
//
class ChunkRenderer(renderedBoxesCount: Int) {
  val vertexesCount = renderedBoxesCount * 6 * 8 * 3 * 4
  var vertexByteBuffer = ByteBuffer.allocateDirect(vertexesCount)
  var indexByteBuffer = ByteBuffer.allocateDirect(renderedBoxesCount * 12 * 3 * 4)
  var normalByteBuffer = ByteBuffer.allocateDirect(vertexesCount)
  var colorByteBuffer = ByteBuffer.allocateDirect(vertexesCount)
  vertexByteBuffer.order(ByteOrder.nativeOrder())
  indexByteBuffer.order(ByteOrder.nativeOrder())
  normalByteBuffer.order(ByteOrder.nativeOrder())
  colorByteBuffer.order(ByteOrder.nativeOrder())
  var vertexBuffer = vertexByteBuffer.asFloatBuffer()
  var indexBuffer = indexByteBuffer.asIntBuffer()
  var normalBuffer = normalByteBuffer.asFloatBuffer()
  var colorBuffer = colorByteBuffer.asFloatBuffer()
  var finished = false

  def addVertex(point: Array[Float], normal: Array[Float], color: Array[Float] = Array(1.0f, 0, 0, 1)) = {
    require(point.size == 3)
    require(normal.size == 3)
    require(color.size == 4)
    require(!finished)
    val position = vertexBuffer.position()
    vertexBuffer.put(point)
    normalBuffer.put(normal)
    colorBuffer.put(color)
    position / 3
  }

  def addTriangle(p1: Int, p2: Int, p3: Int) = {
    indexBuffer.put(Array(p1, p2, p3))
  }

  def finish() {
    finished = true
  }


  def addSquare(sq: SquareData) {
    val v1 = addVertex(sq.p1, sq.n, sq.color)
    val v2 = addVertex(sq.p2, sq.n, sq.color)
    val v3 = addVertex(sq.p3, sq.n, sq.color)
    val v4 = addVertex(sq.p4, sq.n, sq.color)

    addTriangle(v1, v2, v3)
    addTriangle(v1, v3, v4)
  }

  case class SquareData(
    p1: Array[Float],
    p2: Array[Float],
    p3: Array[Float],
    p4: Array[Float],
    n: Array[Float],
    color: Array[Float])

  def addBox(x: Float, y: Float, z: Float, color: Array[Float], boxes: IndexedSeq[Boolean]) = {
    val blockSize = 1f

    val p1 = Array(x - blockSize, y - blockSize, z + blockSize)
    val p2 = Array(x + blockSize, y - blockSize, z + blockSize)
    val p3 = Array(x + blockSize, y + blockSize, z + blockSize)
    val p4 = Array(x - blockSize, y + blockSize, z + blockSize)
    val p5 = Array(x + blockSize, y - blockSize, z - blockSize)
    val p6 = Array(x - blockSize, y - blockSize, z - blockSize)
    val p7 = Array(x - blockSize, y + blockSize, z - blockSize)
    val p8 = Array(x + blockSize, y + blockSize, z - blockSize)

    //front
    val squares = Array(
      SquareData(p6, p1, p4, p7, Array(-1.0f, 0.0f, 0.0f), color),
      SquareData(p2, p5, p8, p3, Array(1.0f, 0.0f, 0.0f), color),
      SquareData(p6, p5, p2, p1, Array(0.0f, -1.0f, 0.0f), color),
      SquareData(p4, p3, p8, p7, Array(0.0f, 1.0f, 0.0f), color),
      SquareData(p5, p6, p7, p8, Array(0.0f, 0.0f, -1.0f), color),
      SquareData(p1, p2, p3, p4, Array(0.0f, 0.0f, 1.0f), color)
    )

    Range(0, squares.size).foreach {
      i ⇒ if (!boxes(i)) addSquare(squares(i))
    }

  }

}
