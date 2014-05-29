package com.supply.game

import scala.util.Random
import javax.media.opengl.fixedfunc.GLPointerFunc
import javax.media.opengl.GL2
import javax.media.opengl.GL._
import javax.media.opengl.GL2ES1._
import javax.media.opengl.fixedfunc.GLLightingFunc._
import javax.media.opengl.fixedfunc.GLPointerFuncUtil._
import javax.media.opengl.fixedfunc.GLMatrixFunc._
import javax.media.opengl.GL2GL3._

/**
 * Created by reldan on 29/05/14.
 */
class Chunk(width: Int, height: Int, depth: Int) {
  val data = Array.fill(width, height, depth)(0.toByte)

  var boxType = new Array[Byte](height * width)
//  val indicesBuf = Buffers.newDirectFloatBuffer(width * height * depth * 12 * 3 * 3)
//  val normalBuf = Buffers.newDirectFloatBuffer(width * height * depth * 12 * 3)
  val rnd = new Random()
  transform()

  def transform() = {
    for (x ← 0 until width;
         y ← 0 until height;
         z ← 0 until depth) {
      data(x)(y)(z) = rnd.nextInt(100).toByte
    }
  }

  def render(gl: GL2) = {
    gl.glEnableClientState(GLPointerFunc.GL_VERTEX_ARRAY)
    gl.glEnableClientState(GLPointerFunc.GL_COLOR_ARRAY)
//
//    gl.glBegin (GL_LINES);
//    gl.glVertex3f(0f, 0f, 0f);
//    gl.glVertex3f(0f, 0f, 1000f);
//    gl.glVertex3f(0f, 0f, 0f);
//    gl.glVertex3f(0f, 1000f, 0f);
//    gl.glVertex3f(0f, 0f, 0f);
//    gl.glVertex3f(1000f, 0f, 0f);
//    gl.glEnd();

//    gl.glVertexPointer(2, GL2.GL_INT, 0, verticesBuf)
    for (x ← 0 until width;
         y ← 0 until height;
         z ← 0 until depth) {
      if (data(x)(y)(z) % 13 == 0) {
        val newX = (x % width - width / 2) * 2f
        val newY = (y % height - height / 2) * 2f
        val newZ = z * 2f - 100
        drawCube(gl, newX, newY, newZ, data(x)(y)(z))
//        createCube(gl, newX, newY, newZ)
      }
    }
//    gl.glVertexPointer(3, GL_FLOAT, 20, indicesBuf)
//    gl.glDrawElements(GL2.GL_POLYGON, 3, GL_FLOAT, indicesBuf)
  }

  def drawCube(gl: GL2, x: Float, y: Float, z: Float, color: Byte = 0) = {
    gl.glLoadIdentity()
    val colorAm = Array(color % 2, color % 3 % 2, 1.0f, 1.0f)
    gl.glLightfv( GL_LIGHT0, GL_DIFFUSE,  colorAm, 0 )

    gl.glRotatef(20, 0, 0, 0)

    gl.glTranslatef(x, y, z)
    val l_length = 1.0f
    val l_height = 1.0f
    val l_width = 1.0f
    gl.glBegin(GL_QUADS)


    gl.glColor3f(0.0f, 1.0f, 0.0f)
    gl.glNormal3f(0.0f, 0.0f, -1.0f)
    gl.glVertex3f(l_length, -l_height, -l_width)
    gl.glVertex3f(-l_length, -l_height, -l_width)
    gl.glVertex3f(-l_length, l_height, -l_width)
    gl.glVertex3f(l_length, l_height, -l_width)

    gl.glColor3f(1.0f, 0.5f, 0.0f)
    gl.glNormal3f(0.0f, 0.0f, 1.0f)
    gl.glVertex3f(-l_length, -l_height, l_width)
    gl.glVertex3f(l_length, -l_height, l_width)
    gl.glVertex3f(l_length, l_height, l_width)
    gl.glVertex3f(-l_length, l_height, l_width)

    gl.glColor3f(1.0f, 0.0f, 0.0f)
    gl.glNormal3f(1.0f, 0.0f, 0.0f)
    gl.glVertex3f(l_length, -l_height, l_width)
    gl.glVertex3f(l_length, -l_height, -l_width)
    gl.glVertex3f(l_length, l_height, -l_width)
    gl.glVertex3f(l_length, l_height, l_width)

    gl.glColor3f(1.0f, 1.0f, 0.0f)
    gl.glNormal3f(-1.0f, 0.0f, 0.0f)
    gl.glVertex3f(-l_length, -l_height, -l_width)
    gl.glVertex3f(-l_length, -l_height, l_width)
    gl.glVertex3f(-l_length, l_height, l_width)
    gl.glVertex3f(-l_length, l_height, -l_width)

    gl.glColor3f(0.0f, 0.0f, 1.0f)
    gl.glNormal3f(0.0f, -1.0f, 0.0f)
    gl.glVertex3f(-l_length, -l_height, -l_width)
    gl.glVertex3f(l_length, -l_height, -l_width)
    gl.glVertex3f(l_length, -l_height, l_width)
    gl.glVertex3f(-l_length, -l_height, l_width)

    gl.glColor3f(1.0f, 0.0f, 1.0f)
    gl.glNormal3f(0.0f, 1.0f, 0.0f)
    gl.glVertex3f(l_length, l_height, -l_width)
    gl.glVertex3f(-l_length, l_height, -l_width)
    gl.glVertex3f(-l_length, l_height, l_width)
    gl.glVertex3f(l_length, l_height, l_width)



    gl.glEnd()
  }

  def createCube(gl: GL2, x: Float, y: Float, z: Float) {
    val blockSize = 1f
    val p1 = Array(x-blockSize, y-blockSize, z+blockSize)
    val p2 = Array(x+blockSize, y-blockSize, z+blockSize)
    val p3 = Array(x+blockSize, y+blockSize, z+blockSize)
    val p4 = Array(x-blockSize, y+blockSize, z+blockSize)
    val p5 = Array(x+blockSize, y-blockSize, z-blockSize)
    val p6 = Array(x-blockSize, y-blockSize, z-blockSize)
    val p7 = Array(x-blockSize, y+blockSize, z-blockSize)
    val p8 = Array(x+blockSize, y+blockSize, z-blockSize)
//    indicesBuf.put(p1)
//    indicesBuf.put(p2)
//    indicesBuf.put(p3)
//    indicesBuf.put(p1)
//    indicesBuf.put(p3)
//    indicesBuf.put(p4)
//    indicesBuf.put(p5)
//    indicesBuf.put(p6)
//    indicesBuf.put(p7)
//    indicesBuf.put(p5)
//    indicesBuf.put(p7)
//    indicesBuf.put(p8)
//    indicesBuf.put(p2)
//    indicesBuf.put(p5)
//    indicesBuf.put(p8)
//    indicesBuf.put(p2)
//    indicesBuf.put(p6)
//    indicesBuf.put(p3)
//    indicesBuf.put(p6)
//    indicesBuf.put(p1)
//    indicesBuf.put(p4)
//    indicesBuf.put(p6)
//    indicesBuf.put(p4)
//    indicesBuf.put(p7)
//    indicesBuf.put(p4)
//    indicesBuf.put(p3)
//    indicesBuf.put(p8)
//    indicesBuf.put(p4)
//    indicesBuf.put(p8)
//    indicesBuf.put(p7)
//    indicesBuf.put(p6)
//    indicesBuf.put(p5)
//    indicesBuf.put(p2)
//    indicesBuf.put(p6)
//    indicesBuf.put(p2)
//    indicesBuf.put(p1)
  }
}
