package de.htwg.se.sinkingships.model

import org.scalatest.wordspec._
import org.scalatest.matchers.should.Matchers


class CellSpec extends AnyWordSpec with Matchers {

  "A Cell" when {
    "not set to any value " should {
      val emptyCell = Cell(0)
      "have value 0" in {
        emptyCell.value should be(0)
      }
      "not be set" in {
        emptyCell.isSet should be(false)
      }
    }
    "set to a specific value" should {
      val nonEmptyCell = Cell(1)
      "return that value" in {
        nonEmptyCell.value should be(1)
      }
      "be set" in {
        nonEmptyCell.isSet should be(true)
      }
    }
  }

}
