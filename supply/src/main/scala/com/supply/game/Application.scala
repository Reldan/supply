package com.supply.game

import javax.swing.{JFrame, SwingUtilities}
import javax.media.opengl.awt.GLCanvas
import java.awt.Dimension
import com.jogamp.opengl.util.FPSAnimator
import java.awt.event.{WindowEvent, WindowAdapter}


object Application extends App {
  /** The entry main() method to setup the top-level container and animator */

  // Define constants for the top-level container
  private val TITLE = "Supply"  // window's title
  private val CANVAS_WIDTH = 600  // width of the drawable
  private val CANVAS_HEIGHT = 480 // height of the drawable
  private val FPS = 60 // animator's target frames per second

  SwingUtilities.invokeLater(new Runnable {
    def run() {
      val canvas: GLCanvas = new Frame
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

