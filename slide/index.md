---
title: プログラミング第一
css: ["lib/reveal.js-3.5.0/css/theme/solarized.css", "lib/kw.css"]
---

<style>
body {
  margin: 5em;
}

code {
  color: #b44;
}
</style>

<script type="text/javascript" src="lib/reveal.js-3.5.0/plugin/highlight/highlight.js"></script>
<script>hljs.initHightlightingOnLoad();</script>

# 講義内容についての質問など

[質問，疑問等の受け付け先](https://github.com/is-prg1b/lecture/issues/new)．受け付けた質問は教員，TA，履修生で共有されます．これ以外の人々には公開されません．

少ないスタッフで大勢を教育する都合上，質問，疑問，それへの対応は可能な限り共有する方針としています．羞恥心などの理由から授業や課題に直接関連する質問を**メールで送付しないで**下さい．以下に issue で対応したい問い合わせ例を示します．

- 講義資料 `***.pdf` の X ページの説明は間違っていませんか

- 講義資料 `***.pdf` の Y ページの説明がわかりません．

- `lx??` リポジトリに含まれるサンプルコード `***.scala` を実行しようとしたが動きません

- 自宅のパソコンに `sbt` をインストールしたが動きません

- 講義資料 `***` を応用して，＊＊＊なことをやってみたいが，＊＊＊がわからない．ヒントを下さい．

以下のような例
: 授業や課題の内容に直接関わらない相談については脇田＆森へのメールでお願いします．

    - 病気のため休学することになったので特別な配慮をして欲しい

# 講義資料とサンプルコード

講義資料
: - [day01](https://github.com/is-prg1b/lecture/raw/master/slides/day01.pdf) <!--
    - [day02](https://github.com/is-prg1b/lecture/raw/master/docs/day02.pdf)
    - [day03](https://github.com/is-prg1b/lecture/raw/master/docs/day03.pdf)
    - [day04](https://github.com/is-prg1b/lecture/raw/master/docs/day04.pdf)
    - [day05](https://github.com/is-prg1b/lecture/raw/master/docs/day05.pdf)
    - [day06](https://github.com/is-prg1b/lecture/raw/master/docs/day06.pdf)
    - [day07](https://github.com/is-prg1b/lecture/raw/master/docs/day07.pdf)
    - [day08](https://github.com/is-prg1b/lecture/raw/master/docs/day08.pdf)
    - [day09](https://github.com/is-prg1b/lecture/raw/master/docs/day09.pdf)
    - [day10](https://github.com/is-prg1b/lecture/raw/master/docs/day10.pdf)
    - [day11](https://github.com/is-prg1b/lecture/raw/master/docs/day11.pdf)
    - [day12](https://github.com/is-prg1b/lecture/raw/master/docs/day12.pdf)
    - [day13](https://github.com/is-prg1b/lecture/raw/master/docs/day13.pdf)
    - [day14](https://github.com/is-prg1b/lecture/raw/master/docs/day14.pdf) -->

サンプルコード
: 講義の進捗ごとにリポジトリを用意します．

    - [day01](https://github.com/is-prg1b/lx01) <!-- /
    - [day02](https://github.com/is-prg1b/lx02)
    - [day03](https://github.com/is-prg1b/lx03)
    - [day04](https://github.com/is-prg1b/lx04)
    - [day05](https://github.com/is-prg1b/lx05)
    - [day06](https://github.com/is-prg1b/lx06)
    - [day07](https://github.com/is-prg1b/lx07)
    - [day08](https://github.com/is-prg1b/lx08)
    - [day09](https://github.com/is-prg1b/lx09)
    - [day10](https://github.com/is-prg1b/lx10)
    - [day11](https://github.com/is-prg1b/lx11)
    - [day12](https://github.com/is-prg1b/lx12)
    - [day13](https://github.com/is-prg1b/lx13)
    - [day14](https://github.com/is-prg1b/lx14) -->

# Gitリポジトリの初期化

以下のような要領で`git`コマンドを実行するとGitHub上のリポジトリを複製できます．

```bash
git clone git@github.com:is-prg1b/lecture
git clone git@github.com:is-prg1b/lx01
```

GitHubのデータが更新された場合は，適宜 `git pull` して下さい．最新版に更新できます．

GitHubから複製したデータを直接変更すると `git pull` のときにエラーが出ます．
