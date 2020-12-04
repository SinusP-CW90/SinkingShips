package de.htwg.se.sinkingships.model

import scala.io.StdIn.readLine

case class Playground(cells:Matrix[Cell]){
  //set the size with the Matrix class
  def this(size:Int) = this(new Matrix[Cell](size, Cell(0)))
  //Int value with the size of the Playground
  val size:Int = cells.size
  //return the called cell
  def cell(row:Int, col:Int):Cell = cells.cell(row, col)
  //change the cell value in the Playground
  def set(row:Int, col:Int, value:Int):Playground = copy(cells.replaceCell(row, col, Cell(value)))
  def setRowWithLetter(rowString:String, columnString:String, value:Int):Playground = {
    val UpperRowString = rowString.toUpperCase
    val row = (UpperRowString(0) -65).toChar.toInt
    val col = columnString.toInt
    copy (cells.replaceCell (row, col-1, Cell (value)))
  }
  //show the called row of the Playground
  def row(row:Int):Vector[Cell] = cells.rows(row)
  //show the called column of the Playground
  def col(col:Int):Vector[Cell] = cells.rows.map(row=>row(col))
  //
  def shoot(pg: Playground, row:Int, col:Int):Playground = {
    if (cell(row,col).value == 1){
      println("hit")
      this.set(row,col, 2)
    }
    else {
      println("miss")
      pg
    }
  }
  def isWinning(pg: Playground): Boolean ={
    var isWinning = true
    for (x <- 1 to pg.size) {
      for (y <- 1 to pg.size) {
        if(pg.cell(x-1,y-1).value == 1){
          println("found a 1 in " +x +" " +y)
          isWinning =  false
        }
      }
    }
    isWinning
  }
//TODO fehler abfangen, wenn ships doppelt oder außerhalb der spielfeld größe gesetzt werden
  def setShips(pgP1: Playground, pgP2:Playground, currentPlayer:String): Playground ={
    var pgP1L = pgP1
    var pgP2R = pgP2
    var counter = size
    print("please set " +size +" Ships\n")
    var input: String = ""
    do {
      input = readLine()
      pgP1L = pgP1L.setRowWithLetter(input(0).toString,input(1).toString,1)
      counter = counter -1
      println(pgP1L.playgroundString(pgP1L,pgP2,currentPlayer))
      print(counter +" Ships are left\n")
    } while (counter != 0)
    if(currentPlayer =="p1"){
      pgP1L
    }
    else{
      pgP2R
    }
  }

  def start(pgP1: Playground, pgP2:Playground): Playground ={
    setShips(pgP1,pgP2,"p1")
    setShips(pgP2,pgP1,"p2")
    pgP1
  }

  //generate a String that represents the Playground
  def playgroundString(pgP1:Playground, pgP2:Playground, currentPlayer: String): String = {

    //create a number String for the first line of the Playground
    //(allows different playground sizes for P1 and P2)
    def buildNumberRowString(): String ={
      var numberString = "\n"
      var currentNumberP1 = "  "
      var currentNumberP2 = "  "
      for (x <- 1 to pgP1.size) {
        currentNumberP1 = currentNumberP1.concat(x.toString + "  ")
      }
      numberString = numberString.concat(currentNumberP1 + "|")
      for (x <- 1 to pgP2.size) {
        currentNumberP2 = currentNumberP2.concat(x.toString + "  ")
      }
      numberString.concat(currentNumberP2)
    }

    //check if the cell ist set to 0 or to another value (that changes the String)
    def setRowForPlayer(x:Int, y:Int, pg:Playground, lineString: String): String ={

      var currentCell = lineString

      if(pg.cell(x-1,y-1).value == 0){
        currentCell = currentCell.concat(" . ")
      }
      if(pg.cell(x-1,y-1).value == 1) {
        currentCell = currentCell.concat(" x ")
      }
      if(pg.cell(x-1,y-1).value == 2) {
        currentCell = currentCell.concat(" o ")
      }
      currentCell
    }

    def setRowForEnemy(x:Int, y:Int, pg:Playground, lineString: String): String ={

      var currentCell = lineString

      if(pg.cell(x-1,y-1).value < 2){
        currentCell = currentCell.concat(" . ")
      }
      if(pg.cell(x-1,y-1).value == 2) {
        currentCell = currentCell.concat(" o ")
      }
      currentCell
    }
    //creates a String for the Playground
    def buildRows(): String ={
      var playgroundString = ""
      var lineP1 = ""
      var lineP2 = ""
      //First loop creates the column Strings
      for (x <- 1 to pgP1.size) {
        //Set first Letter
        playgroundString = playgroundString.concat(("A"(0) + x - 1).toChar.toString)
        //loop to create the row Strings
        for (y <- 1 to pgP1.size) {

          //build the Playground row Strings from the cell values for player1 and player 2
          if (currentPlayer == "p1"){
            lineP1 = setRowForPlayer(x,y,pgP1,lineP1)
            lineP2 = setRowForEnemy(x,y,pgP2,lineP2)
          }
          if (currentPlayer == "p2"){
            lineP1 = setRowForEnemy(x,y,pgP1,lineP1)
            lineP2 = setRowForPlayer(x,y,pgP2,lineP2)
          }
        }
        playgroundString = playgroundString.concat(lineP1 +" | " +lineP2 +("A"(0) + x - 1).toChar.toString+"\n")
        lineP1=""
        lineP2=""
      }
      playgroundString
    }
    "%s\n%s".format(buildNumberRowString(), buildRows())
  }
}