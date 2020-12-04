package de.htwg.se.sinkingships


import de.htwg.se.sinkingships.model._
import de.htwg.se.sinkingships.aview.Tui
import de.htwg.se.sinkingships.controller.Controller

import scala.io.StdIn.readLine

object SinkingShips {
  val controller = new Controller(new Playground(3),new Playground(3))
  val tui = new Tui(controller)
  controller.notifyObservers

  def main(args: Array[String]): Unit = {
    var input: String = ""
    do {
      input = readLine()
      tui.processInputLine(input)
    } while (input != "quit")
  }

}
