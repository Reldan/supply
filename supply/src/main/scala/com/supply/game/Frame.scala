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

  override def keyPressed(e: KeyEvent) {
    scene.keyboard.addKey(e.getKeyCode)
  }

  override def keyReleased(e: KeyEvent) {
    scene.keyboard.removeKey(e.getKeyCode)
  }

  def mouseClicked(e: MouseEvent): Unit = {}

  def mousePressed(e: MouseEvent): Unit = {}

  def mouseReleased(e: MouseEvent): Unit = {}

  def mouseEntered(e: MouseEvent): Unit = {}

  def mouseExited(e: MouseEvent): Unit = {}

  def mouseDragged(e: MouseEvent): Unit = {
    scene.camera = scene.camera.copy(e.getX, e.getY)
  }

  def mouseMoved(e: MouseEvent): Unit = {
    scene.chunkManager.delete()
  }

  override def keyTyped(e: KeyEvent): Unit = {}
}

