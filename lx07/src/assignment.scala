package assignment

import scala.collection.mutable.ArraySeq

class O(_x: Int) {
  var x = _x
}

object Assignment {
  def assign(x: Int) {
    // x = 1  // 変数への代入は C では許されるが Scala では禁止
  }

  def assign(xs: ArraySeq[Int]) {
    xs(0) = 1
  }

  def assign(o: O) {
    o.x = 1
  }

  def main(arguments: Array[String]) {
    val x = 0
    println(f"x = $x")
    assign(x)
    println(f"x = $x\n")

    val xs = ArraySeq(0)
    println(f"xs(0) = ${xs(0)}")
    assign(xs)
    println(f"xs(0) = ${xs(0)}\n")

    val o = new O(0)
    println(f"o.x = ${o.x}")
    assign(o)
    println(f"o.x = ${o.x}\n")
  }
}
