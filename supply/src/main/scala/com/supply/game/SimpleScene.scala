package com.supply.game

import java.awt.event.KeyEvent
import javax.media.opengl.{GL2, GLAutoDrawable, GLEventListener}
import javax.media.opengl.glu.GLU
import javax.media.opengl.GL._
import javax.media.opengl.GL2ES1._
import javax.media.opengl.fixedfunc.GLLightingFunc._
import javax.media.opengl.GL2GL3._
import com.jogamp.opengl.util.gl2.GLUT
import com.supply.game.keyboard.Keyboard
import com.supply.game.render.Camera
import com.supply.game.chunk.{Generator, ChunkManager}

class SimpleScene extends GLEventListener {
  /**
   * Called back immediately after the OpenGL context is initialized. Can be used
   * to perform one-time initialization. Run only once.
   */
  val glu = new GLU
  val glut = new GLUT
  var camera = Camera(0, 0, 500, 1.3f, 3, 1000, 0)
  private var mode = 0
  val keyboard = new Keyboard()
  val cameraSpeed = 5

  val managerWidth = 15
  val managerHeight = 15
  val managerDepth = 5

  val chunkManager = new ChunkManager(managerWidth, managerHeight, managerDepth)


  for (x ← 0 until managerWidth;
       y ← 0 until managerHeight;
       z ← 0 until managerDepth) {
    chunkManager.loadChunk(Generator.terrain(chunkManager.chunkSize, chunkManager.chunkSize, chunkManager.chunkSize, x, y, z, managerWidth, managerHeight, managerDepth), x, y, z)
  }

  val actionMap = Map[Char, () => Unit](
    'q' -> (() => mode = (mode + 1) % 3),
    'w' -> (() => camera = camera.moveForward(cameraSpeed)),
    's' -> (() => camera = camera.moveForward(-cameraSpeed)),
    'a' -> (() => camera = camera.copy(xEye = camera.xEye - cameraSpeed)),
    'd' -> (() => camera = camera.copy(xEye = camera.xEye + cameraSpeed))
  )

  def processKeys() {
    actionMap.foreach{
      case (el, fun) if keyboard.contains(el) => fun()
      case _ =>
    }
    if (keyboard.contains(KeyEvent.VK_ESCAPE)) {
      System.exit(0)
    }
  }


  def init(drawable: GLAutoDrawable) {
    val gl: GL2 = drawable.getGL.getGL2
    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
    gl.glClearDepth(1.0f)
    gl.glEnable(GL_DEPTH_TEST)
    gl.glDepthFunc(GL_LEQUAL)
    gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST)
    gl.glShadeModel(GL_SMOOTH)
    val mat_specular = Array(0.5f, 0.5f, 0.5f, 1.0f)
    val mat_shininess = Array(250.0f)
    val light_position = Array(10f, 0.0f, 0.0f, 100.0f)

    val light_diffuse = Array(0.3f, 0.3f, 0.3f, 1.0f)

//    gl.glMaterialfv(GL_LIGHT0, GL_SHININESS, mat_shininess, 0)
    gl.glLightfv( GL_LIGHT0, GL_POSITION, light_position, 0 )
    gl.glLightfv( GL_LIGHT0, GL_DIFFUSE,  light_diffuse, 0 )
    gl.glLightfv( GL_LIGHT0, GL_AMBIENT,  mat_specular, 0 )
//    gl.glLightfv( GL_LIGHT0, GL_SPECULAR, mat_specular, 0 )
    gl.glEnable( GL_LIGHTING )
    gl.glEnable( GL_LIGHT0 )
    gl.glMaterialfv(GL_FRONT, GL_EMISSION, Array(0,0,0,0f),0)
//    gl.glColorMaterial( GL_FRONT_AND_BACK, GL_AMBIENT_AND_DIFFUSE ); // call glColorMaterial before enabling GL_COLOR_MATERIAL
    gl.glEnable( GL_COLOR_MATERIAL )
    gl.glEnable(GL_BLEND); //Enable blending.
    gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA); //Set blending function.
//    vertex.init(gl)
  }

  /**
   * Called back before the OpenGL context is destroyed. Release resource such as buffers.
   */
  def dispose(drawable: GLAutoDrawable) {}

  /**
   * Called back by the animator to perform rendering.
   */
  def display(drawable: GLAutoDrawable) {
    processKeys()
    val gl = drawable.getGL.getGL2

    gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
    gl.glLoadIdentity()

    gl.glColor3f(0, 0, 0)
    gl.glBegin (GL_LINES)
      gl.glVertex3f(0f, 0f, 0f)
      gl.glVertex3f(0f, 0f, 1000f)
      gl.glVertex3f(0f, 0f, 0f)
      gl.glVertex3f(0f, 1000f, 0f)
      gl.glVertex3f(0f, 0f, 0f)
      gl.glVertex3f(1000f, 0f, 0f)
    gl.glEnd()

    gl.glPushMatrix()
    gl.glTranslatef(0, 0, 0)
    gl.glColor3f(1, 0, 0)
    gl.glRasterPos2d(0, 0)
    glut.glutBitmapString(8, s"(${camera.angleXY}, ${camera.angleXZ}})")
    gl.glPopMatrix()

    camera.show(gl, glu)



    if (mode == 1) {
      gl.glPolygonMode(GL_FRONT_AND_BACK, GL_POINT)
    } else if (mode == 2) {
      gl.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE)
    } else {
      gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL)
    }
    chunkManager.render(gl)
  }


  /**
   * Call-back handler for window re-size event. Also called when the drawable is
   * first set to visible.
   */
  def reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {
    val gl: GL2 = drawable.getGL.getGL2
    camera = camera.copy(width = width, height = height)
    camera.show(gl, glu)
  }

}
