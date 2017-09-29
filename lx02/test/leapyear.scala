// パス: test/leapyear.scala
// エンコーディング: UTF8

import org.scalatest._
import LeapYear._

class LeapYearTest extends FlatSpec {
  "４で割り切れる年" should "閏年である" in {
    assert(leapyear(2004) == (true))
    assert(leapyear(2008) == (true))
  }

  "４で割り切れない年" should "閏年ではない" in {
    assert(leapyear(2013) == (false))
    assert(leapyear(2014) == (false))
    assert(leapyear(2015) == (false))
  }

  "100で割り切れる年" should "閏年ではない" in {
    assert(leapyear(1800) == (false))
    assert(leapyear(1900) == (false))
    assert(leapyear(2000) == (false))
  }
}
