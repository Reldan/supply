package com.supply.game.chunk

import org.scalatest._
import com.supply.game.render.ChunkRenderer

class ChunkSpec extends FlatSpec
    with MustMatchers{

  val cube: Array[Array[Array[Byte]]] = Array.fill(3, 3, 3)(1.toByte)

  val plate: Array[Array[Array[Byte]]] = Array.fill(2, 2, 2)(1.toByte)

  for (x ← 0 until 2;
       y ← 0 until 2;
       z ← 0 until 2) {
    if (z == 0) {
      plate(x)(y)(z) = 0.toByte
    }
  }

  "Chunk" must "show write count of near boxes to render" in {
    Chunk.nearBoxes(0, 0, 0, Array(Array(Array(1.toByte)))).count(el ⇒ el) must be (0)
    Chunk.nearBoxes(1, 1, 1, cube).count(el ⇒ el) must be (6)
    Chunk.nearBoxes(1, 0, 1, plate).count(el ⇒ el) must be (2)
    Chunk.nearBoxes(1, 1, 1, plate).count(el ⇒ el) must be (2)
  }
}
