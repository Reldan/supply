package com.supply.game

import java.awt.event._
import javax.media.opengl.awt.GLCanvas

class Frame extends GLCanvas with KeyListener with MouseListener with MouseMotionListener {
  val scene = new SimpleScene()

  /** Constructor to setup the GUI for this Component */
  addGLEventListener(scene)
  addKeyListener(this)
  addMouseListener(this)
  addMouseMotionListener(this)
  requestFocus()


  override def keyTyped(e: KeyEvent): Unit = {
    println("h")
  }


  override def keyPressed(e: KeyEvent): Unit = {
    println("pr")
    if (e.getKeyChar == 's') {
      scene.mode = (scene.mode + 1) % 3
    }
    else if (e.getKeyChar == 'd') {
      scene.chunkManager.delete()
    }
    else if (e.getKeyChar == 'w') {
      scene.camera = scene.camera.copy(z = scene.camera.z + 1)
    }
    else if (e.getKeyCode == KeyEvent.VK_ESCAPE) {
      System.exit(0)
    }
  }

  override def keyReleased(e: KeyEvent): Unit = {
    println(e.getKeyChar)
  }

  def mouseClicked(e: MouseEvent): Unit = {}

  def mousePressed(e: MouseEvent): Unit = {}

  def mouseReleased(e: MouseEvent): Unit = {}

  def mouseEntered(e: MouseEvent): Unit = {
  }

  def mouseExited(e: MouseEvent): Unit = {
  }

  def mouseDragged(e: MouseEvent): Unit = {
    scene.camera = scene.camera.copy(e.getX, e.getY)
  }

  def mouseMoved(e: MouseEvent): Unit = {
    scene.chunkManager.delete()
  }
}

