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
//    gl.glTranslatef(0, 0, -400)
//    gl.glRotatef(x, 1, 0, 0)
//    gl.glRotatef(y, 0, 1, 0)

    glu.gluLookAt(xEye, yEye, zEye,
      lookDistance * Math.cos(angleXZ) * Math.sin(angleXY),
      lookDistance * Math.sin(angleXZ) * Math.sin(angleXY),
      lookDistance * Math.cos(angleXZ), 1, 0, 0)
    gl.glMatrixMode(GL_MODELVIEW)
    gl.glLoadIdentity()
  }
}
