package com.supply.game.chunk

import javax.media.opengl.GL2
import com.sudoplay.joise.module.ModuleFractal
import com.sudoplay.joise.module.ModuleBasisFunction.{InterpolationType, BasisType}
import com.sudoplay.joise.module.ModuleFractal.FractalType
import com.supply.game.render.NoiseGen
import scala.util.Random

class ChunkManager {
  val chunkSize = 60

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

  def terrain() = {
    val gen: ModuleFractal = new ModuleFractal()
    gen.setAllSourceBasisTypes(BasisType.GRADIENT)
    gen.setAllSourceInterpolationTypes(InterpolationType.CUBIC)
    gen.setNumOctaves(2)
    gen.setFrequency(0.34)
    gen.setGain(0.5)
    gen.setType(FractalType.FBM)
    gen.setSeed(new Random().nextInt(1000))
//    val gen = new NoiseGen().gen()
    val data = Array.fill(chunkSize, chunkSize, chunkSize)(0.toByte)
    for (x ← 0 until chunkSize;
         z ← 0 until chunkSize
         ) {
          println(gen.get(x, z))
         Range(0, (Math.abs(gen.get(x, z) / 4)  * chunkSize).toInt).foreach
          { y ⇒
             data(x)(y)(z) = (Math.abs(gen.get(x, y, z) * 7) + 1).toByte
          }
    }
    chunks = List(new Chunk(data))
  }


}
