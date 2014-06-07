package com.supply.game.render

import org.scalatest.{MustMatchers, FlatSpec}
import com.supply.game.chunk.Chunk

class RendererSpec extends FlatSpec
    with MustMatchers {

  "Renderer" must "increase position after putting new vertex" in {
    val renderer = new ChunkRenderer(new Chunk(Array.empty), 10)
    renderer.addVertex(Array(1, 2, 3f), Array(1f, 2, 3)) must be (0)
    renderer.addVertex(Array(1, 2, 3f), Array(1f, 2, 3)) must be (1)
  }

}
