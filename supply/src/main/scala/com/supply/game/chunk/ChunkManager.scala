package com.supply.game.chunk

import javax.media.opengl.GL2

import com.badlogic.gdx.graphics.GL20

class ChunkManager(height: Int, width: Int, depth: Int = 1) {
  val chunkSize = 16

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
      chunks(x)(y)(z).renderer.render(gl, (x - width / 2) * offs, (y - height / 2) * offs, (z - depth / 2) * offs)
    }
  }

}
