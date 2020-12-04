package de.htwg.se.sinkingships.model

//This Class represents a Cell of the Playground.
//The passed Int value can be set to 0 for empty, 1 for Ship ist set and 2 for ship on this place ist hit
//TODO exclude all other Int and Strings
case class Cell(value: Int) {
  //Test:
  def isSet: Boolean = value != 0
}