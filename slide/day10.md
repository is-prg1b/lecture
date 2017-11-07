---
title: "PRG (10): 正規表現VMの実装"
date: "2017.11.7"
---

# 目次

## 目次

- `object` / `class` / `trait` とその拡張

    - 例題の実装と `extends` の利用

- 仮想機械の命令セット

- 仮想命令

- コンパイル：正規表現から命令列への変換

- 3種の仮想機械の実装

# `object` / `class` / `trait` とその拡張

## `object` 定義

~~~ {.scala}
object RegularExpressionVM {
  type Program = Array[Instruction]

  val version = "regexp.sclala v1.01"
  var time_start = System.nanoTime()

  def printProgram(program: Program) { ... }
}
~~~

- Scala の `object` は宣言の集合体．C言語の structure に似ている．
    - `type` 宣言を用いた型名の宣言
    - `val` 宣言を用いた定数の宣言
    - `var` 宣言を用いた変数の宣言
    - `def` 宣言を用いた関数（メソッド）の宣言

## `class` 定義

~~~ {.scala}
class Complex(_re: Double, _im: Double) {
  def re = _re
  def im = _im
  def plus(c: Complex) = new Complex(_re + c.re, _im + c.im)
  def minus(c: Complex) = new Complex(re - c.re, im - c.im)
  ...
}
~~~

- Scala の `class` は `object` の雛形の定義
    - `object` 宣言はクラス宣言と`new`の省略形と見做すことができる．

    - `class`は型でもある．クラスのインスタンスは型としてのクラスに属する．

~~~ {.scala}
class _RegularExpressionVM_ {
  type Program = ...
  val version = ...
  var time_start = ...
  def printProgram(...) { ...}
}
val RegularExpressionVM = new _RegularExpressionVM_()
~~~

## `trait` 定義

~~~ {.scala}
trait RegularExpression {
  def _compile(label: Int): (Int, LProgram)
  def compile: Program = (_compile(0)._2 ++ List(Match)).toArray
}
~~~

- `trait` は，これに定義を追加することでクラスやオブジェクトを作成するためのもの

- `trait` は，クラスやオブジェクトを構成するための雛形

## 拡張定義を用いたオブジェトの作成

- 既存の `object`, `class`, `trait` に定義を追加して，新たな `object, `class`, `trait` を追加できる．

~~~ {.scala}
object A {
  val x = 1
}

object B extends A { val y = 2 }

object C extends A { val y = 3 }
~~~

- `A` オブジェクトを拡張して `B` オブジェクトと `C` オブジェクトを定義している．
    - `A.x` = `B.x` = `C.x` = 1
    - `B.y` = 2, `C.y` = 3

    共通する定義 (`x`) の共有による，定義の簡素化

## 拡張定義を用いたクラスの定義 (1/2)

~~~ {.scala}
trait RegularExpression {
  def _compile(label: Int): (Int, LProgram)

  def compile: Program = (_compile(0)._2 ++ List(Match)).toArray
}

case class C(c: Char) extends RegularExpression {
  override def toString: String = c.toString

  def _compile(label0: Int): (Int, LProgram) = {
    val (label1, char) = (label0+1, List(Character(c)))
    (label1, char)
  }
}
~~~

- `RegularExpression` trait を拡張して，１文字読み込み命令にあたる `C` クラスを定義

## 拡張定義を用いたクラスの定義 (2/2)

~~~ {.scala}
trait RegularExpression {
  def _compile(label: Int): (Int, LProgram)
  def compile: Program = (_compile(0)._2 ++ List(Match)).toArray
}

case class C(c: Char) extends RegularExpression {
  override def toString: String = c.toString

  def _compile(label0: Int): (Int, LProgram) = { ...  }
}
~~~

- （すべてのオジェクト/クラス/traitに共通に定義されている）`toString` を再定義し

- `RegularExpression` で定義された `_compile` 関数を提供し

- `RegularExpression` では型だけが定められていた `compile` 関数を定義している．

# 仮想機械の命令セット

## 仮想機械命令の構成

- **trait** Instruction
    - **case class** Character(Char) extends Instruction
    - **case class** Jump(Int)       extends Instruction
    - **case class** Split(Int, Int) extends Instruction
    - **case class** Match           extends Instruction

- `case class` 定義は `trait` の拡張の一種

# さまざまな正規表現

## 正規表現の構成

- trait RegularExpression
    - **case object** Empty            extends RegularExpression
    - **case class** C(Char)           extends RegularExpression
    - **case class** Alternate(RE, RE) extends RegularExpression
    - **case class** Star(RE)          extends RegularExpression

## **trait** `RegularExpression`

~~~ {.scala}
trait RegularExpression {
  def _compile(label: Int): (Int, LProgram)

  def compile: Program = (_compile(0)._2 ++ List(Match)).toArray
}
~~~

- `compile` 関数については trait が定義を与えている

- `_compile` 関数については，引数と返り値の型を定めているだけ．`RegularExpression` を拡張して定義するクラスやオブジェクトがこの定義に沿って`_compile`を提供しなくてはいけない．

## `_compile`

`_compile(label: Int): (Int, LProgram)`

- *label* は命令列の最初の命令のインデックス

- 返り値の第1項 (Int) はこの正規表現に続く命令列の最初の命令のインデックス．この正規表現に対応する命令列の長さが $\ell$ ならば $\mathit {label} + \ell$を返す．

- 返り値の第2項はこの正規表現に該当する命令列．

## **object** `Empty` -- ($\varepsilon$)

~~~ {.scala}
case object Empty extends RegularExpression {
  def _compile(label0: Int): (Int, LProgram) = (label0, List())
}
~~~

- 正規表現 $\varepsilon$ に対応した定義

    ${\cal T}[\varepsilon] = \varepsilon$

    - この変換はラベルを消費しないので，渡された *label0* をそのまま返す

    - $\varepsilon$ に対応するのは空命令列 (= `List()`)

- 各正規表現をオブジェクトとして表現するなかで，$\varepsilon$ は世界に唯ひとつなので，わざわざクラスを利用せずとも単純なオブジェクトとして定義できる．

## class `C(Char)` -- ($c$)

~~~ {.scala}
case class C(c: Char) extends RegularExpression {
  def _compile(label0: Int): (Int, LProgram) = {
    val (label1, char) = (label0+1, List(Character(c)))
    (label1, char)
  }
}
~~~

- ${\cal T}[c] = \text {char } c$

- 正規表現$c$に該当するのは`[Character(c)]`の1命令なので，この命令列の次の命令のインデックスは$\mathit {label}_0 + 1$．

## class `Concatenate(r1, r2)`

$r_1\ r_2$

~~~ {.scala}
case class Concatenate(r1: RegularExpression, r2: RegularExpression) extends RegularExpression {
  def _compile(label0: Int): (Int, LProgram) = {
    val (label1, program1) = r1._compile(label0)
    val (label2, program2) = r2._compile(label1)
    (label2, program1 ++ program2)
  }
}
~~~

- ${\cal T}[r_1\ r_2] = {\cal T}[r_1]; {\cal T}[r_2]$

- $r_1$ と $r_2$ を順次コンパイルしたものを繋げればよい．

    - `val (_, program1) = r1._compile(...)`
    - `val (_, program2) = r2._compile(...)`
    - `program1 ++ program2`

- 命令のインデックスの受け渡しに注意: `label0`, `label1`, `label2`

## class `Alternate(r)`

$$\begin {align}
{\cal T}[r_1 | r_2] =\, & \text {split } L_1, L_2 \\
L_1:\quad & {\cal T}[r_1] \\
     & \text {jump } L_3 \\
L_2:\quad & {\cal T}[r_2] \\
L_3:\quad &
\end {align}$$

~~~ {.scala}
case class Alternate(r1: RegularExpression, r2: RegularExpression) extends RegularExpression {
  def _compile(label0: Int): (Int, LProgram) = {
    val label1 = label0 + 1 // Split(L1, L3)
    val (label2, program1) = r1._compile(label1)
    val label3 = label2 + 1 // Jump(L4)
    val (label4, program2) = r2._compile(label3)

    val split = List(Split(label1, label3))
    val jump = List(Jump(label4))
    (label4, split ++ program1 ++ jump ++ program2)
  }
}
~~~

# 仮想機械の実装

## **trait** VM -- 仮想機械の雛形

~~~ {.scala}
trait VM {
  type ProgramCounter = Int
  type StringIndex = Int

  def execute(program: Program, s: String): Boolean
~~~

- *execute*: 正規表現に対応する命令列 (*program*) を入力文字列 ($s$) に適用し，受理か否かを返答

- *trait* VM を拡張して，実際に動作する VM を定義

    - `object RecursiveBackgrackingVM extends VM`
    - `object IterativeBackgrackingVM extends VM`
    - `object KenThompsonVM extends VM`

## **object** RecursiveBackgrackingVM

~~~ {.scala}
object RecursiveBacktrackingVM extends VM {
  def execute(_program: Program, s: String): Boolean = {
    val program = _program.toArray
    def _execute(pc: ProgramCounter, i: StringIndex): Boolean = {
      program(pc) match {
        case Character(c) => i < s.size && s(i) == c && _execute(pc+1, i+1)
        case Jump(label) => _execute(label, i)
        case Split(label1, label2) => _execute(label1, i) || _execute(label2, i)
        case Match => i == s.size
      }
    }
    _execute(0, 0)
  }
}
~~~

## `execute` の概要 (1/2)

~~~ {.scala}
def execute(_program, s): Boolean = {
  val program = _program.toArray
  def _execute(pc, i) = {
    program(pc) match {
      case Character(c) => ...
      case Jump(label) => ...
      case Split(label1, label2) => ...
      case Match => ...
    }
  }
  _execute(0, 0)
}
~~~

- *pc*: 次に実行するのは$program$中の第$pc$番目の命令

- *i*: 次に読み込む文字は入力文字列$s$中の第$i$番目

- `execute(0, 0): 初期状態は `execute(pc = 0, i = 0)`

## `execute` の概要 (2/2)

- `Character(c) => i < s.size && s(i) == c && _execute(pc+1, i+1)`

    文字列を読み過ぎようとしたら非受理，そうでなければ次の文字(i + 1)について次の命令(pc + 1)を実行

- `Jump(label) => _execute(label, i)`

    指定されたラベルの命令を実行(pc = label)

- `Split(label1, label2) => _execute(label1, i) || _execute(label2, i)`

    label1 からの実行，あるいは label2 からの実行のいずれかが成功すれば受理

- `Match => i == s.size`

    すべての文字列を読み切っていたら受理．ここでの条件を `true` にすれば `findPrefixOf` のような機能になる．

## **object** IterativeBackgrackingVM の概要

~~~ {.scala}
def execute(_program: Program, s: String): Boolean = {
  var threads = Queue[(ProgramCounter, StringIndex)]((0, 0))

  while (!threads.isEmpty) { // スレッドプールが空になるまで
    val ((_pc, _i), rest) = threads.dequeue
    threads = rest
    var pc  = _pc
    var i   = _i

    try { // スレッドの実行
      while (true) { program(pc) match { /* 命令の識別と処理 */  } }
    } catch { case MatchFailure => () }
  }
  false // すべてのスレッドが尽きたら非受理
}
~~~

## ThompsonVM の概要

~~~ {.scala}
def execute(_program, s) = {
  var threads = Set[ProgramCounter](0)  // i文字目を処理したがっているスレッドたち

  for (i <- 0 to s.length) {
    var nextThreads = Set[ProgramCounter]()  // i+1文字目を処理したがっているスレッドたち
    while (!threads.isEmpty) {
      val pc = threads.head
      threads = threads - pc
      program(pc) match { /* 命令の識別と処理 */ }
    }
    threads = nextThreads
  }
  false
}
~~~

## ThompsonVM の最内ループ

~~~ {.scala}
var nextThreads = Set[ProgramCounter]()  // i+1文字目を処理したがっているスレッドたち
while (!threads.isEmpty) {
  val pc = threads.head
  threads = threads - pc
  program(pc) match {
    case Character(_c) => if (i < s.length && s(i) == _c) nextThreads = nextThreads + (pc + 1)
    case Jump(label) => threads = threads + label
    case Split(label1, label2) => threads = threads + label1 + label2
    case Match => if (endOfLine(i)) return true
  }
}
~~~

- 妙にすっきりしてないか？ループはスレッドについてのものだけ．スレッド内の命令列を順次処理するコードはどこに行った？

    - `case Jump(label) => threads = threads + label` -- これは何？
    - `case Split(label1, label2) => threads = threads + label1 + label2` -- これはまた何？

## ThompsonVM: 発想の転換

- RecursiveBacktrackingVM と IterativeBacktrackingVM

    各スレッドについて，そのスレッドが誕生した状態からオートマトンを動かして，それぞれ受理するかどうか観察しましょう．

- ThompsonVM

    文字列を先頭から眺めて，その文字を読み込もうとするスレッド群を順次更新する方法．気分的には $\mathit {threads}_0$, $\mathit {threads}_1$, ... を順次生成し，$\mathit {threads}_n$ のなかに受理するものがあるか観察しましょう．
