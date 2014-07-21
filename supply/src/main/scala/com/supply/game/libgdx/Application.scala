package com.supply.game.libgdx

import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}

object Application extends App {
  val cfg = new LwjglApplicationConfiguration()
  cfg.title = "camera"
  cfg.width = 1280
  cfg.height = 720

  new LwjglApplication(new TestAppScala(), cfg)
}
