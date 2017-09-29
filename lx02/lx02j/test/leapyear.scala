// test/leapyear.scala

import org.scalatest._
import LeapYear._

class LeapYearTest extends FlatSpec {
  "4で割り切れる年" should "閏年である" in {
    assert(leapyear(2012) == (Some(true)))
    assert(leapyear(2016) == (Some(true)))
  }

  "4で割り切れない年" should "閏年でない" in {
    assert(leapyear(2014) == (Some(false)))
    assert(leapyear(2015) == (Some(false)))
    assert(leapyear(2017) == (Some(false)))
  }
  
  "100で割り切れる年" should "閏年でない" in {
    assert(leapyear(1900) == (Some(false)))
  }

  def compare_option[A](value: Option[A], expected: Option[A]) = {
    (value, expected) match {
      case (None, None) => true
      case (Some(v1), Some(v2)) => v1 == v2
      case _ => false
    }
  }

  "1899年前後" should "法律の施行時期を反映" in {
    assert(compare_option(leapyear(1898), None))
    assert(!compare_option(leapyear(1899), None))
  }
}
