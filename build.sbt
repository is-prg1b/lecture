// ライブラリ群

val scalactic = "org.scalactic" %% "scalactic" % "3.0.1"

// テストのために使う ScalaTest ライブラリ
val scalatest = "org.scalatest" %% "scalatest" % "3.0.1" % "test"

// データ生成テストのために使う ScalaCheck ライブラリ
val scalacheck = "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"

// 並行処理のための Akka Actor ライブラリ
val akka_actor = "com.typesafe.akka" % "akka-actor_2.12" % "2.5.4"

lazy val commonSettings = Seq(
  organization := "prg17.is.titech.ac.jp",
  version := "0.1.0-SNAPSHOT",

  // scalac に与えるオプション
  scalaVersion := "2.12.3",   // コンパイルに使う scalac のバージョン
  scalacOptions := Seq("-feature", "-unchecked", "-deprecation"),
  libraryDependencies ++= Seq(scalactic, scalatest, scalacheck),

  fork in (Test, run) := true,
  connectInput := true,
  // Scalaのプログラムの置き場所を非標準の場所に指定．
  // 通常は src/scala/*.scala, test/scala/*.scala に置く．
  // これはJavaとの共存のためにsrcとtestの下をScalaとJavaで棲み分けるためである．
  // 本実習ではJavaのプログラムを書くことはないので，非標準だがより単純な設定を採用する．
  scalaSource in Compile := baseDirectory.value / "src",
  scalaSource in Test    := baseDirectory.value / "test",

  // コンパイル結果を非標準の場所に設定
  // この設定はコンパイルの副産物がDropbox等のクラウドストレージに保存されることを
  // 避けるためのものです。これによりクラウドストレージとの同期時間が短縮されます。
  target := Path.userHome / "tmp" / "sbt" / "prg1b" / name.value
)

lazy val root = (project in file("."))
//.aggregate(lx02)

lazy val lx02 = project
  .settings(commonSettings)
  .settings(name := "lx02", version := "0.1.0")

lazy val lx02a = project
  .settings(commonSettings)
  .settings(name := "lx02a", version := "0.1.0")

lazy val lx02b = project
  .settings(commonSettings)
  .settings(name := "lx02b", version := "0.1.0")

lazy val lx02c = project
  .settings(commonSettings)
  .settings(name := "lx02c", version := "0.1.0")

lazy val lx02d = project
  .settings(commonSettings)
  .settings(name := "lx02d", version := "0.1.0")

lazy val lx02e = project
  .settings(commonSettings)
  .settings(name := "lx02e", version := "0.1.0")

lazy val lx02f = project
  .settings(commonSettings)
  .settings(name := "lx02f", version := "0.1.0")

lazy val lx02g = project
  .settings(commonSettings)
  .settings(name := "lx02g", version := "0.1.0")

lazy val lx02h = project
  .settings(commonSettings)
  .settings(name := "lx02h", version := "0.1.0")

lazy val lx02i = project
  .settings(commonSettings)
  .settings(name := "lx02i", version := "0.1.0")

lazy val lx02j = project
  .settings(commonSettings)
  .settings(name := "lx02j", version := "0.1.0")

lazy val lx02k = project
  .settings(commonSettings)
  .settings(name := "lx02k", version := "0.1.0")
