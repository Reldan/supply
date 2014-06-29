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

   val gen: ModuleFractal = new ModuleFractal()
    gen.setAllSourceBasisTypes(BasisType.GRADIENT)
    gen.setAllSourceInterpolationTypes(InterpolationType.CUBIC)
    gen.setNumOctaves(5)
    gen.setFrequency(0.005)
    gen.setType(FractalType.BILLOW)
    gen.setSeed(new Random().nextInt(1000))

  def terrain(width: Int, height: Int, depth: Int, xp: Int, yp: Int, zp: Int, totalX: Int, totalY: Int, totalZ: Int) = {

    val data = Array.fill(width, height, depth)(0.toByte)
    for (x ← 0 until width; y ← 0 until height) {
      val px = x + xp * width
      val py = y + yp * height
      var zz = (((1 - Math.abs(gen.get(px, py) / 2)) * totalZ - zp) * depth).toInt
      zz = if (zz < 0) 0 else if (zz > depth) depth else zz
       Range(0, zz).foreach
        { z ⇒
//          val pz = z + zp * depth
          data(x)(y)(z) = ((zp * depth + z ) * 6 / (totalZ * depth) + 1).toByte
//          data(x)(y)(z) = Math.abs(1).toByte
        }
    }
    data
  }
}
