package com.supply.game.chunk

import scala.util.Random
import javax.media.opengl.fixedfunc.GLPointerFunc
import javax.media.opengl.GL._
import javax.media.opengl.fixedfunc.GLLightingFunc._
import java.nio.{ByteOrder, ByteBuffer}

import javax.media.opengl._
import com.supply.game.render.ChunkRenderer

class Chunk(width: Int, height: Int, depth: Int) {
  val data = Array.fill(width, height, depth)(0.toByte)

  var boxType = new Array[Byte](height * width)
  val rnd = new Random()
  var created = false
  var renderer = new ChunkRenderer(1)

  var filledBoxesCount = 0
//  var colorByteBuffer = ByteBuffer.allocateDirect(filledBoxesCount * 6 * 8 * 3 * 4)
//  var colorBuffer = colorByteBuffer.asFloatBuffer()

  transform()

  private def setBuffers() = {
    renderer = new ChunkRenderer(filledBoxesCount)
//    colorByteBuffer = ByteBuffer.allocateDirect(filledBoxesCount * 6 * 8 * 3 * 4)
//    colorBuffer = colorByteBuffer.asFloatBuffer()
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
     for (x ← 0 until width;
          y ← 0 until height;
          z ← 0 until depth) {
       if (data(x)(y)(z) % 13 == 0) {
          val newX = (x % width - width / 2) * 2f
          val newY = (y % height - height / 2) * 2f
          val newZ = (z % depth - depth / 2f) * 2f
          prepare(newX, newY, newZ)
//         colorBuffer.put(1)
//         colorBuffer.put(0)
//         colorBuffer.put(0)
//         colorBuffer.put(0)
//         colorBuffer.put(1)
//         colorBuffer.put(0)
//         colorBuffer.put(1)
//         colorBuffer.put(1)
//         colorBuffer.put(1)
//         colorBuffer.put(0)
//         colorBuffer.put(0)
//         colorBuffer.put(0)
//         colorBuffer.put(1)
//         colorBuffer.put(0)
//         colorBuffer.put(1)
//         colorBuffer.put(1)
        }
    }
    created = true
  }

  def render(gl: GL2) = {
    gl.glLoadIdentity()
//    val colorAm = Array(1, 0, 0, 1.0f)
//    gl.glLightfv( GL_LIGHT0, GL_DIFFUSE,  colorAm, 0 )
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

  private def prepare(x: Float, y: Float, z: Float) {
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
    var v1 = renderer.addVertex(p1, n1)
    var v2 = renderer.addVertex(p2, n1)
    var v3 = renderer.addVertex(p3, n1)
    var v4 = renderer.addVertex(p4, n1)

    renderer.addTriangle(v1, v2, v3)
    renderer.addTriangle(v1, v3, v4)

    //    back
    val n2 = Array(0.0f, 0.0f, -1.0f)

    var v5 = renderer.addVertex(p5, n2)
    var v6 = renderer.addVertex(p6, n2)
    var v7 = renderer.addVertex(p7, n2)
    var v8 = renderer.addVertex(p8, n2)

    renderer.addTriangle(v5, v6, v7)
    renderer.addTriangle(v5, v7, v8)

    //right
    val n3 = Array(1.0f, 0.0f, 0.0f)

    v2 = renderer.addVertex(p2, n3)
    v5 = renderer.addVertex(p5, n3)
    v8 = renderer.addVertex(p8, n3)
    v3 = renderer.addVertex(p3, n3)

    renderer.addTriangle(v2, v5, v8)
    renderer.addTriangle(v2, v8, v3)

    //left
    val n4 = Array(-1.0f, 0.0f, 0.0f)

    v6 = renderer.addVertex(p6, n4)
    v1 = renderer.addVertex(p1, n4)
    v4 = renderer.addVertex(p4, n4)
    v7 = renderer.addVertex(p7, n4)

    renderer.addTriangle(v6, v1, v4)
    renderer.addTriangle(v6, v4, v7)

    //top
    val n5 = Array(-1.0f, 0.0f, 0.0f)

    v4 = renderer.addVertex(p4, n5)
    v3 = renderer.addVertex(p3, n5)
    v8 = renderer.addVertex(p8, n5)
    v7 = renderer.addVertex(p7, n5)

    renderer.addTriangle(v4, v3, v8)
    renderer.addTriangle(v4, v8, v7)

    //bottom
    val n6 = Array(0.0f, -1.0f, 0.0f)

    v6 = renderer.addVertex(p6, n6)
    v5 = renderer.addVertex(p5, n6)
    v2 = renderer.addVertex(p2, n6)
    v1 = renderer.addVertex(p1, n6)

    renderer.addTriangle(v6, v5, v2)
    renderer.addTriangle(v6, v2, v1)
  }

  def createCube(gl: GL2) {
//    gl.glDrawElements(GL_TRIANGLES, 1000, GL_UNSIGNED_SHORT, 0)
    gl.glLoadIdentity()
//    val colorAm = Array(1, 1, 1, 1.0f)
//    gl.glLightfv( GL_LIGHT0, GL_DIFFUSE,  colorAm, 0 )
    gl.glEnableClientState(GLPointerFunc.GL_VERTEX_ARRAY)
    gl.glVertexPointer(3, GL_FLOAT, 0, renderer.vertexByteBuffer.asFloatBuffer())
    gl.glEnableClientState(GLPointerFunc.GL_NORMAL_ARRAY)
    gl.glNormalPointer(GL_FLOAT, 0, renderer.normalByteBuffer.asFloatBuffer())
//    gl.glEnableClientState(GLPointerFunc.GL_COLOR_ARRAY)
//    gl.glColorPointer(4 , GL_FLOAT, 0, colorByteBuffer.asFloatBuffer())
    gl.glDrawElements(GL_TRIANGLES, 36 * filledBoxesCount, GL_UNSIGNED_INT, renderer.indexByteBuffer)
//    gl.glDisableClientState(GLPointerFunc.GL_COLOR_ARRAY)
    gl.glDisableClientState(GLPointerFunc.GL_NORMAL_ARRAY)
    gl.glDisableClientState(GLPointerFunc.GL_VERTEX_ARRAY)

  }
}
