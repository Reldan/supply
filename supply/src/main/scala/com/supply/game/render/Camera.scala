package com.supply.game.render

import javax.media.opengl.glu.GLU
import javax.media.opengl.fixedfunc.GLMatrixFunc._

import javax.media.opengl.GL2



case class Camera(xEye: Float, yEye: Float, zEye: Float,
                  angleXY: Float, angleXZ: Float, width: Int, height: Int, lookDistance: Int = 500) {
  require(lookDistance > 0)

  def show(gl: GL2, glu: GLU) {
    val aspect = width.asInstanceOf[Float] / height
    gl.glViewport(0, 0, width, if (height == 0) 1 else height)
    gl.glMatrixMode(GL_PROJECTION)
    gl.glLoadIdentity()
    glu.gluPerspective(90.0, aspect, 0.1, 1500.0)
    val (dx, dy, dz) = deltas(lookDistance)
    glu.gluLookAt(xEye, yEye, zEye,
      xEye + dx,
      yEye + dy,
      zEye + dz, 1, 0, 0)
    gl.glMatrixMode(GL_MODELVIEW)
    gl.glLoadIdentity()
  }

  def deltas(rho: Float) = {
    val r = Array(rho * Math.sin(angleXY) * Math.cos(angleXZ),
         rho * Math.cos(angleXY),
         rho * Math.sin(angleXY) * Math.sin(angleXZ)).map(_.toFloat)
    (r(0), r(1), r(2))
  }

  def moveForward(distance: Int) = {
    val (dx, dy, dz) = deltas(distance)
    this.copy(zEye = zEye + dz,
              yEye = yEye + dy,
              xEye = xEye + dx)
  }

  def moveLeft(distance: Int) = {
    this.copy(zEye = zEye + distance,
      yEye = yEye,
      xEye = xEye + distance)
  }
}
