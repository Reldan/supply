package com.supply.game

import scala.util.Random
import javax.media.opengl.fixedfunc.GLPointerFunc
import javax.media.opengl.GL._
import javax.media.opengl.fixedfunc.GLLightingFunc._
import java.nio.{ByteOrder, ByteBuffer}

import javax.media.opengl._

class Chunk(width: Int, height: Int, depth: Int) {
  val data = Array.fill(width, height, depth)(0.toByte)

  var boxType = new Array[Byte](height * width)
  val rnd = new Random()
  var created = false

  var filledBoxesCount = 0
  var vertexByteBuffer = ByteBuffer.allocateDirect(filledBoxesCount * 6 * 8 * 3 * 4)
  var indexByteBuffer = ByteBuffer.allocateDirect(filledBoxesCount * 12 * 3 * 4)
  var normalByteBuffer = ByteBuffer.allocateDirect(filledBoxesCount * 12 * 3 * 4)
  var vertexBuffer = vertexByteBuffer.asFloatBuffer()
  var indexBuffer = indexByteBuffer.asIntBuffer()
  var normalBuffer = normalByteBuffer.asFloatBuffer()
  transform()

  private def setBuffers() = {
    vertexByteBuffer = ByteBuffer.allocateDirect(filledBoxesCount * 6 * 8 * 3 * 4)
    indexByteBuffer = ByteBuffer.allocateDirect(filledBoxesCount * 12 * 3 * 4)
    normalByteBuffer = ByteBuffer.allocateDirect(filledBoxesCount * 12 * 3 * 4)
    vertexByteBuffer.order(ByteOrder.nativeOrder())
    indexByteBuffer.order(ByteOrder.nativeOrder())
    normalByteBuffer.order(ByteOrder.nativeOrder())
    vertexBuffer = vertexByteBuffer.asFloatBuffer()
    indexBuffer = indexByteBuffer.asIntBuffer()
    normalBuffer = normalByteBuffer.asFloatBuffer()
  }


  def transform() = {
    created = false
    filledBoxesCount = 0
    for (x ← 0 until width;
         y ← 0 until height;
         z ← 0 until depth) {
      data(x)(y)(z) = rnd.nextInt(100).toByte
      if (data(x)(y)(z) % 13 == 0) {
        filledBoxesCount += 1
      }
    }
    setBuffers()
    prepareBoxes()
  }

  def prepareBoxes() {
    var i = 0
     for (x ← 0 until width;
          y ← 0 until height;
          z ← 0 until depth) {
       if (data(x)(y)(z) % 13 == 0) {
          val newX = (x % width - width / 2) * 2f
          val newY = (y % height - height / 2) * 2f
          val newZ = (z % depth - depth / 2f) * 2f
          prepare(newX, newY, newZ, i)
          i += 1
        }
    }
    created = true
  }

  def render(gl: GL2) = {
    gl.glLoadIdentity()
    val colorAm = Array(1, 0, 0, 1.0f)
    gl.glLightfv( GL_LIGHT0, GL_DIFFUSE,  colorAm, 0 )
    gl.glTranslatef(0, 0, 0)
    if (created)
      createCube(gl)
  }

  def vertexArray(x: Float, y: Float, z: Float, blockSize: Float = 1f) = {
    Array(x - blockSize, y - blockSize, z + blockSize,
    x + blockSize, y - blockSize, z + blockSize,
    x + blockSize, y + blockSize, z + blockSize,
    x - blockSize, y + blockSize, z + blockSize,
    x + blockSize, y - blockSize, z - blockSize,
    x - blockSize, y - blockSize, z - blockSize,
    x - blockSize, y + blockSize, z - blockSize,
    x + blockSize, y + blockSize, z - blockSize)
  }

//  def

  private def prepare(x: Float, y: Float, z: Float, cubeNumber: Int) {
    val blockSize = 1f

    val vertexes = vertexArray(x, y, z, blockSize)

    vertexBuffer.put(vertexes)

//    val vertexOffset = 12// cubeNumber * vertexes.size
    val vertexOffset = cubeNumber * vertexes.size

    println(cubeNumber)
    def putIndex(data: Array[Int]) = {
      indexBuffer.put(Array(data(0) + vertexOffset, data(1) + vertexOffset, data(2) + vertexOffset))
    }

    //front
    val n1 = Array(0.0f, 0.0f, 1.0f)
    normalBuffer.put(n1)
    normalBuffer.put(n1)

    putIndex(Array(0, 1, 2))
    putIndex(Array(0, 2, 3))

    //    back
    val n2 = Array(0.0f, 0.0f, -1.0f)
    putIndex(Array(4, 5, 6))
    putIndex(Array(4, 6, 7))
    normalBuffer.put(n2)
    normalBuffer.put(n2)
    //right
    val n3 = Array(1.0f, 0.0f, 0.0f)
    putIndex(Array(1, 4, 7))
    putIndex(Array(1, 7, 2))

    normalBuffer.put(n3)
    normalBuffer.put(n3)
    //left
    val n4 = Array(-1.0f, 0.0f, 0.0f)
    putIndex(Array(5, 0, 3))
    putIndex(Array(5, 3, 6))

    normalBuffer.put(n4)
    normalBuffer.put(n4)

    //top
    val n5 = Array(-1.0f, 0.0f, 0.0f)
    putIndex(Array(3, 2, 7))
    putIndex(Array(3, 7, 6))
    normalBuffer.put(n5)
    normalBuffer.put(n5)

//    //bottom
    val n6 = Array(0.0f, -1.0f, 0.0f)
    putIndex(Array(5, 4, 1))
    putIndex(Array(5, 1, 0))

    normalBuffer.put(n6)
    normalBuffer.put(n6)
  }

  def createCube(gl: GL2) {
//    gl.glDrawElements(GL_TRIANGLES, 1000, GL_UNSIGNED_SHORT, 0)
    gl.glLoadIdentity()
    val colorAm = Array(1, 0, 0, 1.0f)
    gl.glLightfv( GL_LIGHT0, GL_DIFFUSE,  colorAm, 0 )
    gl.glEnableClientState(GLPointerFunc.GL_VERTEX_ARRAY)
    gl.glVertexPointer(3, GL_FLOAT, 0, vertexByteBuffer.asFloatBuffer())
    gl.glEnableClientState(GLPointerFunc.GL_NORMAL_ARRAY)
    gl.glNormalPointer(GL_FLOAT, 0, normalByteBuffer.asFloatBuffer())
    gl.glDrawElements(GL_TRIANGLES, 36 * filledBoxesCount, GL_UNSIGNED_INT, indexByteBuffer)
    gl.glDisableClientState(GLPointerFunc.GL_NORMAL_ARRAY)
    gl.glDisableClientState(GLPointerFunc.GL_VERTEX_ARRAY)

  }
}
