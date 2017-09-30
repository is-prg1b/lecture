# 内容

- `src/simple.c`

    C言語で書かれた簡単な級数のプログラム

- `src/simple.scala`

    `src/simple.c`と同等のScalaプログラム

- `src/Makefile`

    上述のふたつのプログラムをビルド、クリーン、実行するためのスクリプト．[Makefile については別途詳述した．](/lecture/note/lx01-makefile.html)

    - `make`: すべてのソフトウェアをまとめてビルド

    - `make clean`: お掃除

    - `make run-c`: `simple.c` をビルドして生成された `simple` コマンドを実行

    - `make run-s`: `simple.scala` をビルドして生成された `simple.class` を Scala の実行系を用いて実行

    - `make run-j`: `simple.scala` をビルドして生成された `simple.class` を Java の実行系を用いて実行
