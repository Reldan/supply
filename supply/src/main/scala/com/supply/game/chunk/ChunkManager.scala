package com.supply.game.chunk

import javax.media.opengl.GL2

/**
 * Created by reldan on 01/06/14.
 */
class ChunkManager {
  var chunks = List(new Chunk(16, 16, 16))

  def loadChunks() {
    chunks = List(new Chunk(16, 16, 16))
  }

  def render(gl: GL2) {
    chunks.foreach(_.render(gl))
  }

}
