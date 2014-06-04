package com.supply.game.chunk

import com.sudoplay.joise.module.ModuleFractal
import com.sudoplay.joise.module.ModuleBasisFunction.{InterpolationType, BasisType}
import com.sudoplay.joise.module.ModuleFractal.FractalType
import scala.util.Random

object Generator {

  def random(width: Int, height: Int, depth: Int) = {
    val data = Array.fill(width, height, depth)(0.toByte)
    for (x ← 0 until width;
         y ← 0 until height;
         z ← 0 until depth) {
      data(x)(y)(z) = BoxType.getRandomNonEmptyType()
    }
    data
  }

  def sphere(sideSize: Int) = {
    val data = Array.fill(sideSize, sideSize, sideSize)(0.toByte)
    for (x ← 0 until sideSize;
         y ← 0 until sideSize;
         z ← 0 until sideSize) {
      if (Math.sqrt((x - sideSize.toFloat / 2) * (x - sideSize.toFloat / 2) +
                    (y - sideSize.toFloat / 2) * (y - sideSize.toFloat / 2) +
                    (z - sideSize.toFloat / 2) * (z - sideSize.toFloat / 2)) <= sideSize.toFloat/2){
             data(x)(y)(z) = BoxType.getRandomNonEmptyType()
          }
    }
    data
  }

  def terrain(width: Int, height: Int, depth: Int) = {
    val gen: ModuleFractal = new ModuleFractal()
    gen.setAllSourceBasisTypes(BasisType.GRADIENT)
    gen.setAllSourceInterpolationTypes(InterpolationType.CUBIC)
    gen.setNumOctaves(5)
    gen.setFrequency(2.34)
    gen.setType(FractalType.BILLOW)
    gen.setSeed(new Random().nextInt(1000))
    val data = Array.fill(width, height, depth)(0.toByte)
    for (x ← 0 until width; z ← 0 until depth) {
        val px = x.toDouble / height
        val pz = z.toDouble / depth
         Range(0, height - (Math.abs(gen.get(px, pz) / 2)  * height).toInt).foreach
          { y ⇒
             data(x)(y)(z) = (y * 6 / height + 1).toByte
          }
    }
    data
  }
}
