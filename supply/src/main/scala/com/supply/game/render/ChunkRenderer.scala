package com.supply.game.render

import java.nio.{ByteOrder, ByteBuffer}

/**
 * Created by reldan on 01/06/14.
 */
class ChunkRenderer(filledBoxesCount: Int) {
  val vertexesCount = filledBoxesCount * 6 * 8 * 3 * 4
  var vertexByteBuffer = ByteBuffer.allocateDirect(vertexesCount)
  var indexByteBuffer = ByteBuffer.allocateDirect(filledBoxesCount * 12 * 3 * 4)
  var normalByteBuffer = ByteBuffer.allocateDirect(vertexesCount)
//  var colorByteBuffer = ByteBuffer.allocateDirect(filledBoxesCount * 6 * 8 * 3 * 4)
  vertexByteBuffer.order(ByteOrder.nativeOrder())
  indexByteBuffer.order(ByteOrder.nativeOrder())
  normalByteBuffer.order(ByteOrder.nativeOrder())
  var vertexBuffer = vertexByteBuffer.asFloatBuffer()
  var indexBuffer = indexByteBuffer.asIntBuffer()
  var normalBuffer = normalByteBuffer.asFloatBuffer()
  var finished = false

  def addVertex(point: Array[Float], normal: Array[Float]) = {
    require(point.size == 3)
    require(normal.size == 3)
    require(!finished)
    val position = vertexBuffer.position()
    println(position)

    vertexBuffer.put(point)
    normalBuffer.put(normal)
    position / 3
  }

  def addTriangle(p1: Int, p2: Int, p3: Int) = {
    indexBuffer.put(Array(p1, p2, p3))
  }

  def finish() {
    finished = true
  }

}
