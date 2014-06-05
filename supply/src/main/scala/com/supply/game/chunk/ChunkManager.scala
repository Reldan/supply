package com.supply.game.chunk

import javax.media.opengl.GL2

class ChunkManager(height: Int, width: Int, depth: Int = 1) {
  val chunkSize = 16

//  var chunks = List(List(Chunk.create(chunkSize, chunkSize, chunkSize)))
  var chunks = Array.fill(width, height, depth)(Chunk.create(chunkSize, chunkSize, chunkSize))

  def loadChunks() {
    chunks = Array.fill(width, height, depth)(Chunk.create(chunkSize, chunkSize, chunkSize))
  }

  def loadChunk(data: Array[Array[Array[Byte]]], x: Int, y: Int, z: Int) = {
    chunks(x)(y)(z) = new Chunk(data)
  }

  def render(gl: GL2) {
    val offs = 2 * chunkSize
    for (x ← 0 until width;
         y ← 0 until height;
         z ← 0 until depth) {
      chunks(x)(y)(z).render(gl, (x - width / 2) * offs, (y - height / 2) * offs, (z - depth / 2) * offs)
    }
  }

  def delete() = {
//    chunks.head.deleteBox()
  }
}
