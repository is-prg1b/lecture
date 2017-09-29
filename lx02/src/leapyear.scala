// パス: src/leapyear.scala
// エンコーディング: UTF8

object LeapYear {
  def leapyear(y: Int) = {
    !(y % 100 == 0) &&
    (y % 4 == 0)
  }
}
