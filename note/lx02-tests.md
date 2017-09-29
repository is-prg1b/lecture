---
title: lx02 のテスト実行結果
date: 2017年9月29日
---

# lx02a

~~~
sbt:lx02a> test
[info] LeapYearTest:
[info] ScalaTest
[info] Run completed in 56 milliseconds.
[info] Total number of tests run: 0
[info] Suites: completed 1, aborted 0
[info] Tests: succeeded 0, failed 0, canceled 0, ignored 0, pending 0
[info] No tests were executed.
[info] Passed: Total 0, Failed 0, Errors 0, Passed 0
[success] Total time: 0 s, completed Sep 29, 2017 12:16:25 PM
~~~

# lx02b

~~~
sbt:lx02a> project lx02b
[info] Set current project to lx02b (in build file:/Users/wakita/Dropbox/projects/prg/lecture/)
sbt:lx02b> test
[info] LeapYearTest:
[info] ScalaTest
[info] Run completed in 63 milliseconds.
[info] Total number of tests run: 0
[info] Suites: completed 1, aborted 0
[info] Tests: succeeded 0, failed 0, canceled 0, ignored 0, pending 0
[info] No tests were executed.
[info] Passed: Total 0, Failed 0, Errors 0, Passed 0
[success] Total time: 0 s, completed Sep 29, 2017 12:16:59 PM
~~~

# lx02c

~~~
sbt:lx02b> project lx02c
[info] Set current project to lx02c (in build file:/Users/wakita/Dropbox/projects/prg/lecture/)
sbt:lx02c> test
[info] LeapYearTest:
[info] 4で割り切れる年
[info] - should 閏年である
[info] ScalaTest
[info] Run completed in 101 milliseconds.
[info] Total number of tests run: 1
[info] Suites: completed 1, aborted 0
[info] Tests: succeeded 1, failed 0, canceled 0, ignored 0, pending 0
[info] All tests passed.
[info] Passed: Total 1, Failed 0, Errors 0, Passed 1
[success] Total time: 0 s, completed Sep 29, 2017 12:17:22 PM
~~~

# lx02d

~~~
sbt:lx02d> test
[info] LeapYearTest:
[info] 4で割り切れる年
[info] - should 閏年である
[info] 4で割り切れない年
[info] - should 閏年でない *** FAILED ***
[info]   true did not equal false (leapyear.scala:13)
[info] ScalaTest
[info] Run completed in 95 milliseconds.
[info] Total number of tests run: 2
[info] Suites: completed 1, aborted 0
[info] Tests: succeeded 1, failed 1, canceled 0, ignored 0, pending 0
[info] *** 1 TEST FAILED ***
[error] Failed: Total 2, Failed 1, Errors 0, Passed 1
[error] Failed tests:
[error] 	LeapYearTest
[error] (lx02d/test:test) sbt.TestsFailedException: Tests unsuccessful
[error] Total time: 0 s, completed Sep 29, 2017 12:43:53 PM
~~~

# lx02e

~~~
sbt:lx02d> project lx02e
[info] Set current project to lx02e (in build file:/Users/wakita/Dropbox/projects/prg/lecture/)
sbt:lx02e> test
[info] Updating {file:/Users/wakita/Dropbox/projects/prg/lecture/}lx02e...
[info] Done updating.
[info] Compiling 1 Scala source to /Users/wakita/tmp/sbt/prg1b/lx02e/scala-2.12/classes ...
[info] Done compiling.
[info] Compiling 1 Scala source to /Users/wakita/tmp/sbt/prg1b/lx02e/scala-2.12/test-classes ...
[info] Done compiling.
[info] LeapYearTest:
[info] 4で割り切れる年
[info] - should 閏年である *** FAILED ***
[info]   false did not equal true (leapyear.scala:8)
[info] 4で割り切れない年
[info] - should 閏年でない
[info] ScalaTest
[info] Run completed in 141 milliseconds.
[info] Total number of tests run: 2
[info] Suites: completed 1, aborted 0
[info] Tests: succeeded 1, failed 1, canceled 0, ignored 0, pending 0
[info] *** 1 TEST FAILED ***
[error] Failed: Total 2, Failed 1, Errors 0, Passed 1
[error] Failed tests:
[error] 	LeapYearTest
[error] (lx02e/test:test) sbt.TestsFailedException: Tests unsuccessful
[error] Total time: 1 s, completed Sep 29, 2017 12:18:17 PM
~~~

# lx02f

~~~
sbt:lx02e> project lx02f
[info] Set current project to lx02f (in build file:/Users/wakita/Dropbox/projects/prg/lecture/)
sbt:lx02f> test
[info] Updating {file:/Users/wakita/Dropbox/projects/prg/lecture/}lx02f...
[info] Done updating.
[info] Compiling 1 Scala source to /Users/wakita/tmp/sbt/prg1b/lx02f/scala-2.12/classes ...
[info] Done compiling.
[info] Compiling 1 Scala source to /Users/wakita/tmp/sbt/prg1b/lx02f/scala-2.12/test-classes ...
[info] Done compiling.
[info] LeapYearTest:
[info] 4で割り切れる年
[info] - should 閏年である
[info] 4で割り切れない年
[info] - should 閏年でない
[info] ScalaTest
[info] Run completed in 120 milliseconds.
[info] Total number of tests run: 2
[info] Suites: completed 1, aborted 0
[info] Tests: succeeded 2, failed 0, canceled 0, ignored 0, pending 0
[info] All tests passed.
[info] Passed: Total 2, Failed 0, Errors 0, Passed 2
[success] Total time: 1 s, completed Sep 29, 2017 12:18:35 PM
~~~

# lx02g

~~~
sbt:lx02f> project lx02g
[info] Set current project to lx02g (in build file:/Users/wakita/Dropbox/projects/prg/lecture/)
sbt:lx02g> test
[info] Updating {file:/Users/wakita/Dropbox/projects/prg/lecture/}lx02g...
[info] Done updating.
[info] Compiling 1 Scala source to /Users/wakita/tmp/sbt/prg1b/lx02g/scala-2.12/classes ...
[info] Done compiling.
[info] Compiling 1 Scala source to /Users/wakita/tmp/sbt/prg1b/lx02g/scala-2.12/test-classes ...
[info] Done compiling.
[info] LeapYearTest:
[info] 4で割り切れる年
[info] - should 閏年である
[info] 4で割り切れない年
[info] - should 閏年でない
[info] 100で割り切れる年
[info] - should 閏年でない *** FAILED ***
[info]   true did not equal false (leapyear.scala:19)
[info] ScalaTest
[info] Run completed in 116 milliseconds.
[info] Total number of tests run: 3
[info] Suites: completed 1, aborted 0
[info] Tests: succeeded 2, failed 1, canceled 0, ignored 0, pending 0
[info] *** 1 TEST FAILED ***
[error] Failed: Total 3, Failed 1, Errors 0, Passed 2
[error] Failed tests:
[error] 	LeapYearTest
[error] (lx02g/test:test) sbt.TestsFailedException: Tests unsuccessful
[error] Total time: 1 s, completed Sep 29, 2017 12:18:55 PM
~~~

# lx02h

~~~
sbt:lx02g> project lx02h
[info] Set current project to lx02h (in build file:/Users/wakita/Dropbox/projects/prg/lecture/)
sbt:lx02h> test
[info] Updating {file:/Users/wakita/Dropbox/projects/prg/lecture/}lx02h...
[info] Done updating.
[info] Compiling 1 Scala source to /Users/wakita/tmp/sbt/prg1b/lx02h/scala-2.12/classes ...
[info] Done compiling.
[info] Compiling 1 Scala source to /Users/wakita/tmp/sbt/prg1b/lx02h/scala-2.12/test-classes ...
[info] Done compiling.
[info] LeapYearTest:
[info] 4で割り切れる年
[info] - should 閏年である
[info] 4で割り切れない年
[info] - should 閏年でない
[info] 100で割り切れる年
[info] - should 閏年でない
[info] ScalaTest
[info] Run completed in 154 milliseconds.
[info] Total number of tests run: 3
[info] Suites: completed 1, aborted 0
[info] Tests: succeeded 3, failed 0, canceled 0, ignored 0, pending 0
[info] All tests passed.
[info] Passed: Total 3, Failed 0, Errors 0, Passed 3
[success] Total time: 1 s, completed Sep 29, 2017 12:19:10 PM
~~~

# lx02i

~~~
sbt:lx02h> project lx02i
[info] Set current project to lx02i (in build file:/Users/wakita/Dropbox/projects/prg/lecture/)
sbt:lx02i> test
[info] Compiling 1 Scala source to /Users/wakita/tmp/sbt/prg1b/lx02i/scala-2.12/test-classes ...
[error] /Users/wakita/Dropbox/projects/prg/lecture/lx02i/test/leapyear.scala:33:36: type mismatch;
[error]  found   : Boolean
[error]  required: Option[?]
[error]     assert((compare_option(leapyear(1898), None)) == true)
[error]                                    ^
[error] /Users/wakita/Dropbox/projects/prg/lecture/lx02i/test/leapyear.scala:34:36: type mismatch;
[error]  found   : Boolean
[error]  required: Option[?]
[error]     assert((compare_option(leapyear(1899), None)) == false)
[error]                                    ^
[error] two errors found
[error] (lx02i/test:compileIncremental) Compilation failed
[error] Total time: 1 s, completed Sep 29, 2017 12:20:05 PM
~~~

# lx02j

~~~
sbt:lx02i> project lx02j
[info] Set current project to lx02j (in build file:/Users/wakita/Dropbox/projects/prg/lecture/)
sbt:lx02j> test
[info] LeapYearTest:
[info] 4で割り切れる年
[info] - should 閏年である
[info] 4で割り切れない年
[info] - should 閏年でない
[info] 100で割り切れる年
[info] - should 閏年でない
[info] 1899年前後
[info] - should 法律の施行時期を反映 *** FAILED ***
[info]   LeapYearTest.this.compare_option[Boolean](LeapYear.leapyear(1898), scala.None) was false (leapyear.scala:31)
[info] ScalaTest
[info] Run completed in 92 milliseconds.
[info] Total number of tests run: 4
[info] Suites: completed 1, aborted 0
[info] Tests: succeeded 3, failed 1, canceled 0, ignored 0, pending 0
[info] *** 1 TEST FAILED ***
[error] Failed: Total 4, Failed 1, Errors 0, Passed 3
[error] Failed tests:
[error] 	LeapYearTest
[error] (lx02j/test:test) sbt.TestsFailedException: Tests unsuccessful
[error] Total time: 0 s, completed Sep 29, 2017 12:20:17 PM
~~~

# lx02k

~~~
sbt:lx02j> project lx02k
[info] Set current project to lx02k (in build file:/Users/wakita/Dropbox/projects/prg/lecture/)
sbt:lx02k> test
[info] Updating {file:/Users/wakita/Dropbox/projects/prg/lecture/}lx02k...
[info] Done updating.
[info] Compiling 1 Scala source to /Users/wakita/tmp/sbt/prg1b/lx02k/scala-2.12/classes ...
[info] Done compiling.
[info] Compiling 1 Scala source to /Users/wakita/tmp/sbt/prg1b/lx02k/scala-2.12/test-classes ...
[info] Done compiling.
[info] LeapYearTest:
[info] 4で割り切れる年
[info] - should 閏年である
[info] 4で割り切れない年
[info] - should 閏年でない
[info] 100で割り切れる年
[info] - should 閏年でない
[info] 1899年前後
[info] - should 法律の施行時期を反映
[info] ScalaTest
[info] Run completed in 86 milliseconds.
[info] Total number of tests run: 4
[info] Suites: completed 1, aborted 0
[info] Tests: succeeded 4, failed 0, canceled 0, ignored 0, pending 0
[info] All tests passed.
[info] Passed: Total 4, Failed 0, Errors 0, Passed 4
[success] Total time: 2 s, completed Sep 29, 2017 12:24:24 PM
~~~
