package com.supply.game.chunk

import javax.media.opengl.GL2
import com.sudoplay.joise.module.ModuleFractal
import com.sudoplay.joise.module.ModuleBasisFunction.{InterpolationType, BasisType}
import com.sudoplay.joise.module.ModuleFractal.FractalType
import scala.util.Random

class ChunkManager {
  val chunkSize = 2

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
      data(x)(y)(z) = BoxType.getRandomNonEmptyType()
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
    gen.setNumOctaves(5)
    gen.setFrequency(0.34)
//    gen.setGain(0.5)
    gen.setType(FractalType.BILLOW)
    gen.setSeed(new Random().nextInt(1000))
//    val gen = new NoiseGen().gen()
    val data = Array.fill(chunkSize, chunkSize, chunkSize)(0.toByte)
    for (x ← 0 until chunkSize;
         z ← 0 until chunkSize
         ) {

        val px = x.toDouble / chunkSize
        val pz = z.toDouble / chunkSize
         Range(0, (Math.abs(gen.get(px, pz) / 2)  * chunkSize).toInt).foreach
          { y ⇒
//             data(x)(y)(z) = (Math.abs(gen.get(px, y.toDouble / chunkSize, pz) / 3 * 7) + 1).toByte
             data(x)(y)(z) = (y * 6 / chunkSize + 1).toByte
          }
    }
    chunks = List(new Chunk(data))
  }


}
