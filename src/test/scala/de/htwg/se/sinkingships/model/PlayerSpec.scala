package de.htwg.se.sinkingships.model

import org.scalatest.wordspec._
import org.scalatest.matchers.should.Matchers

class PlayerSpec extends AnyWordSpec with Matchers {
  //"A Players name" should(not Empty)
  /*
  "A Player" when {
    "not set a name " should {
      val emptyPlayerName = Player("NoName")
      "have the name NoName" in {
        emptyPlayerName.name should be("NoName")
      }
      "not be set" in {
        emptyPlayerName.isSet should be(false)
      }
    }
  }
*/
  "A Player" when { "new" should {
    val player = Player("Your Name")
    "have a name"  in {
      player.name should be("Your Name")
    }
    "have a nice String representation" in {
      player.toString should be("Your Name")
    }
  /*
      //val player = Player("")
      "not be set" in {
        Player.isSet should be(false)
      }
*/

  }}
}
