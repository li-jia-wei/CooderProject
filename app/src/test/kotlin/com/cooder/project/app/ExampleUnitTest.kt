package com.cooder.project.app

import org.junit.Test
import java.lang.String.format


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
	
	private val colorMos = listOf(
		ColorMo("#C2441D", "#BD2E2E", 5),
		ColorMo("#E8C9C8", "#F6D9D9", 5),
	)
	
	@Test
	fun test() {
		colorMos.forEachIndexed { index, colorMo ->
			val start = parseColor(colorMo.start)
			val end = parseColor(colorMo.end)
			val colors = mutableListOf<String>()
			repeat(colorMo.count - 1) { a ->
				var color = "#"
				repeat(3) { b ->
					var s = end[b] - start[b]
					if (s < 0) s--
					else if (s > 0) s++
					val c = start[b] + s / (colorMo.count - 1) * a
					color += format("%02X", c).uppercase()
				}
				colors += color
			}
			colors += colorMo.end
			println(format("%02X: %s", index, colors))
		}
	}
	
	private fun parseColor(color: String): List<Int> {
		val red = color.substring(1, 3).toInt(16)
		val green = color.substring(3, 5).toInt(16)
		val blue = color.substring(5, 7).toInt(16)
		return listOf(red, green, blue)
	}
	
	data class ColorMo(val start: String, val end: String, val count: Int)
}