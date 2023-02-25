package com.cooder.cooder.debug

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class CoDebug(val name: String, val desc: String)
