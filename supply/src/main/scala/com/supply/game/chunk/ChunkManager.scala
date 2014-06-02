package com.supply.game.chunk

import javax.media.opengl.GL2

class ChunkManager {
  val chunkSize = 20

  var chunks = List(Chunk.create(chunkSize, chunkSize, chunkSize))

  def loadChunks() {
    chunks = List(Chunk.create(chunkSize, chunkSize, chunkSize))
  }

  def render(gl: GL2) {
    chunks.foreach(_.render(gl))
  }

  def delete() = {
//    chunks.head.deleteBox()
  }

  def random() = {
    val data = Array.fill(chunkSize, chunkSize, chunkSize)(0.toByte)
    for (x ← 0 until chunkSize;
         y ← 0 until chunkSize;
         z ← 0 until chunkSize) {
      data(x)(y)(z) = BoxType.getRandomBoxType()
    }
    chunks = List(new Chunk(data))
  }

  def sphere() = {
    val data = Array.fill(chunkSize, chunkSize, chunkSize)(0.toByte)
    for (x ← 0 until chunkSize;
         y ← 0 until chunkSize;
         z ← 0 until chunkSize) {
      if (Math.sqrt((x - chunkSize.toFloat / 2) * (x - chunkSize.toFloat / 2) +
                    (y - chunkSize.toFloat / 2) * (y - chunkSize.toFloat / 2) +
                    (z - chunkSize.toFloat / 2) * (z - chunkSize.toFloat / 2)) <= chunkSize.toFloat/2)
          {
             data(x)(y)(z) = BoxType.getRandomNonEmptyType()
          }
    }
    chunks = List(new Chunk(data))
  }

}
