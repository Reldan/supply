package com.supply.game

import scala.util.Random
import javax.media.opengl.fixedfunc.GLPointerFunc
import javax.media.opengl.GL._
import javax.media.opengl.fixedfunc.GLLightingFunc._
import java.nio.{ByteOrder, ByteBuffer}

import javax.media.opengl._

/**
 * Created by reldan on 29/05/14.
 */
class Chunk(width: Int, height: Int, depth: Int) {
  val data = Array.fill(width, height, depth)(0.toByte)

  var boxType = new Array[Byte](height * width)
  val rnd = new Random()
  transform()

  var indexBufferId = Array(0)
  var vertexBufferId = Array(0)
  var vertexByteBuffer = ByteBuffer.allocateDirect(6 * 8 * 3 * 4)
  var indexByteBuffer = ByteBuffer.allocateDirect(12 * 3 * 4)
  var normalByteBuffer = ByteBuffer.allocateDirect(12 * 3 * 4)
  var filledBoxes = 0


  def transform() = {
    for (x ← 0 until width;
         y ← 0 until height;
         z ← 0 until depth) {
      data(x)(y)(z) = rnd.nextInt(100).toByte
//      if if (data(x)(y)(z) % 13 == 0) {
    }
  }

  def render(gl: GL2) = {

    gl.glLoadIdentity()
    val colorAm = Array(1, 0, 0, 1.0f)
    gl.glLightfv( GL_LIGHT0, GL_DIFFUSE,  colorAm, 0 )
    gl.glTranslatef(0, 0, 0)


//    gl.glVertexPointer(2, GL2.GL_INT, 0, verticesBuf)
    for (x ← 0 until width;
         y ← 0 until height;
         z ← 0 until depth) {
      if (data(x)(y)(z) % 13 == 0) {
        val newX = (x % width - width / 2) * 2f
        val newY = (y % height - height / 2) * 2f
        val newZ = (z % depth - depth / 2f) * 2f
//        drawCube(gl, newX, newY, newZ, data(x)(y)(z))
        prepare(gl, newX, newY, newZ)
        createCube(gl, newX, newY, newZ)
      }
    }
  }

  def vertexArray(x: Float, y: Float, z: Float, blockSize: Float = 1f) = {
    Array(x-blockSize, y-blockSize, z+blockSize,
    x+blockSize, y-blockSize, z+blockSize,
    x+blockSize, y+blockSize, z+blockSize,
    x-blockSize, y+blockSize, z+blockSize,
    x+blockSize, y-blockSize, z-blockSize,
    x-blockSize, y-blockSize, z-blockSize,
    x-blockSize, y+blockSize, z-blockSize,
    x+blockSize, y+blockSize, z-blockSize)
  }


  def prepare(gl: GL2, x: Float, y: Float, z: Float) {
    vertexByteBuffer = ByteBuffer.allocateDirect(8 * 3 * 4)
    indexByteBuffer = ByteBuffer.allocateDirect(12 * 3 * 4)
    val blockSize = 1f

    vertexByteBuffer.order(ByteOrder.nativeOrder());  // <-- this line was missing

    val vertexBuffer = vertexByteBuffer.asFloatBuffer()

    indexByteBuffer.order(ByteOrder.nativeOrder());  // <- this line was missing
    val indexBuffer = indexByteBuffer.asIntBuffer()

    normalByteBuffer.order(ByteOrder.nativeOrder());  // <- this line was missing
    val normalBuffer = normalByteBuffer.asFloatBuffer()


    vertexBuffer.put(vertexArray(x, y, z, blockSize))

    //front
    val n1 = Array(0.0f, 0.0f, 1.0f)
    normalBuffer.put(n1)
    normalBuffer.put(n1)

    indexBuffer.put(Array(0, 1, 2))
    indexBuffer.put(Array(0, 2, 3))

    //    back
    val n2 = Array(0.0f, 0.0f, -1.0f)
    indexBuffer.put(Array(4, 5, 6))
    indexBuffer.put(Array(4, 6, 7))
    normalBuffer.put(n2)
    normalBuffer.put(n2)
    //right
    val n3 = Array(1.0f, 0.0f, 0.0f)
    indexBuffer.put(Array(1, 4, 7))
    indexBuffer.put(Array(1, 7, 2))

    normalBuffer.put(n3)
    normalBuffer.put(n3)
    //left
    val n4 = Array(-1.0f, 0.0f, 0.0f)
    indexBuffer.put(Array(5, 0, 3))
    indexBuffer.put(Array(5, 3, 6))

    normalBuffer.put(n4)
    normalBuffer.put(n4)

    //top
    val n5 = Array(-1.0f, 0.0f, 0.0f)
    indexBuffer.put(Array(3, 2, 7))
    indexBuffer.put(Array(3, 7, 6))
    normalBuffer.put(n5)
    normalBuffer.put(n5)

//    //bottom
    val n6 = Array(0.0f, -1.0f, 0.0f)
    indexBuffer.put(Array(5, 4, 1))
    indexBuffer.put(Array(5, 1, 0))

    normalBuffer.put(n6)
    normalBuffer.put(n6)
  }

  def createCube(gl: GL2, x: Float, y: Float, z: Float) {
//    gl.glDrawElements(GL_TRIANGLES, 1000, GL_UNSIGNED_SHORT, 0)
    gl.glLoadIdentity()
    val colorAm = Array(1, 0, 0, 1.0f)
    gl.glLightfv( GL_LIGHT0, GL_DIFFUSE,  colorAm, 0 )
    gl.glEnableClientState(GLPointerFunc.GL_VERTEX_ARRAY)
    gl.glVertexPointer(3, GL_FLOAT, 0, vertexByteBuffer.asFloatBuffer())
    gl.glEnableClientState(GLPointerFunc.GL_NORMAL_ARRAY)
    gl.glNormalPointer(GL_FLOAT, 0, normalByteBuffer.asFloatBuffer())
    gl.glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_INT, indexByteBuffer)
    gl.glDisableClientState(GLPointerFunc.GL_NORMAL_ARRAY)
    gl.glDisableClientState(GLPointerFunc.GL_VERTEX_ARRAY)

  }
}
