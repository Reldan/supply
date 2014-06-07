package com.supply.game.render

import java.nio.{ByteOrder, ByteBuffer}
import com.supply.game.chunk.Chunk
import javax.media.opengl.GL2
import javax.media.opengl.fixedfunc.GLLightingFunc._
import javax.media.opengl.fixedfunc.GLPointerFunc
import javax.media.opengl.GL._


object ChunkRenderer {
  val NORMALS = Array(
    Array(-1.0f, 0.0f, 0.0f),
    Array(1.0f, 0.0f, 0.0f),
    Array(0.0f, -1.0f, 0.0f),
    Array(0.0f, 1.0f, 0.0f),
    Array(0.0f, 0.0f, -1.0f),
    Array(0.0f, 0.0f, 1.0f))
}


class ChunkRenderer(chunk: Chunk, renderedTrianglesCount: Int) {
  val vertexesCount = renderedTrianglesCount * 4 * 2
  var vertexByteBuffer = ByteBuffer.allocateDirect(vertexesCount * 3)
  var indexByteBuffer = ByteBuffer.allocateDirect(renderedTrianglesCount * 3 * 4)
  var normalByteBuffer = ByteBuffer.allocateDirect(vertexesCount * 3)
  var colorByteBuffer = ByteBuffer.allocateDirect(vertexesCount * 4)
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
      SquareData(p6, p1, p4, p7, ChunkRenderer.NORMALS(0), color),
      SquareData(p2, p5, p8, p3, ChunkRenderer.NORMALS(1), color),
      SquareData(p6, p5, p2, p1, ChunkRenderer.NORMALS(2), color),
      SquareData(p4, p3, p8, p7, ChunkRenderer.NORMALS(3), color),
      SquareData(p5, p6, p7, p8, ChunkRenderer.NORMALS(4), color),
      SquareData(p1, p2, p3, p4, ChunkRenderer.NORMALS(5), color)
    )

    Range(0, squares.size).foreach {
      i ⇒ if (!boxes(i)) addSquare(squares(i))
    }
  }

  def render(gl: GL2, x: Int = 0, y: Int = 0, z: Int = 0) = {
    if (chunk.changed)
      chunk.prepareBoxes()
    if (finished && renderedTrianglesCount > 0) {
      gl.glLoadIdentity()
      gl.glTranslatef(x, y, z)
      val colorAm = Array(1, 1, 1, 0.5f)
      gl.glLightfv( GL_LIGHT0, GL_DIFFUSE,  colorAm, 0 )
      gl.glEnableClientState(GLPointerFunc.GL_VERTEX_ARRAY)
      gl.glVertexPointer(3, GL_FLOAT, 0, vertexByteBuffer.asFloatBuffer())
      gl.glEnableClientState(GLPointerFunc.GL_NORMAL_ARRAY)
      gl.glNormalPointer(GL_FLOAT, 0, normalByteBuffer.asFloatBuffer())
      gl.glEnableClientState(GLPointerFunc.GL_COLOR_ARRAY)
      gl.glColorPointer(4 , GL_FLOAT, 0, colorByteBuffer.asFloatBuffer())
      gl.glDrawElements(GL_TRIANGLES, 3 * renderedTrianglesCount, GL_UNSIGNED_INT, indexByteBuffer)
      gl.glDisableClientState(GLPointerFunc.GL_COLOR_ARRAY)
      gl.glDisableClientState(GLPointerFunc.GL_NORMAL_ARRAY)
      gl.glDisableClientState(GLPointerFunc.GL_VERTEX_ARRAY)
    }
  }

}
