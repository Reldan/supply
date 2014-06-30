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
    glu.gluLookAt(xEye, yEye, zEye,
      xEye + lookDistance * - Math.sin(angleXZ),
      yEye + lookDistance * Math.sin(angleXY),
      zEye + lookDistance * Math.cos(angleXZ), 1, 0, 0)
    gl.glMatrixMode(GL_MODELVIEW)
    gl.glLoadIdentity()
  }

  def moveForward(distance: Int) = {
    val deltaX = distance * -Math.sin(angleXZ)
    val deltaY = distance * Math.sin(angleXY)
    val deltaZ = distance * Math.cos(angleXZ)
    this.copy(zEye = zEye + deltaZ.toFloat,
              yEye = yEye + deltaY.toFloat,
              xEye = xEye + deltaX.toFloat)
  }
}
