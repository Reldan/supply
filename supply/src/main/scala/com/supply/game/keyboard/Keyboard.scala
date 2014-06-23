package com.supply.game.keyboard

import java.awt.event.KeyEvent

class Keyboard() {
  private var keyPressedCodes: Set[Int] = Set.empty[Int]

  private def ch2co(char: Char) = KeyEvent.getExtendedKeyCodeForChar(char)

  def addKey(code: Int) {keyPressedCodes += code}
  def addKey(char: Char) {addKey(ch2co(char))}
  def removeKey(code: Int) {keyPressedCodes -= code}
  def removeKey(char: Char) {removeKey(ch2co(char))}

  def contains(code: Int) = keyPressedCodes.contains(code)
  def contains(char: Char) = keyPressedCodes.contains(ch2co(char))
}
