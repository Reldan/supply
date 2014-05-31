package com.supply.game

import java.awt._
import java.awt.event._
import javax.swing._
import javax.media.opengl.{GL2, GLAutoDrawable, GLEventListener}
import javax.media.opengl.awt.GLCanvas
import javax.media.opengl.glu.GLU
import com.jogamp.opengl.util.FPSAnimator
import javax.media.opengl.GL._
import javax.media.opengl.GL2ES1._
import javax.media.opengl.fixedfunc.GLLightingFunc._
import javax.media.opengl.fixedfunc.GLMatrixFunc._
import javax.media.opengl.GL2GL3._

import javax.media.opengl.GL2

// GL constants

class Rotate3D extends GLCanvas with GLEventListener with KeyListener {

  private var glu: GLU = null
  private val chunk = new Chunk(16, 16, 16)
  chunk.transform()
//  val vertex = new VertexArray()

  /** Constructor to setup the GUI for this Component */
  addGLEventListener(this)
  addKeyListener(this)

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
    val mat_specular = Array(1.0f, 1.0f, 1.0f, 1.0f)
    val mat_shininess = Array(250.0f)
    val light_position = Array(10f, 0.0f, 0.0f, 100.0f)

//    val light_diffuse = Array(1.0f, 1.0f, 0.0f, 1.0f)

    gl.glMaterialfv(GL_LIGHT0, GL_SHININESS, mat_shininess, 0)
    gl.glLightfv( GL_LIGHT0, GL_POSITION, light_position, 0 )
//    gl.glLightfv( GL_LIGHT0, GL_DIFFUSE,  light_diffuse, 0 )
    gl.glLightfv( GL_LIGHT0, GL_AMBIENT,  mat_specular, 0 )
    gl.glLightfv( GL_LIGHT0, GL_SPECULAR, mat_specular, 0 )
    gl.glEnable( GL_LIGHTING )
    gl.glEnable( GL_LIGHT0 )
//    vertex.init(gl)
  }

  /**
   * Call-back handler for window re-size event. Also called when the drawable is
   * first set to visible.
   */
  def reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {
    val gl: GL2 = drawable.getGL.getGL2
    val aspect: Float = width.asInstanceOf[Float] / height
    gl.glViewport(0, 0, width, if (height == 0) 1 else height)
    gl.glMatrixMode(GL_PROJECTION)
    gl.glLoadIdentity()
    glu.gluPerspective(90.0, aspect, 0.1, 500.0)
    glu.gluLookAt(0, 50, 0, 0, 0, 0, 1, 0, 0)
    gl.glMatrixMode(GL_MODELVIEW)
    gl.glLoadIdentity()
  }

  /**
   * Called back by the animator to perform rendering.
   */
  def display(drawable: GLAutoDrawable) {
    val gl: GL2 = drawable.getGL.getGL2
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
    chunk.render(gl)
  }

  /**
   * Called back before the OpenGL context is destroyed. Release resource such as buffers.
   */
  def dispose(drawable: GLAutoDrawable) {}


  override def keyTyped(e: KeyEvent): Unit = {
    if (e.getKeyChar == 'r') {
      chunk.transform()
    }
  }


  override def keyPressed(e: KeyEvent): Unit = {
    if (e.getKeyChar == 's') {
    }
    else if (e.getKeyCode == KeyEvent.VK_ESCAPE) {
      System.exit(0)
    }
  }

  override def keyReleased(e: KeyEvent): Unit = {}
}

object HelloWorld extends App {
  /** The entry main() method to setup the top-level container and animator */

  // Define constants for the top-level container
  private val TITLE = "Rotating 3D Shapes (GLCanvas)"  // window's title
  private val CANVAS_WIDTH = 600  // width of the drawable
  private val CANVAS_HEIGHT = 480 // height of the drawable
  private val FPS = 60 // animator's target frames per second

  SwingUtilities.invokeLater(new Runnable {
    def run() {
      val canvas: GLCanvas = new Rotate3D
      canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT))
      val animator: FPSAnimator = new FPSAnimator(canvas, FPS, true)
      val frame: JFrame = new JFrame
      frame.getContentPane.add(canvas)
      frame.addWindowListener(new WindowAdapter {
        override def windowClosing(e: WindowEvent) {
          new Thread {
            override def run() {
              if (animator.isStarted) animator.stop
              System.exit(0)
            }
          }.start()
        }
      })
      frame.setTitle(TITLE)
      frame.pack()
      frame.setVisible(true)
      animator.start
    }
  })
}
