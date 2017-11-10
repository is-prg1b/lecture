import scala.language.postfixOps
import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object Fib {
  def future(): Unit = {
    val s = "Hello"
    val f: Future[String] = Future { s + " future!" }

    println(f"$s + ...: ${Await.result(f, 0 nanos)}")
  }

  def promise(): Unit = {
    val p = Promise[Int]()
    Future { p success 1 }

    val v = p.future map { v => { println(v) } }

    println(f"value from promise: ${Await.result(v, 0 nanos)}")
  }

  def add1() {
    val f1: Future[Int] = Future { 1 }
    val f2: Future[Int] = Future { 2 }

    val sum = for {
      v1 <- f1
      v2 <- f2
    } yield (v1 + v2)

    println("1 + 2 = " + Await.result(sum, 0 nanos))
  }

  def add2() {
    def start(output: (Promise[Int], Promise[Int]) => Awaitable[Int]) {
      val p1 = Promise[Int]()
      Future { p1 success 1 }

      val p2 = Promise[Int]()
      Future { p2 success 2 }

      println(Await.result(output(p1, p2), 0 nanos))
    }

    def output(p1: Promise[Int], p2: Promise[Int]) = {
      for {
        v1 <- p1.future
        v2 <- p2.future
      } yield (v1 + v2)
    }

    start(output)
  }

  trait Fibonacci {
    def fib(n: Int): Int
  }

  object recursive extends Fibonacci {
    def fib(n: Int): Int = if (n <= 1) 1 else fib(n-2) + fib(n-1)
  }

  object parallel extends Fibonacci {

    // Fibonacci(n)を計算した結果を p に伝える
    def fib(n: Int, p: Promise[Int]) {
      if (n <= 1) p success 1
      else {
        // fib(n-1), fib(n-2)を計算するFutureとPromise
        val p_fib1 = Promise[Int]()
        val p_fib2 = Promise[Int]()
        Future { fib(n-1, p_fib1) }
        Future { fib(n-2, p_fib2) }

        // fibを計算するFutureたちから結果を受け取りその和をpで待つFutureに伝えるFuture
        Future { sum(p_fib1, p_fib2, p) }
      }
    }

    // p_fib1, p_fib2 はそれぞれ fib(n-1), fib(n-2) を計算している Future たちと繋がる Promise
    // これらの結果を収集したのちに p で待っている Future に和を伝える。
    def sum(p_fib1: Promise[Int], p_fib2: Promise[Int], p_sum: Promise[Int]) {
      for {
        v1 <- p_fib1.future
        v2 <- p_fib2.future
      } p_sum success (v1 + v2)
    }

    def fib(n: Int) = {
      val p = Promise[Int]()
      fib(n, p)
      Await.result(p.future, 0 nano)
    }
  }

  def usage() {
      println("runMain Fib {future, rec, promise, add1, add2, fib}")
  }

  def main(arguments: Array[String]) {
    val param = if (arguments.length == 0) "recursive" else arguments(0)
    param match {
      case "future" => future()
      case "rec" => println(f"Fibonacci(10) = ${recursive.fib(10)}")
      case "promise" => promise()
      case "add1" => add1()
      case "add2" => add2()
      case "fib" => {
        println(f"fib(10) = ${recursive.fib(10)}")
        println(f"future.fib(10) = ${parallel.fib(10)}")
      }
      case _ => usage()
    }
  }
}
