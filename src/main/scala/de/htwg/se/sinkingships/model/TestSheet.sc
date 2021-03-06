import de.htwg.se.sinkingships.model._

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
  //new
  def start(pg: Playground): Playground ={
    var pgP1L = pg
    var counter = size
    print("Player1 (Left) please set " +size +" Ships")
    var input: String = ""
    do {
      input = readLine()
      pgP1L = pgP1L.setRowWithLetter(input(0).toString,input(1).toString,1)

    } while (counter >= 0)
    pgP1L
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

val emptyPg = new Playground(3)
var pgP1L = emptyPg
var pgP2R = emptyPg

//Player 1 setzt alle bote
println("\n----------Player 1----------------")
pgP1L.playgroundString(pgP1L, pgP2R, "p1")
pgP1L = pgP1L.setRowWithLetter("a","1",1)
pgP1L = pgP1L.setRowWithLetter("a","2",1)
pgP1L = pgP1L.setRowWithLetter("c","1",1)
pgP1L = pgP1L.setRowWithLetter("c","2",1)
pgP1L.playgroundString(pgP1L, pgP2R, "p1")
//Player 2 setzt alle bote
/*
println("\n----------Player 2----------------")
pgP2R.playgroundString(pgP1L, pgP2R, "p2")
pgP2R = pgP2R.setRowWithLetter("a","1",1)
pgP2R = pgP2R.setRowWithLetter("b","1",1)
pgP2R = pgP2R.setRowWithLetter("b","2",1)
pgP2R = pgP2R.setRowWithLetter("c","2",1)
pgP2R.playgroundString(pgP1L, pgP2R, "p2")

 */
println("\n----------Player 1----------------")
//player 1 shoot ships
pgP1L.playgroundString(pgP1L, pgP2R, "p1")
//pgP2R = pgP2R.setRowWithLetter("a","1",2)
//pgP2R = pgP2R.setRowWithLetter("b","1",2)

pgP2R = pgP2R.shoot(pgP2R, 1, 1)
pgP2R = pgP2R.shoot(pgP2R, 1, 2)
pgP2R = pgP2R.shoot(pgP2R, 2, 1)

//pgP2R = pgP2R.setRowWithLetter("c","2",1)

pgP1L.playgroundString(pgP1L, pgP2R, "p1")
pgP1L.isWinning(pgP2R)

//pgP1L.start(pgP1L)