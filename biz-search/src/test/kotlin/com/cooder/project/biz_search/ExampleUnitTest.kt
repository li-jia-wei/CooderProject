package com.cooder.project.biz_search

import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
	@Test
	fun addition_isCorrect() {
		val str = "Hello World"
		val newStr = str.replace(" ", "").toSet()
		println(newStr)
	}
}