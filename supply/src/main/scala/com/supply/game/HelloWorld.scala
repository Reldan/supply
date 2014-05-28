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

// GL constants

class Rotate3D extends GLCanvas with GLEventListener with KeyListener {

  private var glu: GLU = null
  private var angleCube: Float = 0
  private var speedCube: Float = -1.5f
  private val level = new Level(100, 100)

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
    val mat_shininess = Array(50.0f)
    val light_position = Array(1.0f, 1.0f, 1.0f, 0.0f)

    val light_diffuse = Array(1.0f, 0.0f, 0.0f, 1.0f)
//    glClearColor (0.0, 0.0, 0.0, 0.0);
//    glShadeModel (GL_SMOOTH);

//     gl.glMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular, 0)
//     gl.glMaterialfv(GL_FRONT, GL_SHININESS, mat_shininess, 0)
//     gl.glLightfv(GL_LIGHT0, GL_POSITION, light_position, 0)
//
//     gl.glEnable(GL_LIGHTING);
//     gl.glEnable(GL_LIGHT0)
//     gl.glEnable(GL_DEPTH_TEST)

    gl.glLightfv( GL_LIGHT0, GL_POSITION, mat_specular, 0 )
    gl.glLightfv( GL_LIGHT0, GL_DIFFUSE,  light_diffuse, 0 )
    gl.glLightfv( GL_LIGHT0, GL_AMBIENT,  mat_specular, 0 )
    gl.glLightfv( GL_LIGHT0, GL_SPECULAR, mat_specular, 0 )
    gl.glEnable( GL_LIGHTING )
    gl.glEnable( GL_LIGHT0 )
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
    //glu.gluLookAt(0, -300, 0, 0, 0, 0, 0, 1, 12)



    gl.glMatrixMode(GL_MODELVIEW)
    gl.glLoadIdentity()
  }

  def drawCube(gl: GL2, x: Float, y: Float, z: Float) = {
    gl.glLoadIdentity()
//    gl.glTranslatef(0.0f, 0.0f, -70.0f)
//    val whiteMaterial = Array(1.0f, 1.0f, 1.0f, 1.0f)
//    gl.glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, whiteMaterial, 0);
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


  /**
   * Called back by the animator to perform rendering.
   */
  def display(drawable: GLAutoDrawable) {
    val gl: GL2 = drawable.getGL.getGL2
    gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
    Range(0, level.data.size).foreach(
      i ⇒
        Range(0, level.data(i) + 1).foreach{ j =>
        drawCube(gl, (i % level.width - level.width / 2) * 2.1f, (i / level.height - level.height / 2) * 2.1f, j * 2.1f - 100)
        }
    )

    angleCube += speedCube
  }

  /**
   * Called back before the OpenGL context is destroyed. Release resource such as buffers.
   */
  def dispose(drawable: GLAutoDrawable) {}


  override def keyTyped(e: KeyEvent): Unit = {}

  override def keyPressed(e: KeyEvent): Unit = {
    if (e.getKeyChar == 'r') {
      level.transform()
    }
    else if (e.getKeyChar == 's') {
      level.smooth()
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
