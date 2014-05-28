package com.supply.game

import scala.util.Random

class Level(val height: Int, val width: Int) {
  var data = new Array[Byte](height * width)

  val rnd = new Random()
  transform()

  def transform() = {
    Range(0, data.size).foreach{
      k ⇒ data(k) = rnd.nextInt(10).toByte
    }
  }

  def smooth() = {
    Range(0, data.size).foreach {
      k ⇒
        if (k > 0 && k < data.size - 1)
        data(k) = ((data(k) + data(k - 1) + data(k + 1)) / 3).toByte
    }
  }

}
