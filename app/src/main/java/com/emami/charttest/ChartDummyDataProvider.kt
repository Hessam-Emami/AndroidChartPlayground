package com.emami.charttest

import kotlin.random.Random

object ChartDummyDataProvider {
    val dummyDataForX = (0..6).toList()
    fun generateDummyDataForY() = List(7) { Random.nextInt(1, 100) }
    val quarters = arrayOf("۱دی", "۲دی", "۳دی", "۴دی", "۵دی", "۶دی", "۷دی")
}
