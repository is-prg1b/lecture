---
title: "lx02: TDD (leapyear)"
date: "Sep. 29, 2017"
---

# テスト駆動開発 
Test Driven Development (TDD)

## テスト駆動開発の実際

ひとまず，*やる気のないコード*を作成

テストを実施するコードを作成（完璧でなくてよい）

以下を繰り返し

- テストを実行

- テストに合格するようにプログラムを修正

- 想定外のバグを発見 → バグを再現するテストを追加

## 例：閏年の計算

目標：西暦(Y)が与えられたときに，その年が閏年か否かを答えるメソッドleapYearを作成しなさい

## 日本における閏年の根拠法

明治三十一年勅令第九十号（閏年ニ関スル件・明治三十一年五月十一日勅令第九十号

- 神武天皇即位紀元年数ノ四ヲ以テ整除シ得ヘキ年ヲ閏年トス

- 但シ紀元年数ヨリ六百六十ヲ減シテ百ヲ以テ整除シ得ヘキモノノ中更ニ四ヲ以テ商ヲ整除シ得サル年ハ平年トス

## （超訳）明治三十一年勅令第九十号

- 神武天皇が即位なさった年を紀元とする年数<span class="footnote">（これが紀元年数）</span>が4で割り切れるものを閏年とする

- ただし、紀元年数から660を減じたもの<span class="footnote">（神武天皇の即位の年は西暦-660年とされている）</span>が100で割り切れるもののうち、さらにその商が4で割り切れないもの<span class="footnote">(つまり、西暦換算が400で割り切れないもの）</span>は平年とする。

## 早い話が、

グレゴリオ暦では、次の規則に従って400年間に（100回ではなく）97回の閏年を設ける。

- 西暦年が4で割り切れる年は閏年

- ただし、西暦年が100で割り切れる年は平年

- ただし、西暦年が400で割り切れる年は閏年

## lx02a

空のコードとテストのファイルを用意する．

~~~ {.ocaml .src}
// src/leapyear.scala

object LeapYear {
}
~~~


~~~ {.ocaml .test}
// test/leapyear.scala

import org.scalatest._
import LeapYear._

class LeapYearTest extends FlatSpec {
}
~~~

## lx02b

やる気のないコードとして、これ以上はないほど愚かなコードを作る．

型だけは仕様に合わせる．

~~~ {.ocaml .src}
object LeapYear {
  def leapyear(y: Int) : Boolean = {
    true
  }
}
~~~

~~~ {.ocaml .test}
import org.scalatest._
import LeapYear._

class LeapYearTest extends FlatSpec {
}
~~~

## lx02b: sbtでテストを開始

まだ，テストが空だから，なにも起きない

[テスト結果へ](/lecture/note/lx02-tests.html#lx02b)

## lx02c

テストのためのコードを作成

完璧でなくてよい

~~~ {.ocaml .src}
object LeapYear {
  def leapyear(y: Int) : Boolean = {
    true
  }
}
~~~

~~~ {.ocaml .test}
import org.scalatest._
import LeapYear._

class LeapYearTest extends FlatSpec {
  "4で割り切れる年" should "閏年である" in {
    assert(leapyear(2012) == (true))
    assert(leapyear(2016) == (true))
  }
}
~~~

## lx02c: 自動テストの結果は？

[テスト結果へ](/lecture/note/lx02-tests.html#lx02c)

みごとにテストをパス．完璧！

## lx02d: と，喜んでいると，天の声

- 曰く「4で割り切れない年は平年」

    - 「やべ，テストが甘い！追加しなくちゃ」

## lx02d

4で割り切れない年のテストを追加

~~~ {.ocaml .test}
import org.scalatest._
import LeapYear._

class LeapYearTest extends FlatSpec {
  "4で割り切れる年" should "閏年である" in {
    assert(leapyear(2012) == (true))
    assert(leapyear(2016) == (true))
  }

  "4で割り切れない年" should "閏年でない" in {
    assert(leapyear(2014) == (false))
    assert(leapyear(2015) == (false))
    assert(leapyear(2017) == (false))
  }
}
~~~

## lx02d: テストの結果 (1/2)

- `LeapYearTest` で問題発見

- `leapyear.scala` の13行目を見てごらん．`false` が欲しいのに，`true`じゃん．

[テスト結果へ](/lecture/note/lx02-tests.html#lx02d)

## lx02d: テストの結果 (2/2)

`LeapYearTest` で問題発見

- 一箇所コケたよ

- コケたテストは `LeapYearTest` 残念

[テスト結果へ](/lecture/note/lx02-tests.html#lx02d)

## lx02d: テストの13行目

そこでテストコードの13行目を見る もちろんテストの内容は正しい．


~~~ {.ocaml .test}
import org.scalatest._
import LeapYear._

class LeapYearTest extends FlatSpec {
  "4で割り切れる年" should "閏年である" in {
    assert(leapyear(2012) == (true))
    assert(leapyear(2016) == (true))
  }

  "4で割り切れない年" should "閏年でない" in {
    assert(leapyear(2014) == (false))
    assert(leapyear(2015) == (false))
    assert(leapyear(2017) == (false))
  }
}
~~~

## lx02e: ソースの修正

（テストは正しいので）プログラムの問題を（探すまでもないが）探し

~~~ {.ocaml .src}
object LeapYear {
  def leapyear(y: Int) : Booelan = {
    false
  }
}
~~~

（敢て）小ずる賢い変更を施してみよう

## lx02e: 追加したテストはok

でも，さっきは成功していた8行目が．．．

[テスト結果へ](/lecture/note/lx02-tests.html#lx02e)

## lx02f: 真面目に対応するか

4で割り切れば閏年なんでしょ？

~~~ {.ocaml .src}
object LeapYear {
  def leapyear(y: Int) : Boolean = {
    y % 4 == 0
  }
}
~~~

## lx02f: 三度，テストを見ると

すべてパス！やったー！

[テスト結果へ](/lecture/note/lx02-tests.html#lx02f)

## lx02g: テストの追加

調子にのってテストを追加

~~~ {.ocaml .test}
import org.scalatest._
import LeapYear._

class LeapYearTest extends FlatSpec {
  "4で割り切れる年" should "閏年である" in {
    assert(leapyear(2012) == (true))
    assert(leapyear(2016) == (true))
  }

  // 中略

  "100で割り切れる年" should "閏年でない" in {
    assert(leapyear(2012) == (false))
    assert(leapyear(2016) == (false))
  }
}
~~~

## lx02g: 今度のテストは？

もちろん，こけてます．

[テスト結果へ](/lecture/note/lx02-tests.html#lx02g)

## lx02h: テストを通るように修正

~~~ {.ocaml .src}
object LeapYear {
  def leapyear(y: Int) : Boolean = {
    !(y % 100 == 0) && y % 4 == 0
  }
}
~~~

## lx02h: これでどうだ？

[テスト結果へ](/lecture/note/lx02-tests.html#lx02g)

## lx02i: ふたたび天の声

「そもそも，法律施行前の閏年というものは．．．」

- 法律の施行は明治32年1月1日 (西暦 1899.1.1) ．それ以前については意味がない．

    - 「意味がない」をどうやって表す？

        1. エラーを出力 → 例外処理

        1. 「あるかなきか」を表すデータ型 (Option) を利用

## lx02i: Option 型

~~~
Option[A] ::= None | Some[A+]
~~~

- ここでは返り値として「Boolean があるかなきか」なので `Option[Boolean]` を利用

~~~
Option[Boolean] ::= None | Some[Boolean+]
~~~

- `Option[Boolean]` な値は: `None` か `Some(true)` か `Some(false)`

## lx02i: テストの修正

~~~ {.ocaml .test}
class LeapYearTest extends FlatSpec {
  // 中略

  def compare_option[A](value: Option[A], expected: Option[A]) = {
    (value, expected) match {
      case (None, None) => true
      case (Some(v1), Some(v2)) => v1 == v2
      case _ => false
    }
  }

  "1899年前後" should "法律の施行時期を反映" in {
    assert(leapyear(1898) == (None))
    assert(leapyear(1899) != (None))
  }
}
~~~

## lx02i: テストするのだけど

当然，コケます．型エラーでテストのビルドに失敗．

原因はテストコードとソースコードの型が一致しないこと．

[テスト結果へ](/lecture/note/lx02-tests.html#lx02i)

## lx02j: ソースの型をテストにあわせて修正

~~~ {.ocaml .src}
object LeapYear {
  def leapyear(y: Int) : Option[Boolean] = {
    Some(!(y % 100 == 0) && y % 4 == 0)
  }
}
~~~

## lx02j: テスト！

今度はテストの失敗．まだ，ソースコードは法律の施行時期に対応していないからね．

[テスト結果へ](/lecture/note/lx02-tests.html#lx02i)

## lx02k: 法律の施行時期への対応

~~~ {.ocaml .src}
object LeapYear {
  def leapyear(y: Int) : Option[Boolean] = {
    if (y < 1899) None
    else Some(!(y % 100 == 0) && y % 4 == 0)
  }
}
~~~

## lx02k: テスト

~~~
[info] Tests: succeeded 4, failed 0, canceled 0, ignored 0, pending 0
~~~

[テスト結果へ](/lecture/note/lx02-tests.html#lx02k)

## lx02?: いやいや，まだ駄目でしょう

天の声「ただし西暦年が400で割り切れる年は閏年」

- ということは，2000年とか1600年は閏年？

- 施行時期のこともあるから1600年は．．．

## lx02?: おさらい＆残りは任せた

TDD手法を用いて，講義資料のステップ１からステップ４までを順次おさらいしなさい．

さらにTDDを自分で実践し，閏年のプログラムを完成しなさい．最終的には講義資料の「早い話が、」のページに記載された仕様を満すこと．
