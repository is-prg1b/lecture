---
title: "PRG (11): 並行計算と並列実行"
date: "2017.11.10"
---

# 目次

## 目次

- 並行計算と並列実行

- スレッド

- Future 計算

- データ並列処理：並列コレクション

# 並行計算と並列実行

## 並行計算と並列実行

| 並行 (concurrent) | 並列 (parallel) |
| ----- | ---- |
| 論理的な概念 | 物理的な概念 |
| 計算の間にデータ依存関係がないこと | 同時に実行すること |
| 処理を分離して表現できること | 高速実行を目的とする |

- スレッド (thread): 並列実行の単位．

    複数のスレッドを並列実行する方式をマルチスレッドと呼ぶ

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

## タイマーを設定して寝る

- `System.nanoTime()`

    実行開始からの経過時間をナノ秒単位で取得

- `Thread.sleep(Xミリ秒)`

    スレッドの実行を引数で指定したミリ秒間だけ停止

~~~ {.scala}
(* 実行例 (project lx11) runMain bakery loop *)

  def loop(secs_limit: Int) {
    // secs_limit秒経過するまでループ終了
    val nano_limit = secs_limit * NANO  // ナノ秒に換算
    val t_start = System.nanoTime()     // ループを開始した時刻
    for (t <- 1 to Int.MaxValue) {
      if (System.nanoTime() - t_start > nano_limit) return
      Thread.sleep(1000)                // 1,000ミリ秒 = 1秒お休み
      println(f"t = $t%2d")
    }
  }
~~~

## 例題：パン屋さん（一人営業の場合）

- 一人営業なので並列性がない = シングルスレッド

~~~ {.scala}
(* 実行例 (project lx11) runMain bakery seq *)

  def sequential(secs_limit: Int) {
    ...
    for (i <- 1 to Int.MaxValue) {
      ...
      printbakery("仕事を始めましょう")
      Thread.sleep(3000);  printbakery("パン生地ができました。")
      Thread.sleep(5000);  printbakery("パンが焼きあがりました。")
      Thread.sleep(7000);  printbakery("パンをお店に出しました。")
      Thread.sleep(10000); printbakery("パンをレストランに届けました。")
    }
  }
~~~

## 例題：パン工場（店主のほかに職人が四人の場合）

- 店主：店を開けて，時間が来たら店を閉じる

- 込ね方：３単位時間ごとにパン生地をこねる

- 焼き方：５単位時間ごとにパンを焼く

- 売り方：７単位時間ごとにパンを店に出す

- 配達：１０単位時間ごとにレストランにパンを届ける

## 例題：パン工場（店主）

~~~ {.scala}
    println("さあ、パン屋を開きましょう。")
    Thread.sleep(secs_limit * 1000)
    println("今日はもう閉店です。")
~~~

## 例題：パン工場（こね方ン込ね職人のスレッド

~~~ {.scala}
    Future {
      for (i <- 1 to Int.MaxValue) {
        Thread.sleep(3000)
        printbakery(i, "パン生地ができました。")
      }
    }
~~~

## 例題：パン工場（ほかの職人）

~~~ {.scala}
    // ほかの職人たちの仕事の内容
    for ((t, message) <-
         List((5000, "パンが焼きあがりました。"),
           (7000, "パンをお店に出しました。"),
           (10000, "パンをレストランに届けました。"))) {
       Future {
         for (i <- 1 to Int.MaxValue) {
           Thread.sleep(t)
           printbakery(i, message)
         }
       }
     }
~~~

# データ並列処理

## 並列リスト `list.par`

~~~ {.scala}
val list  = (1 to 1000).toList
val plist = (1 to 1000).toList.par

val vec   = (0 until N).toArray
val pvec  = (0 until N).toArray.par
~~~

- `list`: 普通のリスト．長さ 1,000 で，要素として 1, 2, ..., 1,000 を持っている

- `plist`: 並列リスト．`list` と同じ長さ，同じ内容だが，多くのメソッドが並列化されている．

- `vec`: 普通の配列．長さ N で，要素として 0, 1, ..., N-1 を持っている

- `pvec`: 並列配列．`vec` と同じ大きさ，同じ内容だが，多くのメソッドが並列化されている．

## 逐次実行

~~~ {.scala}
    val c = 100; var a = 0
    {
      val t_start = System.nanoTime()
      for (i <- 1 to c) {
        val vecfib = vec.map((v: Int) => fib(v % 1000, 1, 1, 1))
        a = a + vecfib(Random.nextInt(vecfib.length))
      }
      println(f"${(System.nanoTime() - t_start) * 1e-9}%2.2fsec")
    }
~~~

- `list` の各要素 $v$ についてフィボナッチ数を計算した結果を収集

- `a`: 配列から無作為に選択した要素の値．Scala コンパイラの最適化器がフィボナッチ数の計算結果がどこでも使用されていないことに気づいた場合は，計算を省略する最適化を施す可能性がある．一見，無駄な`a`を計算取得することでそのような最適化を抑制している．

- 実行時間

    - 1.55 sec (1.7 GHz Intel Core i7, 2 cores)

    - 1.05 sec (4.0 GHz Intel Core i7, 4 cores)

## 並列実行

~~~ {.scala}
    val c = 100; var a = 0
    {
      val t_start = System.nanoTime()
      for (i <- 1 to c) {
        val vecfib = pvec.map((v: Int) => fib(v % 1000, 1, 1, 1))
        a = a + vecfib(Random.nextInt(vecfib.length))
      }
      println(f"${(System.nanoTime() - t_start) * 1e-9}%2.2fsec")
    }
~~~

- さっきとほとんど同じコード．異なるのは `vec.map` か `pvec.map` の点だけ．

    ここでは並列化された配列を利用している．

- 実行時間


    - 0.73 sec (1.7 GHz Intel Core i7, 2 cores) -- 2.1倍の高速化

    - 0.31 sec (4.0 GHz Intel Core i7, 4 cores) -- 3.4倍の高速化

## 並列実行の例

文字列のリストに対する並列map

~~~ {.scala}
    // project lx11; runMain Par map

    val lastNames = List("Smith","Jones","Frankenstein","Bach","Jackson","Rodin").par
    print(lastNames.map((name: String) => name.toUpperCase))
~~~

数値配列上の並列fold

~~~ {.scala}
    // project lx11; runMain Par fold

    val pvec = (1 to 10000).toArray.par
    println(f"1 + 2 + ... + 10000 = ${pvec.fold(0)(((accu: Int), (v: Int)) => accu + v)}")
~~~

## 注意：並列データ型と副作用

並列計算の最中に並列データ構造を更新するのは危険

~~~ {.scala}
    // project lx11; runMain Par sideeffect

    for (i <- 1 to 3) {
      var sum = 0
      plist.foreach((v: Int) => sum = sum + v)
      println(f"sum = $sum")
    }
~~~

実行結果

~~~ {.scala}
// 1.7 GHz Intel Core i7, 2 cores
sum = 500500, 498710, 500500

// 4.0 GHz Intel Core i7, 4 cores
sum = 489624, 498601, 495584
~~~

- 正しい結果は 500500

- 並列スレッド群が `sum` に同時に代入するときに一方の代入が無視されることが原因

## 注意：Out of order 実行

- Out of order 実行：並列計算の順序が逐次実行の時と異なること

- 並列配列，並列リスト等への並列計算は out of order 実行

- reduce 処理では結合律が重要

~~~ {.scala}
    // x + (y + z) == (x + y) + z
    println("\n結合律が成立する演算に対しては結果は安定している")
    for (i <- 1 to 3) println(plist.reduce((accu: Int, v: Int) => accu + v))
    // 結果: 500500, 500500, 500500

    // x - (y - z) != (x - y) - z
    println("\n結合律が成立しない演算だと結果は不確定")
    for (i <- 1 to 3) println(plist.reduce((accu: Int, v: Int) => accu - v))
    // 結果は滅茶苦茶： 0, -144890, 497564

    // s1 ++ s2 != s2 ++ s1
    // s1 ++ (s2 ++ s3) == (s1 ++ s2) ++ s3
    println("\n交換律は成立しないが、結合律は成立する例（文字列の連結）")
    val strings = List("abc","def","ghi","jkl","mno","pqr","stu","vwx","yz").par
    println(f"${strings.reduce((s1: String, s2: String) => s1 ++ " " ++ s2)}")
~~~
