---
title: "小テスト１<br/>学籍番号：<br/>氏名："
---

あるフォルダに Makefile, simple.c, simple.scala のみがあるものとする．Makefileの内容は以下であり，simple.c, simple.scalaはそれぞれCとScalaで記述された，文法に沿って正しく記述され，意味のあるプログラムとする．

~~~ {#lx01-makefile .makefile .src .numberLines}
all: simple Simple.class

simple: simple.c
	clang -o simple simple.c

Simple.class: simple.scala
	scalac simple.scala

clean:
	rm -f simple simple.s *.class

run-c: ./simple
	./simple
~~~

<div class="quiz">

1. まず，`make simple` を実行した．このコマンド実行によって新規生成されるファイルの名前とそのファイルの働きを簡潔に説明しなさい．

1. つぎに `make` を実行した．このコマンド実行によって新規生成されるファイルの名前とそのファイルの働きを簡潔に説明しなさい．

1. 再度 `make` を実行した．このコマンド実行によって新規生成されるファイルの名前とそのファイルの働きを簡潔に説明しなさい．

1. `simple.c` を修正した．修正後の `simple.c` は文法的に正しいものとする．ここで `make` を実行したときに新規生成されるファイルの内容とそのファイルの働きを簡潔に説明しなさい．

1. 上の `Makefile` には `simple.c` のプログラムを実行するための `run-c` 規則は書かれている．`simple.scala` の実行に相当するものはないので，それを `run-s` 規則として記述しなさい．

1. `make clean` を実行した．このコマンドの実行後にフォルダに残っているファイル名を列挙しなさい．

</div>
