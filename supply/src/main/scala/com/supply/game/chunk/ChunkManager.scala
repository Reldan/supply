package com.supply.game.chunk

import javax.media.opengl.GL2

class ChunkManager {
  val chunkSize = 50

  var chunks = List(Chunk.create(chunkSize, chunkSize, chunkSize))

  def loadChunks() {
    chunks = List(Chunk.create(chunkSize, chunkSize, chunkSize))
  }

  def loadChunk(data: Array[Array[Array[Byte]]]) = {
    chunks ::= new Chunk(data)
  }

  def render(gl: GL2) {
//    Range(0, chunks.size).foreach{
//      i ⇒ chunks
//    }
    chunks.zipWithIndex.foreach{
      case (ch, ind) ⇒
      ch.render(gl, 0, 0, ind * 2 * chunkSize)
    }
  }

  def delete() = {
//    chunks.head.deleteBox()
  }
}
