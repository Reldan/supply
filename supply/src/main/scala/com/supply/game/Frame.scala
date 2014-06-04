package com.supply.game

import java.awt.event._
import javax.media.opengl.{GLAutoDrawable, GLEventListener}
import javax.media.opengl.awt.GLCanvas
import javax.media.opengl.glu.GLU
import javax.media.opengl.GL._
import javax.media.opengl.GL2ES1._
import javax.media.opengl.fixedfunc.GLLightingFunc._
import javax.media.opengl.fixedfunc.GLMatrixFunc._
import javax.media.opengl.GL2GL3._

import javax.media.opengl.GL2
import com.supply.game.chunk.{Generator, ChunkManager}

// GL constants

class Frame extends GLCanvas with GLEventListener with KeyListener with MouseListener with MouseMotionListener {

  val managerWidth = 15
  val managerHeight = 15
  val managerDepth = 5
  private var glu: GLU = null
  private val chunkManager = new ChunkManager(managerWidth, managerHeight, managerDepth)
  var mode = 0
  var xCam = 0
  var yCam = 50
  var zCam = 0
  var widthB = 0
  var heightB = 0

  for (x ← 0 until managerWidth;
       y ← 0 until managerHeight;
       z ← 0 until managerDepth) {
    chunkManager.loadChunk(Generator.terrain(chunkManager.chunkSize, chunkManager.chunkSize, chunkManager.chunkSize), x, y, z)
  }


  /** Constructor to setup the GUI for this Component */
  addGLEventListener(this)
  addKeyListener(this)
  addMouseListener(this)
  addMouseMotionListener(this)

  /**
   * Called back immediately after the OpenGL context is initialized. Can be used
   * to perform one-time initialization. Run only once.
   */
  def init(drawable: GLAutoDrawable) {
    val gl: GL2 = drawable.getGL.getGL2
    glu = new GLU
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
//    vertex.init(gl)
  }

  def camera(gl: GL2) {
    val aspect = widthB.asInstanceOf[Float] / heightB
    gl.glViewport(0, 0, widthB, if (heightB == 0) 1 else heightB)
    gl.glMatrixMode(GL_PROJECTION)
    gl.glLoadIdentity()
    glu.gluPerspective(90.0, aspect, 0.1, 1500.0)
    gl.glTranslatef(0, 0, -400)
    gl.glRotatef(xCam, 1, 0, 0)
    gl.glRotatef(yCam, 0, 1, 0)

//    glu.gluLookAt(0, 0, 100, 0, 0, 0, 1, 0, 0)
    gl.glMatrixMode(GL_MODELVIEW)
    gl.glLoadIdentity()
  }

  /**
   * Call-back handler for window re-size event. Also called when the drawable is
   * first set to visible.
   */
  def reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {
    val gl: GL2 = drawable.getGL.getGL2
    this.widthB = width
    this.heightB = height
    camera(gl)
  }

  /**
   * Called back by the animator to perform rendering.
   */
  def display(drawable: GLAutoDrawable) {
    val gl = drawable.getGL.getGL2

    gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
    gl.glLoadIdentity()

    gl.glBegin (GL_LINES)
    gl.glVertex3f(0f, 0f, 0f)
    gl.glVertex3f(0f, 0f, 1000f)
    gl.glVertex3f(0f, 0f, 0f)
    gl.glVertex3f(0f, 1000f, 0f)
    gl.glVertex3f(0f, 0f, 0f)
    gl.glVertex3f(1000f, 0f, 0f)
    gl.glEnd()
        camera(gl)

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
   * Called back before the OpenGL context is destroyed. Release resource such as buffers.
   */
  def dispose(drawable: GLAutoDrawable) {}


  override def keyTyped(e: KeyEvent): Unit = {
  }


  override def keyPressed(e: KeyEvent): Unit = {
    println("pr")
    if (e.getKeyChar == 's') {
      mode = (mode + 1) % 3
    }
    else if (e.getKeyChar == 'w') {
//      chunkManager.loadChunk(Generator.terrain(chunkManager.chunkSize, chunkManager.chunkSize, chunkManager.chunkSize))
    }
    else if (e.getKeyChar == 'd') {
      chunkManager.delete()
    }
    else if (e.getKeyCode == KeyEvent.VK_ESCAPE) {
      System.exit(0)
    }
  }

  override def keyReleased(e: KeyEvent): Unit = {}

  def mouseClicked(e: MouseEvent): Unit = {}

  def mousePressed(e: MouseEvent): Unit = {}

  def mouseReleased(e: MouseEvent): Unit = {}

  def mouseEntered(e: MouseEvent): Unit = {
  }

  def mouseExited(e: MouseEvent): Unit = {
  }

  def mouseDragged(e: MouseEvent): Unit = {
    xCam = e.getX
    yCam = e.getY
  }

  def mouseMoved(e: MouseEvent): Unit = {
    chunkManager.delete()
  }
}

