---
title: "PRG (11): 並行計算と並列実行（Future計算）"
date: "2017.11.14"
---

# 前回の復習

## Future 計算

~~~ {.scala}
Future {
  処理内容
  計算結果: T
} : Future[T]
~~~

- Future オブジェクト：どこかで計算を実行し，いずれ計算が終わった暁には，その答えをくれるオブジェクト．

- 計算結果の型が `T` のとき，Future オブジェクトの型は `Future[T]`

- Future オブジェクトの `foreach` メソッドを介して，実行結果を入手する．


## Future 計算の例

~~~ {.scala}
f()
Future { g() }
h()
~~~

1. `f()` を実行する．

1. `g()` を計算する　Future オブジェクトを生成する．

    計算資源に余裕がある場合は（つまり，暇なプロセッサがあるとき）Future オブジェクトはすぐに `g()` の計算を開始する．

1. （Future オブジェクトの計算を待たずに）`h()` の計算を始める．

    計算資源に余裕があり，`g()`と`h()`の計算が重い場合はこれらの二つの計算は並列実行される．

# 同期と計算結果の受領

## Future との同期と計算結果の受領

~~~ {.scala}
  (* project lx12; run future*)

  def future(): Unit = {
    val s = "Hello"
    val f: Future[String] = Future { List(s, " future!").reduce((s1, s2) => s1 + s2) }

    println(f"$s + ...: ${Await.result(f, Duration.Inf)}")
  }
~~~

- Future `f` は文字列のリストを連結する． 

- `println` メソッド中の `Await.result(f, 最大待ち時間)` が Future との同期と値の授受を実施する．

    1. `Await.result(f, ...)` の計算を待つ

    1. `Await.result(..., Duration.Inf)` 計算が終るまで無限に待ち続ける

    1. `Await.result(...)` の結果は `f` の計算結果

# Future計算の合成

## Future計算の合成：合成の目的

- ふたつの Future (`f1` と `f2`) の並列実行を考える．

- 最終的には `f1` と `f2` の計算結果を用いて計算したい．たとえば，分割統治法で大きな問題を複数の部分問題に分割したときに，それぞれの部分問題を Future として独立に並列計算し，それらの結果を合成して最終結果を得たい．

Note
: - 上の例は AND 並列

    - OR 並列はどうする？

## Future計算の合成方法 (`for { v1 <- f1; ... } yield ...`)

~~~ {.scala}
  project lx12; run add

  def add() {
    val f1: Future[Int] = Future { 1 }
    val f2: Future[Int] = Future { 2 }

    val sum: Future[Int] = for {
      v1 <- f1
      v2 <- f2
    } yield (v1 + v2)

    println(f"1 + 2 = ${Await.result(sum, Duration.Inf)}")
  }
~~~

- `f1`, `f2` が Future のとき `for { v1 <- f1; v2 <- f2 } yield ...` によって，複数の Future との同期と値の受理を記述できる．

- `yield ...` によって，`f1` と `f2` の結果を合成した Future の計算を表現する．`...` が合成した Future の計算の内容．

## Future計算の合成方法 (zip)

~~~ {.scala}
  def add_zip() {
    val f1: Future[Int] = Future { 1 }
    val f2: Future[Int] = Future { 2 }

    val (v1, v2) = Await.result(f1.zip(f2), Duration.Inf)

    println(f"1 + 2 = ${v1 + v2}")
  }
~~~

- `Future[T]::zip[U](that: Future[U]): Future[(T, U)]`

    - `f1:Future[T]`, `f2:Future[U]` のとき，`f1.zip(f2)` は `Future[(T, U)]` を返す．

    - つまり，ふたつの Future の計算結果（それぞれの型は T, U）から，それらの組（型は (T, U)）を計算するFutureを返す．

- `Await.result(f1.zip(f2), ...)` は `zip` で合成した Future の計算を待って，`f1`, `f2` の結果の組を待つ．

# Futureとの通信

## Promise

- `Promise[T]` は `Future[T]` 型の Future オブジェクトの計算結果を保持するオブジェクト．

    Future にくっついている覗き穴のようなもの 

- Future 計算が完了していないときは，Promise の値は未了 (`p.isComplete == false`)

- Future 計算が完了していたら Promise の値は `Success[T]`

- Future 計算が途中でコケていたら Promise の値は `Failure[T]`

- Promise `p` が割り当てられている Future は `p.future`

- Future が Promise に値を通知する方法は `p success 計算結果`

## Promise の利用例

~~~ {.scala}
  // project lx12; run promise

  def promise(): Unit = {
    val s = "Hello"

    val p = Promise[String]()
    Future { p success List(s, " future!").reduce((s1, s2) => s1 + s2) }

    println(f"Value from promise: ${Await.result(p.future, Duration.Inf)}")
  }
~~~

- Promise `p` を作成

- Future を作成し，計算 (`List(...).reduce(...)`) の結果を `p success ...` でプロミスに通知

- `Await.result(p.future, ...)` により，Promise `p` に結びついた Future から値を取得

- この例だけ見ると無駄に Promise を使っているだけのように見えるが，Future に一体化されたものを計算と通信に分離することで柔軟性を増している．
    - Future での計算
    - Promise が表す通信路

## Promiseを用いたFutureの合成例

~~~ {.scala}
  def add_promise() {
    def zip(p1: Promise[Int], p2: Promise[Int]): Future[Int] = {
      for {
        v1 <- p1.future
        v2 <- p2.future
      } yield (v1 + v2)
    }
~~~

~~~ {.scala}
    val p1 = Promise[Int]()
    Future { p1 success 1 }

    val p2 = Promise[Int]()
    Future { p2 success 2 }

    println(f"1 + 2 = ${Await.result(zip(p1, p2), Duration.Inf)}")
  }
~~~

# 並列 Fibonacci 計算

## 再帰的 Fibonacci 計算

~~~ {.scala}
  object recursive extends Fibonacci {
    def fib(n: Int): Int = {
      if (n <= 1) 1
      else fib(n-2) + fib(n-1)
    }
  }
~~~

## 再帰的 Fibonacci 計算の解剖

~~~ {.scala}
  object recursive extends Fibonacci {
    def fib(n: Int): Int = {
      if (n <= 1) 1
      else fib(n-2) + fib(n-1)
    }
  }
~~~

1. `n` についての比較

1. `n <= 1` ならば 1 を返す．

1. そうでなければ，
    1. `fib(n - 2)` を計算する
    1. `fib(n - 2)` の計算結果を受け取り，覚えておく．（`v1` ということにしようか）
    1. `fib(n - 1)` を計算する
    1. `fib(n - 1)` の計算結果を受け取り，覚えておく．（`v2` ということにしようか）
    1. `v1` と `v2` の和を計算し，覚えておく．（`sum` ということにしようか）
    1. `sum` を返す．

## 再帰的 Fibonacci 計算の解剖のちょっと不明な点

1. `n` についての比較．

1. `n <= 1` ならば 1 を返す．（**誰に？**）

1. そうでなければ，

    1. `fib(n - 2)` を計算する

    1. `fib(n - 2)` の計算結果を受け取り，覚えておく．（`v1` ということにしようか）

    1. `fib(n - 1)` を計算する

    1. `fib(n - 1)` の計算結果を受け取り，覚えておく．（`v2` ということにしようか）

    1. `v1` と `v2` の和を計算し，覚えておく．（`sum` ということにしようか）

    1. `sum` を返す．（**誰に？**）

## 並列 Fibonacci のアイデア

- 「誰に = 計算結果を渡す相手」を明示

- 計算結果を渡す相手を Promise で表現

## 並列 Fibonacci (sum)

~~~ {.scala}
    // promise1, promise2 はそれぞれ fib(n-1), fib(n-2) を計算している Future たちと繋がる Promise
    // これらの結果を収集したのちに promise_sum で待っている Future に合計値を伝える。
    def sum(promise1: Promise[Int], promise2: Promise[Int], promise_sum: Promise[Int]) = {
      val (v1, v2) = Await.result(promise1.future.zip(promise2.future), Duration.Inf)
      promise_sum success (v1 + v2)
    }
~~~

- 説明は板書の図

## 並列 Fibonacci (fib)

~~~ {.scala}
    // Fibonacci(n)を計算した結果を p に伝える
    def fib(n: Int, p: Promise[Int]) {
      if (n <= 1) p success 1
      else {
        // fib(n-1), fib(n-2)を計算するFutureとPromise
        val promise1 = Promise[Int]() // fib(n-1) の結果を納める Promise
        val promise2 = Promise[Int]() // fib(n-2) の結果を納める Promise
        Future { fib(n-1, promise1) }
        Future { fib(n-2, promise2) }

        // fibを計算するFutureたちから結果を受け取りその和をpで待つFutureに伝えるFuture
        sum(promise1, promise2, p)
      }
    }
~~~

- 説明は板書の図
