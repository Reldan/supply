package com.supply.game.chunk

import javax.media.opengl.GL2

class ChunkManager {
  val chunkSize = 16

  var chunks = List(new Chunk(chunkSize, chunkSize, chunkSize))

  def loadChunks() {
    chunks = List(new Chunk(chunkSize, chunkSize, chunkSize))
  }

  def render(gl: GL2) {
    chunks.foreach(_.render(gl))
  }

  def delete() = {
    chunks.head.deleteBox()
  }

}
