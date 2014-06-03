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
    val n1 = Array(0.0f, 0.0f, 1.0f)
    var v1 = addVertex(p1, n1, color)
    var v2 = addVertex(p2, n1, color)
    var v3 = addVertex(p3, n1, color)
    var v4 = addVertex(p4, n1, color)

    addTriangle(v1, v2, v3)
    addTriangle(v1, v3, v4)

    //    back
    val n2 = Array(0.0f, 0.0f, -1.0f)

    var v5 = addVertex(p5, n2, color)
    var v6 = addVertex(p6, n2, color)
    var v7 = addVertex(p7, n2, color)
    var v8 = addVertex(p8, n2, color)

    addTriangle(v5, v6, v7)
    addTriangle(v5, v7, v8)

    //right
    val n3 = Array(1.0f, 0.0f, 0.0f)

    v2 = addVertex(p2, n3, color)
    v5 = addVertex(p5, n3, color)
    v8 = addVertex(p8, n3, color)
    v3 = addVertex(p3, n3, color)

    addTriangle(v2, v5, v8)
    addTriangle(v2, v8, v3)

    //left
    val n4 = Array(-1.0f, 0.0f, 0.0f)

    v6 = addVertex(p6, n4, color)
    v4 = addVertex(p4, n4, color)
    v7 = addVertex(p7, n4, color)

    addTriangle(v6, v1, v4)
    addTriangle(v6, v4, v7)

    //top
    val n5 = Array(-1.0f, 0.0f, 0.0f)

    v4 = addVertex(p4, n5, color)
    v3 = addVertex(p3, n5, color)
    v8 = addVertex(p8, n5, color)
    v7 = addVertex(p7, n5, color)

    addTriangle(v4, v3, v8)
    addTriangle(v4, v8, v7)

    //bottom
    val n6 = Array(0.0f, -1.0f, 0.0f)

    v6 = addVertex(p6, n6, color)
    v5 = addVertex(p5, n6, color)
    v2 = addVertex(p2, n6, color)
    v1 = addVertex(p1, n6, color)

    addTriangle(v6, v5, v2)
    addTriangle(v6, v2, v1)
  }

}
