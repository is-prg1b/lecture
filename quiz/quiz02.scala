import java.lang.{Byte => JByte}
import java.lang.{Short => JShort}
import scala.math._

println(JByte.parseByte("01010000", 2) + 4)

println(JByte.toUnsignedInt(-5.toByte).toBinaryString)

println(f"${-5.toByte}%x")

println(f"${JShort.toUnsignedInt(-5.toShort).toBinaryString}")

println("{5 < 3}")

println("${5 < 3}")

println(f"5 > 3, {5 > 3}, ${5 > 3}, $${5 > 3}")

println(f"${if (5 > 3) 0 else 1}")

println(f"π = ${sqrt(2)}%1.1f")

/*
:type (1:Byte) + (1:Byte)

:type (1:Byte) + (1:Short)

:type (1:Byte) + 1

:type 1 + 1

:type 1 + 1L

:type 1L + 1L
*/
