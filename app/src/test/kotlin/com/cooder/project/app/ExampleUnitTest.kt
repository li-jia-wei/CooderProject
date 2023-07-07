package com.cooder.project.app

import org.junit.Test
import java.lang.String.format
import kotlin.math.abs
import kotlin.math.min


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
	
	private val colorMos = listOf(
		ColorMo("#FEEBED", "#B71C1C", 24),
		ColorMo("#FBE3EB", "#C51161", 24),
		ColorMo("#F3E4F5", "#49138B", 24),
		ColorMo("#ECE7F6", "#301B92", 24),
		ColorMo("#E8EAF6", "#1A227D", 24),
		ColorMo("#E2F1FC", "#0D46A0", 24),
		ColorMo("#DFF7F9", "#005F63", 24),
		ColorMo("#DFF1F0", "#004D40", 24),
		ColorMo("#E8F5E9", "#1B5E1F", 24),
		ColorMo("#F0F8E9", "#32691E", 24),
		ColorMo("#F8FAE7", "#827716", 24),
		ColorMo("#FEFCE7", "#F57E16", 24),
		ColorMo("#FEF8E0", "#FE6E00", 24),
		ColorMo("#FEF3DF", "#E65100", 24),
		ColorMo("#FAE9E7", "#BE360B", 24),
	)
	
	@Test
	fun test() {
		colorMos.forEachIndexed { index, colorMo ->
			val start = parseColor(colorMo.start)
			val end = parseColor(colorMo.end)
			val colors = mutableListOf<String>()
			repeat(colorMo.count) { a ->
				var color = "#"
				repeat(3) { b ->
					val c = min(start[b], end[b]) + (abs(end[b] - start[b]) + 1) / (colorMo.count - 1) * a
					color += format("%02X", c).uppercase()
				}
				colors += color
			}
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