package de.htwg.se.sinkingships.aview

import de.htwg.se.sinkingships.model._
import de.htwg.se.sinkingships.controller.Controller
import de.htwg.se.sinkingships.util._

import scala.io.StdIn.readLine

class Tui(controller: Controller) extends Observer {

  controller.add(this)

  def processInputLine(input: String):Unit = {

    input match {
      case "q" =>
      case "s"|"small"|"3x3"|"set size small" => controller.createEmptyPlayground(3);
      case "m"|"medium"|"6x6"|"set size medium" => controller.createEmptyPlayground(6);
      case "l"|"large"|"9x9"|"set size large" => controller.createEmptyPlayground(9);
      case "start" => controller.start()
      /* //für später
      case "start" => {
          var input2: String = ""
          do {
            input2 = readLine()
            input2 match {
              case "d" => println("det?")
              case _ => println("please set a valid String" )
            }
          } while (input2 != "quit1")
      }*/

      case _ => println("please set a valid String" )



    }
  }
  override def update: Unit =  println(controller.playgroundToString)
}
