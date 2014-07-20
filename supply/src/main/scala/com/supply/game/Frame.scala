package com.supply.game

import java.awt.Robot
import java.awt.event._
import javax.media.opengl.awt.GLCanvas

class Frame extends GLCanvas with KeyListener with MouseListener with MouseMotionListener {
  val scene = new SimpleScene()
  val robot = new Robot()

  /** Constructor to setup the GUI for this Component */
  addGLEventListener(scene)
  addKeyListener(this)
  addMouseListener(this)
  addMouseMotionListener(this)
  setFocusable(true)
  requestFocus()
  requestFocusInWindow()

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

  def mouseDragged(e: MouseEvent): Unit = {}

  def mouseMoved(e: MouseEvent): Unit = {
    val point = e.getLocationOnScreen
    scene.camera = scene.camera.changeAngles(
      (this.getWidth / 2 - point.getX).toFloat / this.getWidth,
      (this.getHeight / 2 - point.getY).toFloat / this.getHeight / 2)
    robot.mouseMove(this.getWidth / 2, this.getHeight / 2)
  }

  override def keyTyped(e: KeyEvent): Unit = {}
}

