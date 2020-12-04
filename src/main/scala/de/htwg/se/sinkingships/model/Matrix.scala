package de.htwg.se.sinkingships.model

//class to create a 2D Vector with the Generic Datatype [T]
case class Matrix[T] (rows:Vector[Vector[T]]) {
  //Funktion
  // => is syntactic sugar for creating instances of functions. Recall that every function in scala is an instance of a class.
  //For example, the type Int => String, is equivalent to the type Function1[Int,String]
  // i.e. a function that takes an argument of type Int and returns a String.
  def this(size:Int, filling:T) = this(Vector.tabulate(size, size){(row, col) => filling})
  //value size retrun the size of the 2d Vector rows
  val size:Int = rows.size
  //Cell function to address the exact cell in the Vector
  def cell(row:Int, col:Int):T = rows (row)(col)
  //creates a copy and updates the desired cell
  def replaceCell(row:Int, col:Int, cell:T):Matrix[T] = copy(rows.updated(row, rows(row).updated(col, cell)))
  //fill object (Vector.tabulate generates a list from the Vector)
  def fill (filling:T):Matrix[T]= copy( Vector.tabulate(size, size){(row, col) => filling})
}