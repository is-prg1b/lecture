---
title: プログラミング第一
---

# 講義資料等

- 9月26日 --- [講義資料](https://github.com/is-prg1b/lecture/raw/master/slide/day01.pdf) / サンプルコード (sbt project: `lx01`) / [makeとMakefile](/lecture/note/lx01-makefile.html) / **次回小テスト**

- 9月29日 --- [講義資料](/lecture/slide/lx02.html) / サンプルコード (sbt projects: `lx02a` -- `lx02k`) / [テスト実行のログ](/lecture/note/lx02-tests.html) / [小テスト](/lecture/quiz/quiz01.html)

- 10月2日 --- [講義資料](https://github.com/is-prg1b/lecture/raw/master/slide/day03.pdf) / [補助スライド](/lecture/slide/lx03-exercise.html) / 次回小テスト

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

# Gitリポジトリの初期化

以下の要領で`git`コマンドを実行するとGitHub上のリポジトリを複製できます．

~~~ {.bash .src}
git clone git@github.com:is-prg1b/lecture
~~~

GitHubのデータが更新された場合は，適宜 `git pull` して下さい．最新版に更新できます．

GitHubから複製したデータを直接変更すると `git pull` のときにエラーが出ます．
